#!/bin/sh
JAR=Algs4Prt18puzzle.jar
if [ "x" != "x$1" ] ; then
  java -jar dist/${JAR} $*
else
  for F in 8puzzle.none/*.txt ; do
     echo $F
     java -jar dist/${JAR} $F
     sleep 5
  done
fi
