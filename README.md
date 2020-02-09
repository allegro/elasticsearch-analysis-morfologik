# Morfologik Polish Lemmatizer plugin for Elasticsearch #
[![Build Status](https://travis-ci.org/allegro/elasticsearch-analysis-morfologik.svg?branch=master)](https://travis-ci.org/allegro/elasticsearch-analysis-morfologik)
[![Maven Central](https://img.shields.io/maven-metadata/v/https/repo1.maven.org/maven2/pl/allegro/tech/elasticsearch/plugin/elasticsearch-analysis-morfologik/maven-metadata.xml.svg)](https://repo1.maven.org/maven2/pl/allegro/tech/elasticsearch/plugin/elasticsearch-analysis-morfologik/)

Morfologik plugin for elasticsearch 7.x, 6.x, 5.x and 2.x. It's lucene-analyzers-morfologik wrapper for elasticsearch.

Plugin provide "morfologik" analyzer and "morfologik_stem" token filter.

Originally created by https://github.com/monterail/elasticsearch-analysis-morfologik

## Install
  
```
bin/elasticsearch-plugin install pl.allegro.tech.elasticsearch.plugin:elasticsearch-analysis-morfologik:7.5.2
```

*tip: select proper plugin version, should be the same as elasticsearch version*

## Changelog:
version 7.5.3 (version not yet released):
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

### Elasticsearch 7.x
- 7.5.x (7.5.0, 7.5.1, 7.5.2)
- 7.4.x (7.4.0, 7.4.1, 7.4.2)
- 7.3.x (7.3.2)

### Elasticsearch 6.x
- 6.0.x (6.0.0, 6.0.1)
- 6.1.x (6.1.0, 6.1.1, 6.1.2, 6.1.3, 6.1.4)
- 6.2.x (6.2.1, 6.2.2, 6.2.3, 6.2.4)
- 6.3.x (6.3.0, 6.3.1, 6.3.2)
- 6.4.x (6.4.0, 6.4.1, 6.4.2, 6.4.3)
- 6.5.x (6.5.0, 6.5.1, 6.5.2, 6.5.3, 6.5.4)
- 6.6.x (6.6.0, 6.6.1, 6.6.2)
- 6.7.x (6.7.1, 6.7.2)
- 6.8.x (6.8.0, 6.8.1, 6.8.2, 6.8.3, 6.8.4)

### Elasticsearch 5.x
- 5.0.x (5.0.0, 5.0.1, 5.0.2)
- 5.1.x (5.1.1, 5.1.2)
- 5.2.x (5.2.0, 5.2.1, 5.2.2)
- 5.3.x (5.3.0, 5.3.1, 5.3.2, 5.3.3)
- 5.4.x (5.4.0, 5.4.1, 5.4.2, 5.4.3)
- 5.5.x (5.5.0, 5.5.1, 5.5.2)
- 5.6.x (5.6.0, 5.6.1, 5.6.2, 5.6.3, 5.6.4, 5.6.5, 5.6.10, 5.6.16)

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

