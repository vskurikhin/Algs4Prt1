#!/bin/sh
JAR=Algs4Prt18puzzle.jar
if [ "x" != "x$1" ] ; then
  java -jar dist/${JAR} $*
else
  for F in 8puzzle/*.txt ; do
     echo $F
     java -jar dist/${JAR} $F|egrep -e 'Minimum number of moves|No solution possible'
     sleep 1
  done
fi
