#!/bin/sh

#set -x

if [ -z "$1" ]; then
	echo
	echo Usage: ./create.sh hello-world \"Hello World\"
	echo
	echo The first hello-world is your jar name. A new directory will be created based
	echo on the jar name.
	echo
	echo The second \"Hello World\" is the jar\'s name. The quotation marks are
	echo only needed because there is a space in the display name.
	echo

	exit 127
fi

ant -Djar.name=$1 -Djar.display.name=\"$2\" create

#ant deploy

exit 0