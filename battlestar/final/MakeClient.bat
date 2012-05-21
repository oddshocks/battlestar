javac source/*.java
jar -cmf clientmanifest.txt BattleClient.jar source/*.class source/images/*.*
jar -tvf BattleClient.jar
java -jar BattleClient.jar
pause
