#! /bin/bash

LOG="part7.log"

if [ "$1" != "" ] 
then
	LOG=$1"/"$LOG
fi

printf "COMP421 Project Deliverable #2 Part 7\n" | tee $LOG

#Run part7_view1.sql and log output
/bin/bash execute_sql.sh "sql/part7_view1.sql" $LOG "--echo-all"

#Run part7_view2.sql and log output
/bin/bash execute_sql.sh "sql/part7_view2.sql" $LOG "--echo-all"
