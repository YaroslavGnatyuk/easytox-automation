#!/usr/bin/env bash
echo "Tests will use Browser: $1"
echo "Tests will use Browser Version: $2"
echo "Tests will run on Platform: $3"

case $1 in
    firefox)
        mvn clean test -Dbrowser=$1 -DdesiredPlatform=$2 -DremoteDriver=true -DgridURL=http://localhost:4444/wd/hub
    ;;
    ie)
        mvn clean test -Dbrowser=$1 -DdesiredBrowserVersion=$2 -DdesiredPlatform=$3 -DremoteDriver=true -DgridURL=http://localhost:4444/wd/hub
    ;;
    chrome)
        mvn clean test -Dbrowser=$1 -DdesiredBrowserVersion=$2 -DdesiredPlatform=$3 -DremoteDriver=true -DgridURL=http://localhost:4444/wd/hub
    ;;
esac
