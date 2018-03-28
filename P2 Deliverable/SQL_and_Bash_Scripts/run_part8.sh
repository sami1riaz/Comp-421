#! /bin/bash

LOG="part8.log"

if [ "$1" != "" ] 
then
	LOG=$1"/"$LOG
fi

printf "COMP421 Project Deliverable #2 Part 8\n" | tee $LOG

#Run part8_check1.sql and log output
/bin/bash execute_sql.sh "sql/part8_check1.sql" $LOG "--echo-all"

#Run part8_check2.sql and log output
/bin/bash execute_sql.sh "sql/part8_check2.sql" $LOG "--echo-all"
