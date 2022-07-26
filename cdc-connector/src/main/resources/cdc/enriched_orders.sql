CREATE TABLE products
(
    id          INT,
    name        STRING,
    description STRING,
    PRIMARY KEY (id) NOT ENFORCED
) WITH (
      'connector' = 'mysql-cdc',
      'hostname' = '192.168.1.220',
      'port' = '3308',
      'username' = 'root',
      'password' = 'useradmin',
      'database-name' = 'flinkcdc',
      'table-name' = 'products'
      );

CREATE TABLE orders
(
    order_id      INT,
    order_date    TIMESTAMP(0),
    customer_name STRING,
    price         DECIMAL(10, 5),
    product_id    INT,
    order_status  BOOLEAN,
    PRIMARY KEY (order_id) NOT ENFORCED
) WITH (
      'connector' = 'mysql-cdc',
      'hostname' = '192.168.1.220',
      'port' = '3308',
      'username' = 'root',
      'password' = 'useradmin',
      'database-name' = 'flinkcdc',
      'table-name' = 'orders'
      );


-- 创建 enriched_orders 表
CREATE TABLE enriched_orders
(
    order_id            INT,
    order_date          TIMESTAMP(0),
    customer_name       STRING,
    price               DECIMAL(10, 5),
    product_id          INT,
    order_status        BOOLEAN,
    product_name        STRING,
    product_description STRING,
    PRIMARY KEY (order_id) NOT ENFORCED
) WITH (
      'connector' = 'elasticsearch-7',
      'hosts' = 'http://192.168.1.71:9200',
      'index' = 'enriched_orders'
      );


insert into enriched_orders
select o.order_id      as order_id,
       o.order_date    as order_date,
       o.customer_name as customer_name,
       o.price         as price,
       o.product_id    as product_id,
       o.order_status  as order_status,
       p.name          as product_name,
       p.description   as product_description
from orders as o
left join products as p on o.product_id = p.id;