@echo off

if "" == "%1" goto errorCreate

if "" == "%2" goto errorCreate

call ant -Djar.name=%1 -Djar.display.name=%2 create

rem call ant deploy

goto end

:errorCreate
	echo.
	echo Usage: create.bat hello-world "Hello World"
	echo.
	echo The first hello-world is your jar name. A new directory will be created based
	echo on the jar name.
	echo.
	echo The second "Hello World" is the jar's name. The quotation marks are
	echo only needed because there is a space in the jar name.
	echo.

	goto end

:end