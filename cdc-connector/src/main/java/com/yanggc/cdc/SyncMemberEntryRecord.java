package com.yanggc.cdc;

import com.ververica.cdc.connectors.mysql.MySqlSource;
import com.ververica.cdc.connectors.mysql.table.StartupOptions;
import com.ververica.cdc.debezium.DebeziumSourceFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.types.Row;

/**
 * 同步会员记录
 * Description:
 * 支持断点续传: 代码推荐增量更新,
 * 增量更新： StartupOptions.latest()
 * 全量更新使用: StartupOptions.initial()
 * @author: YangGC
 */
public class SyncMemberEntryRecord {
    public static void main(String[] args) throws Exception {


        //1.创建执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.setParallelism(1);
        //1.1 开启 Checkpoint
        env.enableCheckpointing(5000);
        env.getCheckpointConfig().setCheckpointTimeout(10000);
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
        env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);



        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

        //2.创建 Flink-MySQL-CDC 的 Source
        tableEnv.executeSql("CREATE TABLE products (" +
                " id INTEGER NOT NULL," +
                " name STRING NOT NULL," +
                " description STRING" +
                ") WITH (" +
                " 'connector' = 'mysql-cdc'," +
                " 'hostname' = '192.168.1.220'," +
                " 'port' = '3308'," +
                " 'username' = 'root'," +
                " 'scan.startup.mode' = 'latest-offset'," +
                " 'password' = 'useradmin'," +

                " 'database-name' = 'flinkcdc'," +
                " 'table-name' = 'products'" +
                ")");
        Table table = tableEnv.sqlQuery("select * from products");

        DataStream<Tuple2<Boolean, Row>> retractStream = tableEnv.toRetractStream(table, Row.class);
        retractStream.print();
        env.execute("flinkCdcSql");

    }

}
