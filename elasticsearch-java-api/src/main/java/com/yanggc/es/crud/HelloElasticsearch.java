package com.yanggc.es.crud;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * Description:
 *
 * @author: YangGC
 */
public class HelloElasticsearch {
    public static void main(String[] args) throws IOException {
        // 创建客户端对象
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("10.10.10.124", 9200, "http")));
        System.out.println(client);
        // 关闭客户端连接
        client.close();
    }
}
