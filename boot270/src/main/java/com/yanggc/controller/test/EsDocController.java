package com.yanggc.controller.test;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.*;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author: YangGC
 */
@RestController
@RequestMapping("/esdoc")
public class EsDocController {

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    //设置索引
    @RequestMapping("/createIndex")
    public void createIndex() throws IOException {
        Map<String, Property> property = new HashMap<>();
        property.put("name", new Property(new TextProperty.Builder().analyzer("ik_max_word").searchAnalyzer("ik_smart").index(true).store(true).build()));
        property.put("staffNo", new Property(new LongNumberProperty.Builder().index(true).store(true).build()));
        property.put("age", new Property(new IntegerNumberProperty.Builder().index(true).store(true).build()));
        TypeMapping typeMapping = new TypeMapping.Builder().properties(property).build();
        //该API还可设置分片设置,别名设置等等
        CreateIndexRequest createIndexRequest = new CreateIndexRequest.Builder().index("staff").mappings(typeMapping).build();
        CreateIndexResponse createIndexResponse = elasticsearchClient.indices().create(createIndexRequest);
        System.out.println(createIndexResponse.acknowledged());
    }





    @RequestMapping("/createDocument")
    public void createDocument() throws IOException {
        Staff staff001 = new Staff(001L,"员工001-王大力",22);

        IndexRequest<Staff> indexRequest = new IndexRequest.Builder<Staff>().index("staff").document(staff001).id("001").build();
        IndexResponse indexResponse = elasticsearchClient.index(indexRequest);
        //返回索引信息
        System.out.println(indexResponse.toString());
        //返回id
        System.out.println(indexResponse.result());
    }

    //查看是否存在
    @RequestMapping("/existsDocument")
    public void existsDocument() throws IOException {
        GetRequest getRequest = new GetRequest.Builder().index("staff").id("001").build();
        GetResponse<Staff> bookGetResponse = elasticsearchClient.get(getRequest, Staff.class);
        //查看是否存在
        //IndexResponse: {"_id":"001","_index":"staff","_primary_term":1,"result":"created","_seq_no":0,"_shards":{"failed":0.0,"successful":1.0,"total":2.0},"_type":"_doc","_version":1}
        System.out.println(bookGetResponse.found());
    }

    /**
     * 获取文档
     * @throws IOException
     */
    @RequestMapping("/getDocument")
    public void getDocument() throws IOException {
        GetRequest getRequest = new GetRequest.Builder().index("staff").id("001").build();
        GetResponse<Staff> bookGetResponse = elasticsearchClient.get(getRequest, Staff.class);

        Staff staff = bookGetResponse.source();
        System.out.println("staff = " + staff);
        /**
         * staff = Staff(staffNo=1, name=员工001-王大力, age=22)
         */
    }

    /**
     * 分页获取文档
     */
    @RequestMapping("/getDocumentByPage")
    public void getDocumentByPage() throws IOException {
        SearchRequest searchRequest = new SearchRequest.Builder().index("staff").from(0).size(10).build();
        SearchResponse<Staff> bookSearchResponse = elasticsearchClient.search(searchRequest, Staff.class);

        List<Hit<Staff>> bookList = bookSearchResponse.hits().hits();
        bookList.forEach(item->System.out.println(item.source()));
    }

    /**
     * 更新文档
     */
    @RequestMapping("/updateDocument")
    public void updateDocument() throws IOException {
        Staff staff = new Staff();
        staff.setName("员工001-出奇迹");
        UpdateRequest<Staff, Staff> bookBookUpdateRequest = new UpdateRequest.Builder<Staff, Staff>().index("staff").id("001").doc(staff).build();
        UpdateResponse<Staff> personUpdateResponse = elasticsearchClient.update(bookBookUpdateRequest, Staff.class);
        // 执行结果: Updated
        System.out.println(personUpdateResponse.result());
    }


    //删除文档信息
    @RequestMapping("/deleteDocument")
    public void deleteDocument() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest.Builder().index("staff").id("001").build();
        DeleteResponse delete = elasticsearchClient.delete(deleteRequest);
        //删除状态 Deleted
        System.out.println(delete.result());

    }

}
