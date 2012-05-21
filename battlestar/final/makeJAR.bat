javac source/*.java
jar -cmf manifest.txt Battlestar.jar source/*.class source/images/*.*
jar -tvf Battlestar.jar
java -jar Battlestar.jar
pause
