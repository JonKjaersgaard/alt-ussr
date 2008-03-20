#!/bin/sh

cd ../../
n=1
while [ $n -le 30 ]; do
  make -f ./home/rimen05/makefile atron
  let n++
done
