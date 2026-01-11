#!/bin/bash
# Zabbix Low-Level Discovery for Nginx Access Logs

echo "{\"data\":["
first=1

# Loop through all files ending in access.log
for file in /var/log/nginx/*access.log; do
    # specific check to skip if no files match
    [ -e "$file" ] || continue

    # Print comma if this isn't the first item
    if [ $first -eq 0 ]; then
        echo ","
    fi
    first=0

    # Get just the filename (e.g. cm.smobilpay.com.access.log)
    filename=$(basename "$file")

    # Output JSON format required by Zabbix
    echo -n "{\"{#LOGPATH}\":\"$file\", \"{#LOGNAME}\":\"$filename\"}"
done

echo "]}"
