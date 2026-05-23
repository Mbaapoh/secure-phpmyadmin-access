#!/bin/bash
# Zabbix LLD for Nginx Error Logs
echo "{\"data\":["
first=1
for file in /var/log/nginx/*error.log; do
    [ -e "$file" ] || continue
    if [ $first -eq 0 ]; then echo ","; fi
    first=0
    filename=$(basename "$file")
    echo -n "{\"{#LOGPATH}\":\"$file\", \"{#LOGNAME}\":\"$filename\"}"
done
echo "]}"
