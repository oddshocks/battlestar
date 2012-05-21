echo Main-Class: BattleClient > clientmanifest.txt
echo Main-Class: BattleServer > servermanifest.txt

jar cvfm client.jar clientmanifest.txt *.class images
jar cvfm server.jar servermanifest.txt *.class 
