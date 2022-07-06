package com.yanggc.example;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.types.Row;


/**
 * FlinkSQL 方式的实现
 * Description:
 *
 * @author: YangGC
 */
public class CDC4SQLTest {

    public static void main(String[] args) throws Exception {

        //1.创建执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.setParallelism(1);
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

        //2.创建 Flink-MySQL-CDC 的 Source
        tableEnv.executeSql("CREATE TABLE products (" +
                " id INTEGER NOT NULL," +
                " name STRING NOT NULL," +
                " description STRING" +
                ") WITH (" +
                " 'connector' = 'mysql-cdc'," +
                " 'hostname' = '192.168.64.128'," +
                " 'port' = '4306'," +
                " 'username' = 'root'," +
                " 'password' = 'useradmin'," +
                " 'database-name' = 'flinkcdc'," +
                " 'table-name' = 'products'" +
                ")");

        //tableEnv.executeSql("select * from user_info1").print();
        Table table = tableEnv.sqlQuery("select * from products");
        DataStream<Tuple2<Boolean, Row>> retractStream = tableEnv.toRetractStream(table, Row.class);
        retractStream.print();
        env.execute("flinkCdcSql");
    }
}
