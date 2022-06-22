package com.yanggc.pojo;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 *
 * @author: YangGC
 */
@Repository
public interface ProductDao extends ElasticsearchRepository<Product, Long> {
    //会自动按命名来封装相应功能(JPA思想)
    List<Product> findProductByTitleLike(String title);
}
