project=$1

echo "*** Clean containers ***"
docker stop $project
docker rm $project

echo "*** Clean images via Image ID ***"
docker images | grep $project | awk '{print $3}' | xargs docker rmi -f

echo "*** Generate tag via timestamp ***"
hash=`echo $GIT_COMMIT | cut -c1-8`
ts=`date +%Y%m%d.%H%M%S`
tag="${ts}_$hash"

echo "*** Build docker images ***"
docker build -t $project:$tag -f $WORKSPACE/docker/Dockerfile $WORKSPACE

if [ $? -ne 0 ]; then
    echo "Failed to build docker image"
    exit 2
fi

echo "*** Tag and push docker images ***"
docker tag $project:$tag 192.168.0.20:7899/$project:$tag
docker push 192.168.0.20:7899/$project:$tag

if [ $? -ne 0 ]; then
    echo "Failed to push docker image"
    exit 3
fi

echo "*** Push git tag/tmp branch ***"
git tag -a build-$tag -m "Built on $tag"
git checkout -b br-$tag

git push origin --tags
git push origin br-$tag

if [ $? -ne 0 ]; then
    echo "Failed to push git tag/tmp branch"
    exit 4
fi

echo "*** Publish the service ***"
# docker run --name $project -d -p 9000:8080 $project:$tag
