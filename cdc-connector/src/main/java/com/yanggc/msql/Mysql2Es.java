package com.yanggc.msql;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.types.Row;

/**
 *
 * 目前同步报错：
 * Unable to create a source for reading table 'default_catalog.default_database.orders
 *
 * mysql同步数据到ES
 * Description:
 * <p>
 * Entry class: com.yanggc.msql.Mysql2Es
 * program Arguments: -m 192.168.1.220
 * @author: YangGC
 */
public class Mysql2Es {

    public static void main(String[] args) throws Exception {

        //1.创建执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.setParallelism(1);
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

        String CONNECTOR_SOURCE_MSYQL = "'connector' = 'mysql-cdc'" +
                ",'hostname' = '192.168.1.220'" +
                ",'port' = '3308'" +
                ",'username' = 'root'" +
                ",'password' = 'useradmin'" +
                ",'database-name' = 'flinkcdc',";

        String CONNECTOR_TARGET_ES = " 'connector' = 'elasticsearch-7', 'hosts' = 'http://192.168.1.71:9200', 'index' = 'enriched_orders','format' = 'json'";


        //2.创建 Flink-MySQL-CDC 的 Source (products 和orders)
        tableEnv.executeSql("CREATE TABLE products ( id INT, NAME STRING, description STRING, PRIMARY KEY ( id ) NOT ENFORCED)" +
                "WITH (" + CONNECTOR_SOURCE_MSYQL + "'table-name' = 'products')");

//
        tableEnv.executeSql("CREATE TABLE orders (order_id INT, order_date TIMESTAMP ( 0 ), customer_name STRING,price DECIMAL ( 10, 5 ),product_id INT,order_status BOOLEAN ,PRIMARY KEY ( order_id ) NOT ENFORCED) " +
                "WITH (" + CONNECTOR_SOURCE_MSYQL + "'table-name' = 'orders')");

        //插入ES ,PRIMARY KEY ( order_id ) NOT ENFORCED
        tableEnv.executeSql("CREATE TABLE enriched_orders ( order_id INT,order_date TIMESTAMP ( 0 ),customer_name STRING,price DECIMAL ( 10, 5 ),product_id INT,order_status BOOLEAN,product_name STRING,product_description STRING ,PRIMARY KEY ( order_id ) NOT ENFORCED)" +
                "WITH (" + CONNECTOR_TARGET_ES + ")");

        tableEnv.executeSql("INSERT INTO enriched_orders SELECT o.order_id AS order_id, o.order_date AS order_date, o.customer_name AS customer_name, o.price AS price, o.product_id AS product_id, o.order_status AS order_status, p.NAME AS product_name, p.description AS product_description FROM orders AS o LEFT JOIN products AS p ON o.product_id = p.id ");

        //查询数据并且输出
        Table table = tableEnv.sqlQuery("SELECT o.order_id AS order_id, o.order_date AS order_date, o.customer_name AS customer_name, o.price AS price, o.product_id AS product_id, o.order_status AS order_status, p.NAME AS product_name, p.description AS product_description FROM orders AS o LEFT JOIN products AS p ON o.product_id = p.id");

        DataStream<Tuple2<Boolean, Row>> retractStream = tableEnv.toRetractStream(table, Row.class);
        retractStream.print();
        env.execute("Mysql2Es");
    }

}
