#!/bin/sh

MONGO_URI=mongodb://breathelive_mongo:27017/measurements

java -jar -Dspring.data.mongodb.uri=$MONGO_URI -Djava.security.egd=file:/dev/./urandom /api.jar