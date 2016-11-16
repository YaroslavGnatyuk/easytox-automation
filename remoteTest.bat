@echo on
echo "Tests will use Browser: %1"
echo "Tests will use Browser Version: %2"
echo "Tests will run on Platform: %3"

IF %#== 3 (
    mvn clean test -Dbrowser=%1 -DdesiredBrowserVersion=%2 -DdesiredPlatform=%3 -DremoteDriver=true -DgridURL=http://localhost:4444/wd/hub
) ELSE IF %# == 2 (
    mvn clean test -Dbrowser=%1 -DdesiredBrowserVersion=%2 -DdesiredPlatform=any -DremoteDriver=true -DgridURL=http://localhost:4444/wd/hub
) ELSE IF %# == 1 (
    mvn clean test -Dbrowser=%1 -DdesiredPlatform=any -DremoteDriver=true -DgridURL=http://localhost:4444/wd/hub
)

rem mvn clean test -DremoteDriver=true -DgridURL=http://localhost:4444/wd/hub
rem mvn clean test -Dbrowser=firefox -DdesiredPlatform=%3 -DremoteDriver=true -DgridURL=http://localhost:4444/wd/hub