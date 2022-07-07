package com.yanggc.example.deserial;


import com.alibaba.ververica.cdc.connectors.mysql.MySQLSource;
import com.alibaba.ververica.cdc.connectors.mysql.table.StartupOptions;
import com.alibaba.ververica.cdc.debezium.DebeziumSourceFunction;
import com.alibaba.ververica.cdc.debezium.StringDebeziumDeserializationSchema;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 *
 * 自定义反序列化器测试
 * Description:
 *
 * @author: YangGC
 */
public class CDC4Stream2Test {
    public static void main(String[] args) throws Exception {
        //1.创建执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        //打开ckp
        env.enableCheckpointing(5000);
        env.getCheckpointConfig().setCheckpointTimeout(10000);
        //确定一致模式
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
        env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);



        DebeziumSourceFunction<String> sourceFunction = MySQLSource.<String>builder()
                .hostname("192.168.64.128")
                .port(4306)
                .databaseList("flinkcdc.*") // set captured database, If you need to synchronize the whole database, Please set tableList to ".*".
//                .tableList("flinkcdc.orders") // set captured table
                .username("root")
                .password("useradmin")
                .startupOptions(StartupOptions.initial())
                .deserializer(new CustomerDeserializtionSchema())
                .build();

        DataStreamSource<String> streamSource = env.addSource(sourceFunction);
        streamSource.print();
        env.execute("flinkCdc");
    }
}
