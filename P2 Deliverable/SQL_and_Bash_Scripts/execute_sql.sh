#! /bin/bash

SQL_FILE=$1
LOG_FILE=$2
ECHO_ALL=$3

printf " executing $SQL_FILE\n"
printf "\npsql cs421 < $SQL_FILE\n\n" >> $LOG_FILE

if [ ECHO_ALL != "" ] 
then
	psql $ECHO_ALL cs421 < $SQL_FILE >> $LOG_FILE 2>&1
else 
	psql  cs421 < $SQL_FILE >> $LOG_FILE 2>&1
fi
