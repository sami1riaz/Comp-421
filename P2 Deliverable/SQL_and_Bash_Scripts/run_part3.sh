#! /bin/bash

LOG="part3.log"

if [ "$1" != "" ] 
then
	LOG=$1"/"$LOG
fi

printf "COMP421 Project Deliverable #2 Part 3\n" | tee $LOG

#Run part3_setup.sql and log output
/bin/bash execute_sql.sh "sql/part3_setup.sql" $LOG 

#Run part3_insert.sql and log output
/bin/bash execute_sql.sh "sql/part3_insert.sql" $LOG "--echo-all"
