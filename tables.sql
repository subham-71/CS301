CREATE DATABASE railway;
\c railway

CREATE TABLE train(
uid INT NOT NULL,
ac_count INT NOT NULL,
sl_count INT NOT NULL,
DOJ DATE NOT NULL,
Primary key(uid)
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

CREATE TABLE remaining_tickets(
uid INT not null,
ac_rem INT not null,
sl_rem INT not null,
DOJ date not null,
Primary key(uid, DOJ)
);

CREATE TABLE ticket_records(
uid INT not null,
Booking_timestamp TIMESTAMP,
Berth_no INT not null,
DOJ date not null,
Coach_no INT not null,
Coach_type VARCHAR(2) not null, 
Seat_type VARCHAR(2) not null,
PNR INT not null,
passenger_name VARCHAR(20) not null,
Primary key(uid, berth_no, DOJ, coach_no, coach_type, seat_type)
);

CREATE TABLE station(
Uid INT not null,
DOJ date not null,
Arrival_time time not null,
Departure_time time not null,
Order_of_station INT,
Primary key (uid,DOJ,arrival_time)
);
