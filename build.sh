#!/bin/sh
mvn clean install
mkdir build
cp target/Practice-1.0-SNAPSHOT.jar build/
echo "Done!"
