# Morfologik Polish Lemmatizer plugin for Elasticsearch #
[![Maven Central](https://img.shields.io/maven-metadata/v/https/repo1.maven.org/maven2/pl/allegro/tech/elasticsearch/plugin/elasticsearch-analysis-morfologik/maven-metadata.xml.svg)](https://repo1.maven.org/maven2/pl/allegro/tech/elasticsearch/plugin/elasticsearch-analysis-morfologik/)

Morfologik plugin for elasticsearch 8.x, 7.x, 6.x, 5.x and 2.x. It's lucene-analyzers-morfologik wrapper for elasticsearch.

Plugin provide "morfologik" analyzer and "morfologik_stem" token filter.

Originally created by https://github.com/monterail/elasticsearch-analysis-morfologik

## Install
  
```
bin/elasticsearch-plugin install pl.allegro.tech.elasticsearch.plugin:elasticsearch-analysis-morfologik:9.0.3
```

*tip: select proper plugin version, should be the same as elasticsearch version*

## Changelog:
version 7.6.0
 - add support for custom morfologik dictionary eg. `{"type": "morfologik_stem", "dictionary": "polish-wo-brev.dict"}`

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
  "filter": ["morfologik_stem"],
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

All ready to install plugins are deployed to [maven central](https://repo1.maven.org/maven2/pl/allegro/tech/elasticsearch/plugin/elasticsearch-analysis-morfologik/).

### Elasticsearch 9.x
- 9.0.x (9.0.0, 9.0.1, 9.0.2, 9.0.3)

### Elasticsearch 8.x
- 8.18.x (8.18.0, 8.18.2)
- 8.17.x (8.17.0, 8.17.1, 8.17.2, 8.17.3, 8.17.4, 8.17.5, 8.17.7)
- 8.16.x (8.16.0, 8.16.1)
- 8.15.x (8.15.0, 8.15.1, 8.15.2, 8.15.3, 8.15.4)
- 8.14.x (8.14.1, 8.14.3)
- 8.13.x (8.13.1, 8.13.2, 8.13.3, 8.13.4)
- 8.12.x (8.12.0, 8.12.1, 8.12.2)
- 8.11.x (8.11.0, 8.11.1, 8.11.2, 8.11.3, 8.11.4)
- 8.10.x (8.10.4)
- 8.9.x (8.9.0)
- 8.8.x (8.8.0, 8.8.1, 8.8.2)
- 8.7.x (8.7.0, 8.7.1)
- 8.6.x (8.6.0, 8.6.1, 8.6.2)
- 8.5.x (8.5.0, 8.5.1, 8.5.2, 8.5.3)
- 8.4.x (8.4.1, 8.4.2, 8.4.3)
- 8.3.x (8.3.1, 8.3.2, 8.3.3)
- 8.2.x (8.2.0, 8.2.1, 8.2.2, 8.2.3)
- 8.1.x (8.1.0, 8.1.1, 8.1.2, 8.1.3) 
- 8.0.x (8.0.0, 8.0.1)

### Elasticsearch 7.x
- 7.17.x (7.17.0, 7.17.3, 7.17.4, 7.17.5, 7.17.6, 7.17.7, 7.17.8, 7.17.9, 7.17.10, 7.17.11, 7.17.14, 7.17.22)
- 7.16.x (7.16.1, 7.16.2.1, 7.16.3)
- 7.10.x (7.10.0, 7.10.1, 7.10.2)
- 7.9.x (7.9.0, 7.9.1, 7.9.2, 7.9.3)
- 7.8.x (7.8.0, 7.8.1)
- 7.7.x (7.7.0, 7.7.1)
- 7.6.x (7.6.0, 7.6.1, 7.6.2)
- 7.5.x (7.5.0, 7.5.1, 7.5.2)
- 7.4.x (7.4.0, 7.4.1, 7.4.2)
- 7.3.x (7.3.2)

### Elasticsearch 6.x
- 6.8.x (6.8.0, 6.8.1, 6.8.2, 6.8.3, 6.8.4, 6.8.6, 6.8.8, 6.8.9, 6.8.10, 6.8.11, 6.8.12, 6.8.23)
- 6.7.x (6.7.1, 6.7.2)
- 6.6.x (6.6.0, 6.6.1, 6.6.2)
- 6.5.x (6.5.0, 6.5.1, 6.5.2, 6.5.3, 6.5.4)
- 6.4.x (6.4.0, 6.4.1, 6.4.2, 6.4.3)
- 6.3.x (6.3.0, 6.3.1, 6.3.2)
- 6.2.x (6.2.1, 6.2.2, 6.2.3, 6.2.4)
- 6.1.x (6.1.0, 6.1.1, 6.1.2, 6.1.3, 6.1.4)
- 6.0.x (6.0.0, 6.0.1)

### Elasticsearch 5.x
- 5.6.x (5.6.0, 5.6.1, 5.6.2, 5.6.3, 5.6.4, 5.6.5, 5.6.10, 5.6.16)
- 5.5.x (5.5.0, 5.5.1, 5.5.2)
- 5.4.x (5.4.0, 5.4.1, 5.4.2, 5.4.3)
- 5.3.x (5.3.0, 5.3.1, 5.3.2, 5.3.3)
- 5.2.x (5.2.0, 5.2.1, 5.2.2)
- 5.1.x (5.1.1, 5.1.2)
- 5.0.x (5.0.0, 5.0.1, 5.0.2)

### Elasticsearch 2.x
- 2.4.x (2.4.1, 2.4.2, 2.4.3, 2.4.4, 2.4.6)


#### Install in Elasticsearch for version <= 5.4.0
 
```
bin/elasticsearch-plugin install \
  https://repo1.maven.org/maven2/pl/allegro/tech/elasticsearch/plugin/elasticsearch-analysis-morfologik/5.4.0/elasticsearch-analysis-morfologik-5.4.0-plugin.zip
```

*tip: select proper plugin version, should be the same as elasticsearch version*


#### Install in Elasticsearch 2.x
```
bin/plugin install \
  https://repo1.maven.org/maven2/pl/allegro/tech/elasticsearch/plugin/elasticsearch-analysis-morfologik/2.4.2/elasticsearch-analysis-morfologik-2.4.2-plugin.zip
```
*tip: select proper plugin version, should be the same as elasticsearch version*

## Build ##

`./gradlew clean build`

