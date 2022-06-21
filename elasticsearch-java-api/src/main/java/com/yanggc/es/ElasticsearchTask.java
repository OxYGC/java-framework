package com.yanggc.es;

import org.elasticsearch.client.RestHighLevelClient;

/**
 * Description:
 *
 * @author: YangGC
 */
public interface ElasticsearchTask {
    void doSomething(RestHighLevelClient client) throws Exception;
}
