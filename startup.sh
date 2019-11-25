#!/bin/bash

## build
mvn clean install

## shutdown
lsof -i:8088 | awk '/java/ {print $2}'|xargs kill -9

## starup
nohup java -jar ./target/liren-admin-0.0.1-SNAPSHOT.jar &>liren.log &

tail -f liren.log