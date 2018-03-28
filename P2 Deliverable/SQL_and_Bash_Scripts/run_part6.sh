#! /bin/bash

LOG="part6.log"

if [ "$1" != "" ] 
then
	LOG=$1"/"$LOG
fi

printf "COMP421 Project Deliverable #2 Part 6\n" | tee $LOG

#Run part6_mod1.sql and log output
/bin/bash execute_sql.sh "sql/part6_mod1.sql" $LOG "--echo-all"

#Run part6_mod2.sql and log output
/bin/bash execute_sql.sh "sql/part6_mod2.sql" $LOG "--echo-all"

#Run part6_mod3.sql and log output
/bin/bash execute_sql.sh "sql/part6_mod3.sql" $LOG "--echo-all"

#Run part6_mod4.sql and log output
/bin/bash execute_sql.sh "sql/part6_mod4.sql" $LOG "--echo-all"
