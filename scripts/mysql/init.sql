CREATE DATABASE semefa;

CREATE USER 'semefa'@'%' IDENTIFIED WITH mysql_native_password BY 'semefa';

GRANT ALL ON semefa.* TO 'semefa'@'%';
