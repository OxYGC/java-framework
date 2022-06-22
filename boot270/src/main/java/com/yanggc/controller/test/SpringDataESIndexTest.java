package com.yanggc.controller.test;

import com.yanggc.pojo.Product;
import com.yanggc.pojo.ProductDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;




/**
 * Description:
 *
 * @author: YangGC
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringDataESIndexTest {
    //注入 ElasticsearchRestTemplate
    @Resource
    private ProductDao productDao;


    /**
     * ① ElasticsearchRestTemple是ElasticsearchOperations的子类的子类
     * ② 在ES7.x以下的版本使用的是ElasticsearchTemple，7.x以上版本已弃用ElasticsearchTemple，使用ElasticsearchRestTemple替代
     */
    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    //创建索引并增加映射配置
    @Test
    public void createIndex(){
        //创建索引，系统初始化会自动创建索引
        System.out.println("创建索引");
    }


    @Test
    public void deleteIndex(){
        //创建索引，系统初始化会自动创建索引
        String delete = elasticsearchOperations.delete(Product.class);
        System.out.println("删除索引 delete = " + delete);

    }


    /**
     * 新增
     * POSTMAN, GET http://localhost:9200/product/_doc/2
     */
    @Test
    public void save(){
        Product product = new Product();
        product.setId(2L);
        product.setTitle("华为手机");
        product.setCategory("手机");
        product.setPrice(2999.0);
        product.setImages("http://www.atguigu/hw.jpg");
        productDao.save(product);
    }

    @Test
    public void update(){
        Product product = new Product();
        product.setId(2L);
        product.setTitle("小米 2 手机");
        product.setCategory("手机");
        product.setPrice(9999.0);
        product.setImages("http://www.baidu.com/123.jpg");
        productDao.save(product);
    }
    //根据 id 查询
    @Test
    public void findById(){
        Product product = productDao.findById(2L).get();
        System.out.println(product);
    }

    @Test
    public void findAll(){
        Iterable<Product> products = productDao.findAll();
        for (Product product : products) {
            System.out.println(product);
        }
    }

    /**
     * 删除
     */
    @Test
    public void delete(){
        Product product = new Product();
        product.setId(2L);
        productDao.delete(product);
    }

    //批量新增
    @Test
    public void saveAll(){
        List<Product> productList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            product.setId(Long.valueOf(i));
            product.setTitle("["+i+"]小米手机");
            product.setCategory("手机");
            product.setPrice(1999.0 + i);
            product.setImages("http://www.baidu.com/123.jpg");
            productList.add(product);
        }
        productDao.saveAll(productList);
    }
    //分页查询
    @Test
    public void findByPageable(){
        //设置排序(排序方式，正序还是倒序，排序的 id)
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        int currentPage=0;//当前页，第一页从 0 开始， 1 表示第二页
        int pageSize = 5;//每页显示多少条
        //设置查询分页
        PageRequest pageRequest = PageRequest.of(currentPage, pageSize,sort);
        //分页查询
        Page<Product> productPage = productDao.findAll(pageRequest);
        for (Product Product : productPage.getContent()) {
            System.out.println(Product);
        }
    }






}
