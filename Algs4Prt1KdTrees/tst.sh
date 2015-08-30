#!/bin/sh
LIBS=/usr/local/lib/java
CLASSES=build/classes
JUNIT=${LIBS}/junit.jar
STDLIB=${LIBS}/stdlib.jar
HAMCREST=${LIBS}/hamcrest-core.jar
findbugs-algs4 build/classes/KdTree.class 'build/classes/KdTree$Node.class' build/classes/RectHV.class
for F in ${CLASSES}/*Tests*.class ; do
   echo $F
   T=${F%%.class}
   C=${T##*/}
   echo $C
   java -cp .:${JUNIT}:${HAMCREST}:${STDLIB}:${CLASSES} org.junit.runner.JUnitCore ${C}
   sleep 1
done
