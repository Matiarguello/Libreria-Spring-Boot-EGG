DROP DATABASE IF EXISTS libreriamat;
CREATE DATABASE libreriamat CHARACTER SET utf8mb4;
USE libreriamat;


DROP TABLE IF EXISTS libro;
CREATE TABLE libro (
  id_libro int NOT NULL AUTO_INCREMENT,
  titulo varchar(45) DEFAULT NULL,
  year int DEFAULT NULL,
  ejemplares int DEFAULT NULL,
  restantes int DEFAULT NULL,
  prestados int DEFAULT NULL,
  alta tinyint DEFAULT NULL,
  isbn int DEFAULT NULL,
  autor_idautor int DEFAULT NULL,
  editorial_ideditorial int DEFAULT NULL,
  PRIMARY KEY (id_libro));

DROP TABLE IF EXISTS autor;
CREATE TABLE autor (
  idautor int NOT NULL AUTO_INCREMENT,
  nombre_autor varchar(45) DEFAULT NULL,
  alta_autor tinyint DEFAULT NULL,
  PRIMARY KEY (idautor));


DROP TABLE IF EXISTS editorial;
CREATE TABLE editorial (
  ideditorial int NOT NULL AUTO_INCREMENT,
  nombre_editorial varchar(45) DEFAULT NULL,
  alta_editorial tinyint DEFAULT NULL,
  PRIMARY KEY (ideditorial) );