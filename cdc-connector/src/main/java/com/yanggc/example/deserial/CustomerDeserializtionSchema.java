package com.yanggc.example.deserial;

import com.alibaba.ververica.cdc.debezium.DebeziumDeserializationSchema;
import io.debezium.data.Envelope;
import net.minidev.json.JSONObject;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.util.Collector;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;

import java.util.List;

/**
 * 自定义反序列化器
 * Description:
 *
 * @Author: YangGC
 * DateTime: 07-08
 */
public class CustomerDeserializtionSchema implements DebeziumDeserializationSchema<String> {

    /**
     *
     * @param sourceRecord
     * {
     *     "db":"",
     *     "tableName":"",
     *     "before":{"id":"","name":""},
     *     "after":{"id":"","name":""},
     *     "op":""
     * }
     *
     * @param collector
     * @throws Exception
     */

    @Override
    public void deserialize(SourceRecord sourceRecord, Collector<String> collector) throws Exception {
        JSONObject result = new JSONObject();

        //获取库名&表名
        String topic = sourceRecord.topic();
        String[] fields = topic.split("\\.");
        result.put("db",fields[1]);
        result.put("tableName",fields[2]);

        //获取before数据
        Struct value = (Struct)sourceRecord.value();
        Struct before = value.getStruct("before");
        JSONObject beforeJson = new JSONObject();
        if(before != null){
            //获取列信息
            Schema schema = before.schema();
            List<Field> fieldList = schema.fields();
            for (Field field : fieldList) {
                beforeJson.put(field.name(),before.get(field));
            }
        }
        result.put("before",beforeJson);

        //获取操作类型
        Envelope.Operation operation =  Envelope.operationFor(sourceRecord);

        result.put("op",operation);


        //输出数据
        collector.collect(result.toJSONString());
    }

    @Override
    public TypeInformation<String> getProducedType() {
        return BasicTypeInfo.STRING_TYPE_INFO;
    }
}
