#!/bin/bash

## build
mvn clean install

## shutdown
lsof -i:8088 | awk '/java/ {print $2}'|xargs kill -9

sleep 1

## check pid stopped
lsof -i:8088

## backup log
subfix=`date '+%Y%m%d%H%M%S'`
mv liren.log liren.log.${subfix}
echo 'log is already backuped to ', liren.log.${subfix}

## starup
nohup java -jar ./target/liren-admin-0.0.1-SNAPSHOT.jar &>liren.log &

tail -f liren.log