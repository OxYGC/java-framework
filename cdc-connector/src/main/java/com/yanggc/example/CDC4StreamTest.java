package com.yanggc.example;



import com.ververica.cdc.connectors.mysql.MySqlSource;
import com.ververica.cdc.connectors.mysql.table.StartupOptions;
import com.ververica.cdc.debezium.DebeziumSourceFunction;
import com.ververica.cdc.debezium.StringDebeziumDeserializationSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 *
 * Flink Stream 方式的实现
 * Description:
 *
 * @author: YangGC
 */
public class CDC4StreamTest {
    public static void main(String[] args) throws Exception {
        //1.创建执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        //通过FlinkCDC构建SourceFunction
        DebeziumSourceFunction<String> sourceFunction = MySqlSource.<String>builder()
                .hostname("192.168.1.220")
                .port(3308)
                //flinkcdc 下面的所有表
                .databaseList("flinkcdc.*")
                //可以指定个别的表
//                .tableList("flinkcdc.orders")
                .username("root")
                .password("useradmin")
                .startupOptions(StartupOptions.initial())
                //反序列化
                .deserializer(new StringDebeziumDeserializationSchema())
                .build();

        DataStreamSource<String> streamSource = env.addSource(sourceFunction);

        streamSource.print();


        env.execute("CDC4StreamTest");
    }
}
