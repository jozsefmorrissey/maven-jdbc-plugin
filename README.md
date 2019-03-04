mvn \
'com.opsc.maven.plugin:jdbc-to-json:0.0.1-SNAPSHOT:DbToJson' \
-DuserName=USER_SRVC_TEST \
-Dpassword=iabus9bie9eeZ8wein2ohfaequien \
-DserverOdbName=localhost \
-Dport=1521 \
-Ddbms=oracle \
-Dsid=xe \
"-Dcmd=SELECT * FROM AM_USER" \
-Dfilename=./temp.json

mvn \
'com.opsc.maven.plugin:jdbc-to-json:0.0.1-SNAPSHOT:DbToJson' \
-DuserName=$(sudo confidentalInfo.sh value UserSrvc DB_USER) \
-Dpassword=$(sudo confidentalInfo.sh value UserSrvc dbPass) \
-DdbUrl=$(sudo confidentalInfo.sh value UserSrvc DB_URL)\
-DqId=FIELD \
-Dcmd=SELECT * FROM FIELD" \
