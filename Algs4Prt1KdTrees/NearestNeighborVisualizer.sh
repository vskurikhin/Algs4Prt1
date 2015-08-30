#!/bin/sh
LIBS=/usr/local/lib/java
CLASSES=build/classes
JUNIT=${LIBS}/junit.jar
STDLIB=${LIBS}/stdlib.jar
HAMCREST=${LIBS}/hamcrest-core.jar
java-algs4 -Xms128m -Xmx256m -XX:PermSize=256M -cp .:${JUNIT}:${HAMCREST}:${STDLIB}:${CLASSES} NearestNeighborVisualizer $*
