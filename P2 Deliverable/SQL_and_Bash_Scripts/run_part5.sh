#! /bin/bash

LOG="part5.log"

if [ "$1" != "" ] 
then
	LOG=$1"/"$LOG
fi

printf "COMP421 Project Deliverable #2 Part 5\n" | tee $LOG

#Run part5_query1.sql and log output
/bin/bash execute_sql.sh "sql/part5_query1.sql" $LOG "--echo-all"

#Run part5_query2.sql and log output
/bin/bash execute_sql.sh "sql/part5_query2.sql" $LOG "--echo-all"

#Run part5_query3.sql and log output
/bin/bash execute_sql.sh "sql/part5_query3.sql" $LOG "--echo-all"

#Run part5_query4.sql and log output
/bin/bash execute_sql.sh "sql/part5_query4.sql" $LOG "--echo-all"

#Run part5_query5.sql and log output
/bin/bash execute_sql.sh "sql/part5_query5.sql" $LOG "--echo-all"
