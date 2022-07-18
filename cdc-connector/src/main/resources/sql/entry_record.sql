CREATE TABLE saas_member_be_present
(
    `member_be_present_id` BIGINT,
    `member_id`            BIGINT,
    `store_id`             BIGINT,
    `name`                 STRING,
    `mobile`               STRING,
    `arrival_mode`         STRING,
    `face_img`             STRING,
    `stu`                  int,
    `time`                 TIMESTAMP,
    `club_id`              BIGINT,
    `end_time`             TIMESTAMP,
    `card_id`              BIGINT,
    `card_name`            STRING,
    `store_name`           STRING,
    `card_detail_id`       BIGINT,
    `remark`               STRING,
    `member_type`          INT,
    PRIMARY KEY (member_be_present_id) NOT ENFORCED
) WITH (
      'connector' = 'mysql-cdc',
      'hostname' = '192.168.1.220',
      'port' = '3308',
      'username' = 'root',
      'password' = 'useradmin',
      'database-name' = 'ldd_new',
      'table-name' = 'saas_member_be_present'
      );


-- 创建 enriched_orders 表
CREATE TABLE entry_record
(
    `member_be_present_id` BIGINT,
    `member_id`            BIGINT,
    `store_id`             BIGINT,
    `name`                 STRING,
    `mobile`               STRING,
    `arrival_mode`         STRING,
    `face_img`             STRING,
    `stu`                  int,
    `time`                 TIMESTAMP,
    `club_id`              BIGINT,
    `end_time`             TIMESTAMP,
    `card_id`              BIGINT,
    `card_name`            STRING,
    `store_name`           STRING,
    `card_detail_id`       BIGINT,
    `remark`               STRING,
    `member_type`          INT,
    PRIMARY KEY (member_be_present_id) NOT ENFORCED
) WITH (
      'connector' = 'elasticsearch-7',
      'hosts' = 'http://10.10.10.124:9200',
      'index' = 'entry_record'
      );


insert into entry_record
select *
from saas_member_be_present;