#! /bin/bash

LOG_INSERT="part4_insert.log"
LOG_PRINT="part4_print.log"

if [ "$1" != "" ] 
then
	LOG_INSERT=$1"/"$LOG_INSERT
	LOG_PRINT=$1"/"$LOG_PRINT
fi

printf "COMP421 Project Deliverable #2 Part 4\n" | tee $LOG_INSERT $LOG_PRINT

#Run tables_dropall.sql 
/bin/bash execute_sql.sh "sql/tables_dropall.sql" $LOG_INSERT

#Run tables_create.sql 
/bin/bash execute_sql.sh "sql/tables_create.sql" $LOG_INSERT

#Run insert sql files
printf "\nInserting data:\n" >> $LOG_INSERT
INSERT_ORDER=( wall creator usert page submission post commentt eventt notification page_follower eventt_subscription submission_like feed_view usert_friend )
for i in "${INSERT_ORDER[@]}"
do
	/bin/bash execute_sql.sh "sql/insert_"$i".sql" $LOG_INSERT
done

#Run enforce_covering_constraints.sql 
/bin/bash execute_sql.sh "sql/enforce_covering_constraints.sql" $LOG_INSERT "--echo-all"

#Run tables_print_data sql file
/bin/bash execute_sql.sh "sql/tables_print_data.sql" $LOG_PRINT "--echo-all"
