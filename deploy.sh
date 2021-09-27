#!/bin/bash

HOME_DIR=/home/ec2-user

SRC_DIR=$HOME_DIR/pepple

JAR_DIR=$HOME_DIR/pepple/build/libs

LOG_DIR=$HOME_DIR/logs

PROJECT_NAME=Pepple

CURRENT_PID=$(pgrep -f ${PROJECT_NAME}.*.jar)

echo "현재 구동중인 어플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
	echo "> 현재 구동중인 어플리케이션이 없으므로 종료하지 않습니다."
else
	echo ">kill -9 $CURRENT_PID"
	kill -9 $CURRENT_PID
	sleep 5
fi

echo ">새 에플리케이션 배포"

sleep 3

cd $SRC_DIR

git pull

echo "> git pull"

echo "빌드 시작"

sh ./gradlew clean bootjar

sleep 3

cd $JAR_DIR

JAR_NAME=$(ls -tr . | grep jar | tail -n 1)

echo ">JAR Name: $JAR_NAME"

nohup java -jar -Xms512m -Xmx512m $JAR_NAME > $LOG_DIR/log.out 2>&1 &