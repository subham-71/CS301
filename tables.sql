CREATE DATABASE railway;
\c railway

CREATE TABLE train(
uid INT NOT NULL,
ac_count INT NOT NULL,
sl_count INT NOT NULL,
DOJ DATE NOT NULL,
Primary key(uid, doj)
);

CREATE TABLE AC_CC(
berth_no INT not null,
Type VARCHAR(2) not null,
Primary key(berth_no)
);

CREATE TABLE SL_CC(
berth_no INT not null,
Type VARCHAR(2) not null,
Primary key(berth_no)
);


CREATE TABLE ticket_records(
uid INT not null,
Booking_timestamp TIMESTAMP,
Berth_no INT not null,
DOJ date not null,
Coach_no INT not null,
Coach_type VARCHAR(2) not null, 
Seat_type VARCHAR(2) not null,
PNR VARCHAR (20) not null,
passenger_name VARCHAR(20) not null,
Primary key(uid, berth_no, DOJ, coach_no, coach_type, seat_type)
);









CREATE TABLE station(
Uid INT not null,
DOJ date not null,
Arrival_time time not null,
Departure_time time not null,
Order_of_station INT,
Name VARCHAR(20) not null,
Primary key (uid,DOJ,order_of_station)
);


DUMMY DATA QUERY


Insert into train(uid,ac_count,sl_count,DOJ) values(12891,  5, 6, ‘2022-12-10’);
Insert into train(uid,ac_count,sl_count,DOJ) values(12891,  5, 6, ‘2022-12-11’);
 





Insert into ac_cc(berth_no,type) values(1,'lb');
Insert into ac_cc(berth_no,type) values(2,'lb');
Insert into ac_cc(berth_no,type) values(3,'ub');
Insert into ac_cc(berth_no,type) values(4,'ub');
Insert into ac_cc(berth_no,type) values(5,'sl');
Insert into ac_cc(berth_no,type) values(6,'su');
Insert into ac_cc(berth_no,type) values(7,'lb');
Insert into ac_cc(berth_no,type) values(8,'lb');
Insert into ac_cc(berth_no,type) values(9,'ub');
Insert into ac_cc(berth_no,type) values(10,'ub');
Insert into ac_cc(berth_no,type) values(11,'sl');
Insert into ac_cc(berth_no,type) values(12,'su');
Insert into ac_cc(berth_no,type) values(13,'lb');
Insert into ac_cc(berth_no,type) values(14,'lb');
Insert into ac_cc(berth_no,type) values(15,'ub');
Insert into ac_cc(berth_no,type) values(16,'ub');
Insert into ac_cc(berth_no,type) values(17,'sl');
Insert into ac_cc(berth_no,type) values(18,'su');





Insert into sl_cc(berth_no,type) values(1,'lb');
Insert into sl_cc(berth_no,type) values(2,'mb');
Insert into sl_cc(berth_no,type) values(3,'ub');
Insert into sl_cc(berth_no,type) values(4,'lb');
Insert into sl_cc(berth_no,type) values(5,'mb');
Insert into sl_cc(berth_no,type) values(6,'ub');
Insert into sl_cc(berth_no,type) values(7,'sl');
Insert into sl_cc(berth_no,type) values(8,'su');
Insert into sl_cc(berth_no,type) values(9,'lb');
Insert into sl_cc(berth_no,type) values(10,'mb');
Insert into sl_cc(berth_no,type) values(11,'ub');
Insert into sl_cc(berth_no,type) values(12,'lb');
Insert into sl_cc(berth_no,type) values(13,'mb');
Insert into sl_cc(berth_no,type) values(14,'ub');
Insert into sl_cc(berth_no,type) values(15,'sl');
Insert into sl_cc(berth_no,type) values(16,'su');
Insert into sl_cc(berth_no,type) values(17,'lb');
Insert into sl_cc(berth_no,type) values(18,'mb');
Insert into sl_cc(berth_no,type) values(19,'ub');
Insert into sl_cc(berth_no,type) values(20,'lb');
Insert into sl_cc(berth_no,type) values(21,'mb');
Insert into sl_cc(berth_no,type) values(22,'ub');
Insert into sl_cc(berth_no,type) values(23,'sl');
Insert into sl_cc(berth_no,type) values(24,'su');
