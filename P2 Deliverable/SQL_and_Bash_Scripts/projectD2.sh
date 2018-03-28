#! /bin/bash

LOGDIR=projectD2_logs_$(date +%Y-%m-%d_%H:%M:%S)

mkdir $LOGDIR 

/bin/bash "run_part2.sh" $LOGDIR
/bin/bash "run_part3.sh" $LOGDIR
/bin/bash "run_part4.sh" $LOGDIR
/bin/bash "run_part5.sh" $LOGDIR
/bin/bash "run_part6.sh" $LOGDIR
/bin/bash "run_part7.sh" $LOGDIR
/bin/bash "run_part8.sh" $LOGDIR
/bin/bash "run_part9.sh" $LOGDIR

printf "Output logged to ./$LOGDIR\n"
