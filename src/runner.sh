#!/bin/sh

run_jars() {
  echo "Start Running..."
  echo -n "Insert Frontend's name: "
  read name
  frontend=$name"Frontend.jar"
  echo -n "Insert Backend's name: "
  read name
  backend=$name"Backend.jar"
  echo -n "Insert Main's name: "
  read name
  main=$name"Main.jar"
  java -cp $frontend":"$backend":"$main Main
}

echo -n "Java version: "
java --version
echo -n "Compile file? [y/n]: "
read option

#echo "You chose $option"
if [ $option = "y" ]
then
  echo "Compiling..."
  #javac backend
  #javac frontend
  javac -cp . Main.java
  echo "Not Supported yet"
fi

echo -n "Convert to jars? [y/n]: "
read option
if [ $option = "y" ]
then
  ## Convertir los archivos .class jar
  echo -n "Insert jar's name: "
  read keyword
  #jar -cvf JoseCamachoFrontend.jar frontend/*
  value=$keyword"Frontend"
  jar -cvf $value.jar frontend/*
  value=$keyword"Backend"
  #jar -cvf JoseCamachoBackend.jar backend/*
  jar -cvf $value.jar backend/*
  value=$keyword"Main"
  #jar -cvf JoseCamachoMain.jar Main.class
  jar -cvf $value.jar Main.class
fi

echo -n "Run jars? [y/n]: "
read option
if [ $option = "y" ]
then
  run_jars
fi

echo "Finished, Have a nice day"
