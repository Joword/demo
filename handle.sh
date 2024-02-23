#!/bin/sh

echo -n "请输入操作(deploy(d)/run(r)):"
read handle
echo -n "请输入版本号:"
read version
if [ $handle=='deploy' ]; then
    docker build -t aigi_python_image:$version ./
elif [ $handle=='d' ]; then
    docker build -t aigi_python_image:$version ./
elif [ $handle=='run' ]; then
    docker run -d --name aigipython  -v /home/mengjunhua/project/aigi-python:/app --privileged=true  -p 9670:9670 aigi_python_image
elif [ $handle=='r' ]; then
    docker run -d --name aigipython  -v /home/mengjunhua/project/aigi-python:/app --privileged=true  -p 9670:9670 aigi_python_image
else
    echo "输入不符合要求"
fi