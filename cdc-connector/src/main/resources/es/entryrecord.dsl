# 查看某会员今年的进店数量
POST /entry_record/_search
{
  "size": 0,
  "runtime_mappings": {
    "day_of_week": {
      "type": "keyword",
      "script": "emit(doc['time'].value.dayOfWeekEnum.getDisplayName(TextStyle.FULL,Locale.ROOT))"
    },
    "time": {
      "type": "date",
      "format": "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
    }
  },
  "query": {
    "bool": {
      "must": [
        {"match": {
          "member_id": "1371295748158787584"
        }}
      ],
      "filter": [
        {"range": {
          "time": {
            "gte": "2021-01-01 00:00:00",
            "lte": "2022-01-01 00:00:00"
          }
        }}
      ]
    }
  }
}



# 查看某个门每月的进店人次(按照时间进行分组)
POST /entry_record/_search
{
  "size": 0,
  "runtime_mappings": {
    "day_of_week": {
      "type": "keyword",
      "script": "emit(doc['time'].value.dayOfWeekEnum.getDisplayName(TextStyle.FULL,Locale.ROOT))"
    },
    "time": {
      "type": "date",
      "format": "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
    }
  },
  "query": {
    "bool": {
      "must": [
        {"match": {
          "store_id": "1371278393940774912"
        }}
      ]
    }
  },
  "aggs": {
    "group-store": {
      "date_histogram": {
        "field": "time",
        "calendar_interval": "month",
        "format": "yyyy-MM-dd"
      }
    }
  }
}



-- 七月份门店的MAU
POST /entry_record_v1/_search
{
  "size": 0,
  "query": {
    "range": {
      "time": {
        "gte": "2022-07-01 00:00:00",
        "lte": "2022-07-31 00:00:00"
      }
    }
  },
  "aggs": {
    "group-store": {
      "terms": {
        "field": "store_id"
      }
    }
  }
}


# DB对照查询
select r1.store_Id, COUNT(r1.member_be_present_id) FROM
(
	SELECT member_be_present_id,store_id FROM `saas_member_be_present` WHERE time BETWEEN "2022-07-01 00:00:00" AND "2022-07-31 00:00:00"
) r1 GROUP BY r1.store_Id ORDER BY COUNT(r1.member_be_present_id) DESC


# 查看门店每个月的进店数量
POST /entry_record_v1/_search
{
  "size": 0,
  "query": {
    "bool": {
      "must": [
        {"match": {
          "store_id": "1394889520880685056"
        }}
      ]
    }
  },
  "aggs": {
    "group-store": {
      "date_histogram": {
        "field": "time",
        "calendar_interval": "month",
        "format": "yyyy-MM-dd"
      }
    }
  }
}

# DB对照
SELECT
	COUNT(store_Id) as cnt, date_format( r1.time, '%Y-%m' ) AS date
FROM
(SELECT store_id,time FROM saas_member_be_present WHERE store_id = 1394889520880685056) AS r1

GROUP BY
	date_format( r1.time, '%Y-%m' ),store_Id
ORDER BY
	date_format( r1.time, '%Y-%m') DESC ,cnt DESC


# topN 每个门店进店次数最多的3个人
GET /entry_record_v1/_search
{
  "size": 0,
  "aggs": {
    "group_by_store": {
      "terms": {
        "field": "store_id"
      },
      "aggs": {
        "group_by_member_id":{
          "terms": {
            "field": "member_id",
            "size": 3
          }
        }
      }
    }
  }
}
DB对照
SELECT member_id,COUNT(member_be_present_id) AS cnt FROM `saas_member_be_present` WHERE store_Id = 1394889520880685056 GROUP BY member_id ORDER BY cnt DESC











