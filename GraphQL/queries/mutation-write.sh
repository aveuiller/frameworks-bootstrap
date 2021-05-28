#!/bin/bash

set -x

curl -i -X POST "http://localhost:8080/graphql" \
  -d "{\"query\": \"$(sed "s/\"/\\\\\"/g" mutation-write.graphql | tr -d '\n')\", \"variables\": {\"title\": \"Hello World\"}}"
