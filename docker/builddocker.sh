#!/bin/sh

cd ..
find . -iname "api-*.jar" -exec cp {} docker/api.jar \;
cd docker
docker build . -t breathelive:latest
docker tag breathelive:latest lukaszwelnicki/breathelive:latest
docker login --username $DOCKER_USERNAME --password $DOCKER_PASSWORD
docker push lukaszwelnicki/breathelive:latest
rm api.jar