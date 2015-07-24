#!/bin/sh
java -jar dist/Algs4_Prt1_Percolation.jar
exit 0
if [ "x" != "x$1" ] ; then
  java -jar dist/Algs4_Prt1_Percolation.jar $1 200 100
else
  #java -jar dist/Algs4_Prt1_Percolation.jar percolation/input1.txt 200 100
  #java -jar dist/Algs4_Prt1_Percolation.jar percolation/input10.txt 200 100
  for F in percolation/*.txt ; do
     echo $F
     java -jar dist/Algs4_Prt1_Percolation.jar $F 200 100
     sleep 15
  done
fi
#input1.txt
#input10.txt
#input2.txt
#input20.txt