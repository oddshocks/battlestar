del source/*.class
javac source/*.java
jar -cmf servermanifest.txt BattleServer.jar source/*.class
jar -tvf BattleServer.jar
java -jar BattleServer.jar
pause
