create database bestbooksproject1db;
show databases;
use bestbooksproject1db;

drop table if exists users;

create table USERS(
	
    FIRSTNAME VARCHAR (30),
    LASTNAME VARCHAR (30),
    USERNAME VARCHAR(20),
    PSWD VARCHAR (25),
    AMADMIN INT
);
show tables;

Insert into Users (Firstname, Lastname, Username, Pswd, Amadmin) values("Erin", "Scho", "CodingBoss00", "Zelda", 1);
Insert into Users (Firstname, Lastname, Username, Pswd, Amadmin) values("Kyle", "Schomer", "FatherofFun", "banana", 0);

select * from users; 