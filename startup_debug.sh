#!/bin/bash

## build
mvn clean install -Dmaven.test.skip=true

## shutdown
lsof -i:8088 | awk '/java/ {print $2}'|xargs kill -9

sleep 1

## check pid stopped
lsof -i:8088

## backup log
subfix=`date '+%Y%m%d%H%M%S'`
mv liren_debug.log liren_debug.log.${subfix}
echo 'log is already backuped to ', liren_debug.log.${subfix}

## starup
nohup java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8000 -jar ./target/liren-admin-0.0.1-SNAPSHOT.jar &>liren_debug.log &

tail -f liren_debug.log
