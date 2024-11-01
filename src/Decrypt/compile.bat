@echo off
cd /d ./
del *.class
javac Decrypt.java
jar cfm Decrypt.jar MANIFEST.MF *.class
pause