package com.yanggc.controller.test;


import com.yanggc.pojo.Product;
import com.yanggc.pojo.ProductDao;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Description:
 *
 * @author: YangGC
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringDataESSearchTest {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    /**
     * term(关键字) 查询
     * search(termQueryBuilder) 调用搜索方法，参数查询构建器对象
     */
    @Test
    public void termQuery(){
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", "小米");
//        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("price", "2004.0");
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(termQueryBuilder);
        NativeSearchQuery nativeSearchQuery = nativeSearchQueryBuilder.build();
        SearchHits<Product> search = this.elasticsearchOperations.search(nativeSearchQuery, Product.class, IndexCoordinates.of("product"));
        for (SearchHit<Product> productSearchHit : search) {
            Product content = productSearchHit.getContent();
            System.out.println(content);
        }
    }

    /**
     * boolean 查询
     */
    @Test
    public void booleanQuery(){
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        // 查询必须满足的条件
        boolQueryBuilder.must(QueryBuilders.termQuery("title", "小米"));
        // 查询可能满足的条件
        boolQueryBuilder.should(QueryBuilders.termQuery("price", "1999"));
        boolQueryBuilder.should(QueryBuilders.termQuery("price", "2005"));
        // 设置在可能满足的条件中，至少必须满足其中1条
        boolQueryBuilder.minimumShouldMatch(1);
//        // 必须不满足的条件
        boolQueryBuilder.mustNot(QueryBuilders.termQuery("id", 0));

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        NativeSearchQuery nativeSearchQuery = nativeSearchQueryBuilder.build();
        SearchHits<Product> search = this.elasticsearchOperations.search(nativeSearchQuery, Product.class, IndexCoordinates.of("product"));
        for (SearchHit<Product> productSearchHit : search) {
            Product content = productSearchHit.getContent();
            System.out.println(content);
        }
    }

    @Test
    public void rangeQuery(){
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();

        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("price");
        // 大于等于
        rangeQueryBuilder.gte(1999);
        // 小于等于
        rangeQueryBuilder.lte(2006);
        nativeSearchQueryBuilder.withQuery(rangeQueryBuilder);

        RangeQueryBuilder rangeQueryBuilder2 = QueryBuilders.rangeQuery("id");
        rangeQueryBuilder2.gte(0);
        rangeQueryBuilder2.lte(4);
        nativeSearchQueryBuilder.withQuery(rangeQueryBuilder2);

        NativeSearchQuery nativeSearchQuery = nativeSearchQueryBuilder.build();

        SearchHits<Product> search = this.elasticsearchOperations.search(nativeSearchQuery, Product.class, IndexCoordinates.of("product"));
        for (SearchHit<Product> productSearchHit : search) {
            Product content = productSearchHit.getContent();
            System.out.println(content);
        }
    }





    /**
     * term 查询加分页
     */
    @Test
    public void termQueryByPage(){
        int currentPage= 0 ;
        int pageSize = 5;
        //设置查询分页
        PageRequest pageRequest = PageRequest.of(currentPage, pageSize);

        Product productt = new Product();
        Page<Product> products4Page = productDao.searchSimilar(productt, null, pageRequest);
        for (Product product : products4Page.getContent()) {
            System.out.println(product);
        }
    }



}
