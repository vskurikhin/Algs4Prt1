#!/bin/sh
JAR=Algs4Prt1RandQueuesDeques.jar
if [ "x" != "x$1" ] ; then
  java -jar dist/${JAR} $*
else
  for F in percolation/*.txt ; do
     echo $F
     java -jar dist/${JAR} $F
     sleep 15
  done
fi
