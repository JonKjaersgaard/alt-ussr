#!/bin/sh

cd ../../
n=1
while [ $n -le 30 ]; do
  echo $n
  make -f ./home/rimen05/makefile atron >> benchmark.txt
  let n++
done
mv benchmark.txt ./home/rimen05/
