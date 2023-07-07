::@echo off
javac ACHSystem.java Account.java
if %errorlevel% neq 0 (
	echo There was an error; exiting now.	
) else (
	echo Compiled correctly
	java ACHSystem	
)
