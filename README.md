# Morfologik Polish Lemmatizer plugin for Elasticsearch #
[![Build Status](https://travis-ci.org/allegro/elasticsearch-analysis-morfologik.svg?branch=master)](https://travis-ci.org/allegro/elasticsearch-analysis-morfologik)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/pl.allegro.tech.elasticsearch.plugin/elasticsearch-analysis-morfologik/badge.svg)](http://central.maven.org/maven2/pl/allegro/tech/elasticsearch/plugin/elasticsearch-analysis-morfologik/)

Morfologik plugin for elasticsearch 5.x and 2.x. It's lucene-analyzers-morfologik wrapper for elasticsearch.

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

All ready to install plugins are deployed to maven central:
http://central.maven.org/maven2/pl/allegro/tech/elasticsearch/plugin/elasticsearch-analysis-morfologik/

### Elasticsearch 5.x
- 5.0.x (5.0.0, 5.0.1, 5.0.2)
- 5.1.x (5.1.1, 5.1.2)
- 5.2.x (5.2.0, 5.2.1, 5.2.2)
- 5.3.x (5.3.0, 5.3.1, 5.3.2, 5.3.3)
- 5.4.x (5.4.0, 5.4.1, 5.4.2, 5.4.3)
- 5.5.x (5.5.0, 5.5.1, 5.5.2)
- 5.6.x (5.6.0, 5.6.1, 5.6.2)
- 6.0.x (6.0.0)

#### Install in Elasticsearch 5.x
version >= 5.4.1 
```
<ES directory>/bin/elasticsearch-plugin install \
  pl.allegro.tech.elasticsearch.plugin:elasticsearch-analysis-morfologik:5.5.2
```
version <= 5.4.0 
```
<ES directory>/bin/elasticsearch-plugin install \
  http://central.maven.org/maven2/pl/allegro/tech/elasticsearch/plugin/elasticsearch-analysis-morfologik/5.3.1/elasticsearch-analysis-morfologik-5.3.1-plugin.zip
```
*tip: select proper version in url to plugin*

### Elasticsearch 2.x
- 2.4.x (2.4.1, 2.4.2, 2.4.3, 2.4.4, 2.4.6)

#### Install in Elasticsearch 2.x
```
<ES directory>/bin/plugin install \
  http://central.maven.org/maven2/pl/allegro/tech/elasticsearch/plugin/elasticsearch-analysis-morfologik/2.4.2/elasticsearch-analysis-morfologik-2.4.2-plugin.zip
```
*tip: select proper version in url to plugin*