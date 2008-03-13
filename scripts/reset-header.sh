#!/bin/sh
java -classpath bin util.UpdateHeader `find src/ -name '*.java'`
