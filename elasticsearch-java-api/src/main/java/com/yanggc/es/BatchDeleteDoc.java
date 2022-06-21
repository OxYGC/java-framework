package com.yanggc.es;

import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.client.RequestOptions;

/**
 * Description:
 * 批量删除
 * @author: YangGC
 */
public class BatchDeleteDoc {

    public static void main(String[] args) {
        ConnectElasticsearch.connect(client -> {
            //创建批量删除请求对象
            BulkRequest request = new BulkRequest();
            request.add(new DeleteRequest().index("user").id("1001"));
            request.add(new DeleteRequest().index("user").id("1002"));
            request.add(new DeleteRequest().index("user").id("1003"));
            BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
            //打印结果信息
            System.out.println("took:" + response.getTook());
            System.out.println("items:" + response.getItems());

        });
    }
}
