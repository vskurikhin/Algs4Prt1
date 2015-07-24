#!/bin/sh
JAR=Algs4Prt1PatternRecognition.jar
if [ "x" != "x$1" ] ; then
  java -jar dist/${JAR} $*
else
  for F in collinear/*.txt ; do
     echo $F
     java -jar dist/${JAR} $F
     sleep 15
  done
fi
