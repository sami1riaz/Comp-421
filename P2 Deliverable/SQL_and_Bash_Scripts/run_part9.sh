#! /bin/bash

LOG="part9.log"

if [ "$1" != "" ] 
then
	LOG=$1"/"$LOG
fi

printf "COMP421 Project Deliverable #2 Part 9\n" | tee $LOG

#Run part9_queries.sql and log output
/bin/bash execute_sql.sh "sql/part9_complex_query.sql" $LOG "--echo-all"
