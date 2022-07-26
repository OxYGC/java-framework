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
