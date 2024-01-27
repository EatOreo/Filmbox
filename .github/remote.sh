kill $(lsof -ti :8080)
nohup java -jar filmbox.jar > log.log 2>&1 &