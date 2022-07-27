package com.yanggc.controller;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.IntegerNumberProperty;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import co.elastic.clients.elasticsearch._types.mapping.TextProperty;
import co.elastic.clients.elasticsearch.indices.*;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 *
 * @author: YangGC
 */

@Slf4j
@RestController("/es")
public class IndexController {

    @Resource
    ElasticsearchClient elasticsearchClient;


    @PostMapping("/createIndex")
    public String createIndex(@RequestParam String indexName) throws IOException {
        //创建一个空字段索引
//        elasticsearchClient.indices().create(createIndex -> createIndex.index(indexName));

        /**
         *  分析器主要有两种情况会被使用：
         *      第一种是插入文档时，将text类型的字段做分词然后插入倒排索引，
         *      第二种就是在查询时，先对要查询的text类型的输入做分词，再去倒排索引搜索
         *  analyzer: 分词器
         *  searchAnalyzer： 查询分词器
         */
        //配置索引
        Map<String, Property> documentMap = new HashMap<>();
        documentMap.put("id", Property.of(val -> val.integer(IntegerNumberProperty.of(ival -> ival.index(true)))));
        documentMap.put("user_name", Property.of(val -> val.text(TextProperty.of(valt -> valt.index(true).analyzer("ik_max_word")
                .searchAnalyzer("ik_smart").index(true).store(true)))));
        documentMap.put("age", Property.of(val -> val.integer(IntegerNumberProperty.of(ival -> ival.index(true)))));

        CreateIndexResponse createIndexResponse = elasticsearchClient.indices().create(val -> val.index(indexName)
                .mappings(currMapping -> currMapping.properties(documentMap)).aliases("aliases"+indexName, aliases -> aliases.isWriteIndex(true)));
        boolean acknowledged = createIndexResponse.acknowledged();
        System.out.println("acknowledged = " + acknowledged);
        return indexName;
    }

    //测试判断是否拥有某个索引
    @GetMapping("/existsIndex")
    boolean existsIndex() throws IOException {
        //创建获取索引请求
        ExistsRequest existsRequest = new ExistsRequest.Builder().index("user").build();
        //执行获取索引请求判断是否有这个索引
        BooleanResponse booleanResponse = elasticsearchClient.indices().exists(existsRequest);
        return booleanResponse.value();
    }

    @GetMapping("/getIndex")
    Map<String, IndexState> getIndex() throws IOException {
        //创建获取索引请求
        GetIndexRequest indexRequest = new GetIndexRequest.Builder().index("user").build();
        //执行获取索引请求判断是否有这个索引
        GetIndexResponse indexResponse = elasticsearchClient.indices().get(indexRequest);
        return indexResponse.result();
    }

    @GetMapping("/deleteIndex")
    public String deleteIndex(@RequestParam String indexName) throws IOException {
        DeleteIndexResponse deleteIndexResponse = elasticsearchClient.indices().delete(index -> index.index(indexName));
        boolean acknowledged = deleteIndexResponse.acknowledged();
        System.out.println("acknowledged = " + acknowledged);
        return acknowledged+"";
    }

}
