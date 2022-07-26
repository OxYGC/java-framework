-- 在ES进行index

PUT /entry_record
{
    "mappings" : {
        "properties" : {
          "arrival_mode" : {
            "type" : "text"
          },
          "card_detail_id" : {
            "type" : "long"
          },
          "card_id" : {
            "type" : "long"
          },
          "card_name" : {
            "type" : "text"
          },
          "club_id" : {
            "type" : "long"
          },
          "end_time" : {
            "type" : "date",
            "format": "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
          },
          "face_img" : {
            "type" : "text"
          },
          "member_be_present_id" : {
            "type" : "long"
          },
          "member_id" : {
            "type" : "long"
          },
          "member_type" : {
            "type" : "long"
          },
          "mobile" : {
            "type" : "text"
          },
          "name" : {
            "type" : "text"
          },
          "remark" : {
            "type" : "text"
          },
          "store_id" : {
            "type" : "long"
          },
          "store_name" : {
            "type" : "text"
          },
          "stu" : {
            "type" : "long"
          },
          "time" : {
            "type" : "date",
            "format": "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
          }
        }
      }
}




-- 注意字段 scan.startup.mode

CREATE TABLE saas_member_be_present (
                                        `member_be_present_id` BIGINT ,
                                        `member_id` BIGINT ,
                                        `store_id` BIGINT ,
                                        `name` STRING,
                                        `mobile` STRING,
                                        `arrival_mode` STRING,
                                        `face_img` STRING,
                                        `stu` int ,
                                        `time` TIMESTAMP  ,
                                        `club_id` BIGINT ,
                                        `end_time` TIMESTAMP  ,
                                        `card_id` BIGINT ,
                                        `card_name` STRING,
                                        `store_name` STRING,
                                        `card_detail_id` BIGINT ,
                                        `remark` STRING,
                                        `member_type` INT,
                                        PRIMARY KEY (member_be_present_id) NOT ENFORCED
) WITH (
      'connector' = 'mysql-cdc',
      'hostname' = 'xxx',
      'port' = '3306',
      'username' = 'ro',
      'password' = 'xxx',
      'scan.startup.mode' = 'latest-offset',
      'database-name' = 'ldd_new',
      'table-name' = 'saas_member_be_present'
      );



-- 创建 enriched_orders 表

CREATE TABLE entry_record (
                              `member_be_present_id` BIGINT ,
                              `member_id` BIGINT ,
                              `store_id` BIGINT ,
                              `name` STRING,
                              `mobile` STRING,
                              `arrival_mode` STRING,
                              `face_img` STRING,
                              `stu` int ,
                              `time` TIMESTAMP  ,
                              `club_id` BIGINT ,
                              `end_time` TIMESTAMP  ,
                              `card_id` BIGINT ,
                              `card_name` STRING,
                              `store_name` STRING,
                              `card_detail_id` BIGINT ,
                              `remark` STRING,
                              `member_type` INT,
                              PRIMARY KEY (member_be_present_id) NOT ENFORCED
) WITH (
      'connector' = 'elasticsearch-7',
      'hosts' = 'http://10.10.10.124:9200',
      'index' = 'entry_record'
      );

insert into entry_record
select * from saas_member_be_present;


