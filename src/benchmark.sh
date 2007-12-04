#!/bin/bash


if [ $# -lt 2 ]
then
    echo "The usage is ./benchmark.sh <number of trials> <text file to count>"
    exit 1
fi

count=0
totalusr=0
totalsys=0
totalreal=0

#file used to perform counting 
file=$2

if  [ ! -e $file ] 
then
    echo "The file '$file' does not exist."
    exit 1
fi

temp=`mktmp`

while [ $count -lt $1 ]
do
  real=`time ./count.sh $file 2>$temp`
  #time=`cat $temp | grep real`
  echo "$real"
  ((count=count+1))
done

rm -f $temp


exit 0
