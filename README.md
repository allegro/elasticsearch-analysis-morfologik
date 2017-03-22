# Morfologik Polish Lemmatizer plugin for Elasticsearch #
[![Build Status](https://travis-ci.org/allegro/elasticsearch-analysis-morfologik.svg?branch=master)](https://travis-ci.org/allegro/elasticsearch-analysis-morfologik)

Morfologik plugin for elasticsearch 2.x, lucene-analyzers-morfologik wrapper for elasticsearch.

Plugin provide "morfologik" analyzer and "morfologik_stem" token filter.

Originally created by https://github.com/monterail/elasticsearch-analysis-morfologik

## Build ##

`./gradlew clean build`

## Examples ## 

### "morfologik" analyzer ###
Request:
```
GET _analyze
{
  "analyzer": "morfologik",
  "text": "jestem"
}
```
Response:
```
{
  "tokens": [
    {
      "token": "być",
      "start_offset": 0,
      "end_offset": 6,
      "type": "<ALPHANUM>",
      "position": 0
    }
  ]
}
```

### "morfologik_stem" token filter ###
Request:
```
GET _analyze
{
  "tokenizer": "standard",
  "token_filter": ["morfologik_stem"],
  "text": "jestem"
}
```
Response:
```
{
  "tokens": [
    {
      "token": "być",
      "start_offset": 0,
      "end_offset": 6,
      "type": "<ALPHANUM>",
      "position": 0
    }
  ]
}
```

## Supported elasticsearch versions: ##
- 2.4.1