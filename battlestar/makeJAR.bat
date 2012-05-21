javac *.java
jar -cmf manifest.txt Battlestar.jar *.class images/*.*
jar -tvf Battlestar.jar
java -jar Battlestar.jar
pause
