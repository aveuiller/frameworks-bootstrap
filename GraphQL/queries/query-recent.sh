#!/bin/bash

set -x

curl -i -X POST "http://localhost:8080/graphql" \
  -d "{\"query\": \"$(sed "s/\"/\\\\\"/g" query-recent.graphql | tr -d '\n')\", \"variables\": {\"count\": 20}}"
