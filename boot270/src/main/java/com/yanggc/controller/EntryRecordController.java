package com.yanggc.controller;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.LongTermsBucket;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import com.yanggc.pojo.doc.EntryRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * Description:
 *
 * @author: YangGC
 */
@Slf4j
@RestController
public class EntryRecordController {

    @Resource
    ElasticsearchClient client;

    /**
     * 找出时间最新的几条数据
     *
     * @param topNum
     * @return
     */
    @GetMapping("/entryRecordNewTopN")
    public void entryRecordNewTopN(@RequestParam("newTopNum") Integer topNum) throws IOException {
        SortOptions time = new SortOptions.Builder().field(f -> {
            f.field("time");
            f.order(SortOrder.Desc);
            return f;
        }).build();

        SearchResponse<EntryRecord> search = client.search(s ->
                s.index("entry_record_v1").size(topNum).sort(time)
                        .query(q -> q.range(r -> r.field("time").gte(JsonData.of("2022-07-01 00:00:00")).lt(JsonData.of("2022-08-01 00:00:00")))), EntryRecord.class);

        for (Hit<EntryRecord> hit : search.hits().hits()) {
            EntryRecord source = hit.source();
            System.out.println(source.toString());
        }
    }


    /**
     * dsl:
     * # topN 每个门店进店次数最多的3个人
     * GET /entry_record_v1/_search
     * {
     *     "query": {
     *     "range": {
     *       "time": {
     *         "gte": "2022-07-01 00:00:00",
     *         "lte": "2022-07-31 00:00:00"
     *       }
     *     }
     *   },
     *   "size": 0,
     *   "aggs": {
     *     "group_by_store": {
     *       "terms": {
     *         "field": "store_id",
     *         "size": 280
     *       },
     *       "aggs": {
     *         "group_by_member_id":{
     *           "terms": {
     *             "field": "member_id",
     *             "size": 100
     *           }
     *         }
     *       }
     *     }
     *   }
     * }
     */


    /**
     * 七月份 前200个门店每家门店进店次数最多的三个人
     *
     * @param topNum
     * @return
     */
    @GetMapping("/newTopNByStore")
    public void newTopNByStore(@RequestParam("newTopNum") Integer topNum) throws IOException {
        SearchResponse<Void> searchResponse = client.search(s -> s.index("entry_record_v1")
                .query(q -> q.range(r -> r.field("time").gte(JsonData.of("2022-07-01 00:00:00")).lt(JsonData.of("2022-08-01 00:00:00")))).size(0)
                .aggregations("group_store", a -> a.terms(ter -> ter.field("store_id").size(200))
                        .aggregations("count_store_member", group_by_member_id -> group_by_member_id.terms(ter -> ter.field("member_id").size(topNum)))), Void.class);

        List<LongTermsBucket> array = searchResponse.aggregations().get("group_store")._get()._toAggregate().lterms().buckets().array();
        for (LongTermsBucket longTermsBucket : array) {
            List<LongTermsBucket> countStoreMemberList = longTermsBucket.aggregations().get("count_store_member")._get()._toAggregate().lterms().buckets().array();
            countStoreMemberList.forEach(var->{
                System.out.println(var.key());
                System.out.println(var.docCount());
            });
        }
    }


    /**
     * 根据名称搜索会员
     *
     * @param name
     */
    @GetMapping("/searchMemberByName")
    public void searchMemberByName(@RequestParam("name") String name) throws IOException {
        Query query = MatchQuery.of(mq -> mq.field("name").query(name))._toQuery();
        SearchResponse<EntryRecord> searchResponse = client.search(s -> s.index("entry_record_v1").size(10).query(query), EntryRecord.class);
        for (Hit<EntryRecord> hit : searchResponse.hits().hits()) {
            System.out.println(hit.source());
        }
    }


}
