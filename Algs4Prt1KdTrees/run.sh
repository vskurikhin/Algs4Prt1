#!/bin/sh
JAR=Algs4Prt1KdTrees.jar
if [ "x" != "x$1" ] ; then
  java -jar dist/${JAR} $*
else
  for F in 8puzzle/*.txt ; do
     echo $F
     java -Xms128m -Xmx256m -XX:PermSize=256M -jar dist/${JAR} $F|
     egrep -e 'Minimum number of moves|No solution possible'
     sleep 1
  done
fi
