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


-- DUMMY DATA QUERY


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




-- ======================== TRAINS ==================================


-- # 12046
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12046,'2022-12-10', '12:05:00' , '12:05:00' , 0, 1, 'CHANDIGARH');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12046,'2022-12-10', '12:43:00' , '12:47:00' , 0, 2, 'AMBALA CANT JN');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12046,'2022-12-10', '13:35:00' , '13:37:00' , 0, 3, 'KARNAL');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12046,'2022-12-10', '15:20:00' , '15:20:00' , 0, 4, 'NEW DELHI');

-- # 12012
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12012,'2022-12-10', '17:45:00' , '17:45:00' , 0, 1, 'KALKA');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12012,'2022-12-10', '18:15:00' , '18:23:00' , 0, 2, 'CHANDIGARH');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12012,'2022-12-10', '19:03:00' , '19:08:00' , 0, 3, 'AMBALA CANT JN');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12012,'2022-12-10', '19:38:00' , '19:40:00' , 0, 4, 'KURUKSHETRA JN');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12012,'2022-12-10', '20:22:00' , '20:24:00' , 0, 5, 'PANIPAT JN');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12012,'2022-12-10', '21:50:00' , '21:50:00' , 0, 6, 'NEW DELHI');

-- # 22692
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(22692,'2022-12-10', '19:50:00' , '19:50:00' , 0, 1, 'H NIZAMUDDIN');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(22692,'2022-12-10', '21:45:00' , '21:47:00' , 0, 2, 'AGRA CANTT');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(22692,'2022-12-10', '23:08:00' , '23:10:00' , 0, 3, 'GWALIOR');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(22692,'2022-12-10', '00:25:00' , '00:30:00' , 1, 4, 'V LAKSHMIBAI');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(22692,'2022-12-10', '03:45:00' , '03:55:00' , 1, 5, 'BHOPAL JN');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(22692,'2022-12-10', '05:23:00' , '05:25:00' , 1, 6, 'ITARSI JN');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(22692,'2022-12-10', '09:25:00' , '09:30:00' , 1, 7, 'NAGPUR');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(22692,'2022-12-10', '12:15:00' , '12:20:00' , 1, 8, 'BALHARSHAH');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(22692,'2022-12-10', '15:08:00' , '15:10:00' , 1, 9, 'KAZIPET JN');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(22692,'2022-12-10', '17:10:00' , '17:25:00' , 1, 10, 'SECUNDERABAD JN');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(22692,'2022-12-10', '19:29:00' , '19:30:00' , 1, 11, 'SERAM');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(22692,'2022-12-10', '21:38:00' , '21:40:00' , 1, 12, 'RAICHUR');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(22692,'2022-12-10', '23:30:00' , '23:35:00' , 1, 13, 'GUNTAKAL JN');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(22692,'2022-12-10', '02:03:00' , '02:05:00' , 2, 14, 'SAI P NILAYAM');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(22692,'2022-12-10', '05:20:00' , '05:20:00' , 2, 15, 'KSR BENGALURU');

-- # 12650
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12650,'2022-12-10', '08:20:00' , '08:20:00' , 0, 1, 'NEW DELHI');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12650,'2022-12-10', '11:43:00' , '11:45:00' , 0, 2, 'GWALIOR');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12650,'2022-12-10', '13:01:00' , '13:09:00' , 0, 3, 'V LAKSHMIBAI');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12650,'2022-12-10', '16:55:00' , '17:05:00' , 0, 4, 'BHOPAL JN');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12650,'2022-12-10', '22:55:00' , '23:00:00' , 0, 5, 'NAGPUR');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12650,'2022-12-10', '02:25:00' , '02:30:00' , 1, 6, 'BALHARSHAH');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12650,'2022-12-10', '08:20:00' , '08:30:00' , 1, 7, 'KALCHEGUDA');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12650,'2022-12-10', '12:29:00' , '12:30:00' , 1, 8, 'KURNOOL CITY');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12650,'2022-12-10', '15:20:00' , '15:25:00' , 1, 9, 'GUNTAKAL JN');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12650,'2022-12-10', '16:30:00' , '16:35:00' , 1, 10, 'BALLARI JN');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12650,'2022-12-10', '17:35:00' , '17:40:00' , 1, 11, 'HOSAPETE JN');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12650,'2022-12-10', '18:14:00' , '18:16:00' , 1, 12, 'KOPPAL');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12650,'2022-12-10', '19:00:00' , '19:02:00' , 1, 13, 'GADAG JN');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12650,'2022-12-10', '20:35:00' , '20:45:00' , 1, 14, 'SSS HUBBALLI JN');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12650,'2022-12-10', '21:58:00' , '22:00:00' , 1, 15, 'MMAILARA HAVERI');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12650,'2022-12-10', '23:13:00' , '23:15:00' , 1, 16, 'DAVANGERE');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12650,'2022-12-10', '01:15:00' , '01:20:00' , 2, 17, 'ARSIKERE JN');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12650,'2022-12-10', '03:24:00' , '03:25:00' , 2, 18, 'TUMAKURU');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12650,'2022-12-10', '05:30:00' , '05:30:00' , 2, 19, 'YESVANTPUR JN');


-- # 12058
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12058,'2022-12-10', '05:05:00' , '05:05:00' , 0, 1, 'UNA HIMACHAL');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12058,'2022-12-10', '05:22:00' , '05:28:00' , 0, 2, 'NANGAL DAM');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12058,'2022-12-10', '05:42:00' , '05:44:00' , 0, 3, 'ANANDPUR SAHIB');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12058,'2022-12-10', '05:52:00' , '05:54:00' , 0, 4, 'KIRATPUR SAHIB');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12058,'2022-12-10', '06:13:00' , '06:15:00' , 0, 5, 'RUPNAGAR');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12058,'2022-12-10', '06:40:00' , '06:42:00' , 0, 6, 'MORINDA');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12058,'2022-12-10', '06:57:00' , '06:59:00' , 0, 7, 'KHARAR');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12058,'2022-12-10', '07:16:00' , '07:18:00' , 0, 8, 'SAHIBZADA ASNGR');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12058,'2022-12-10', '07:33:00' , '07:43:00' , 0, 9, 'CHANDIGARH');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12058,'2022-12-10', '08:32:00' , '08:45:00' , 0, 10, 'AMBALA CANT JN');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12058,'2022-12-10', '09:14:00' , '09:16:00' , 0, 11, 'KURUKSHETRA JN');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12058,'2022-12-10', '09:38:00' , '09:40:00' , 0, 12, 'KARNAL');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12058,'2022-12-10', '10:02:00' , '10:04:00' , 0, 13, 'PANIPAT JN');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12058,'2022-12-10', '10:22:00' , '10:24:00' , 0, 14, 'BHODWAL MAJRI');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12058,'2022-12-10', '10:32:00' , '10:34:00' , 0, 15, 'SONIPAT');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12058,'2022-12-10', '11:05:00' , '11:07:00' , 0, 16, 'SUBZI MANDI');
Insert into station(uid,doj,arrival_time,departure_time,off_set,order_of_station,name) values(12058,'2022-12-10', '11:45:00' , '11:45:00' , 0, 17, 'NEW DELHI');
