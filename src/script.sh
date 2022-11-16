#!/bin/sh
run_jars() {
  echo -n "Frontend: "
  read name
  frontendName=$name"Frontend.jar"
  
  echo -n "Servicio Publicaciones: "
  read name
  publicationsName=$name"ServicioPublicaciones.jar"
  
  echo -n "Servicio Reacciones: "
  read name
  reactionsName=$name"ServicioReacciones.jar"
  
  echo -n "Servicio Usuarios: "
  read name
  usersName=$name"ServicioUsuarios.jar"
  
  echo -n "Main: "
  read name
  mainName=$name"Main.jar"

  java -cp $frontendName":"$publicationsName":"$reactionsName":"$usersName":"$mainName Main
}

echo -n "¿Compilar Proyecto? [y/n]: "
read option

if [ $option = "y" ]
then
  echo -n "Java version: "
  java --version
  javac -cp . Main.java
fi

echo -n "¿Generar Jars? [y/n]: "
read option
if [ $option = "y" ]
then
  echo -n "Nombre del usuario para los Jars: "
  read userName

  fileName=$userName"Frontend.jar"
  jar -cvf $fileName frontend/*

  fileName=$userName"ServicioPublicaciones.jar"
  jar -cvf $fileName backend/serviciopublicaciones/*

  fileName=$userName"ServicioReacciones.jar"
  jar -cvf $fileName backend/servicioreacciones/*

  fileName=$userName"ServicioUsuarios.jar"
  jar -cvf $fileName backend/serviciousuarios/*
  
  fileName=$userName"Main.jar"
  jar -cvf $fileName Main.class
fi

echo -n "¿Correr Jars? [y/n]: "
read option
if [ $option = "y" ]
then
  run_jars
fi

echo "Finalizado"
