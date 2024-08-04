#!/bin/bash

set -e

kubectl apply -f namespace-dev.yml

for dir in postgres reactive-movie sink ingress; do
  kubectl apply -f "$dir"  || exit 1
  echo -e  "\033[32m$dir\033[0m" "[" "\033[32mOk\033[0m" "]"
done
