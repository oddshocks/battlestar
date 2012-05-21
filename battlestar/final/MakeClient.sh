#!/bin/sh
rm source/*.class
javac source/*.java
jar -cmf clientmanifest.txt BattleClient.jar source/*.class source/images/*.*
jar -tvf BattleClient.jar
java -jar BattleClient.jar
read -p "Press [Enter] key to continue..."
