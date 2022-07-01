# 创建索引
PUT /test_user
{
	"mappings": {
		"properties": {

			"user_name": {
				"type": "text"
			},
			"age": {
				"type": "integer"
			},
			"address": {
				"type": "text"
			},
			"role_id": {
				"type": "long"
			},
			"gmt_create": {
				"type": "date"
			}
		}
	}
}


# 新增字段
PUT /test_user/_mapping
{
		"properties": {
		 "test_user_id": {
			"type": "long"
		}
		}
}


GET /test_user/_search

# 更新doc中数据
PUT test_user/_doc/2
{
  "doc":{
    "user_name":"我是用户2"
  }
}




