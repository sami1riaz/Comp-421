#! /bin/bash

LOG="part2.log"

if [ "$1" != "" ] 
then
	LOG=$1"/"$LOG
fi

printf "COMP421 Project Deliverable #2 Part 2\n" | tee $LOG

#Run tables_dropall.sql and log output
/bin/bash execute_sql.sh "sql/tables_dropall.sql" $LOG "--echo-all"

#Run tables_create.sql and log output
/bin/bash execute_sql.sh "sql/tables_create.sql" $LOG "--echo-all"

#Run tables_print.sql and log output
/bin/bash execute_sql.sh "sql/tables_print_desc.sql" $LOG "--echo-all"
