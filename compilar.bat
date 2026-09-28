@echo off
cd /d "%~dp0"
chcp 65001 > nul
javac -encoding UTF-8 -cp ".;java-cup-11b-runtime.jar" Lexer.java sym.java Parser.java Main.java
if errorlevel 1 exit /b 1
echo Compilacion terminada.
