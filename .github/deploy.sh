kill $(lsof -ti :8080)
sleep 10
nohup java -jar filmbox.jar > log.log 2>&1 &