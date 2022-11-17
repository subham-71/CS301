# CS301 - Railway Resrvation System

## Key Features :

1. This is a scalable database which is able to handle 5000 queries per second.
2. Admin will be able to add Trains along with their properties.
3. There is a train recommender system which recommends both direct and indirect trains (with one intermmediate station) between source and destination.


## Steps to run :

# Manually

0. Make a config.properties file where you place your database name, username and password of postgreSQL database.

1. To Add Trains: Give the train Number , date of journey and respective number of coaches in Trainschedule.txt. <br/>
   ``Format : <Train Number> <Date> <Number of AC Coaches> <Number of SL coaches> ``

2. Run the Admin.java file to update the train records in the database.

3. Place your booking requests in the INPUT folder following the naming scheme as in the repo. <br/> 
   ``Format :  <No. of Passengers> <Name of Passengers> <Train Number> <Date> <AC or SL>``

4. Start the ServiceModule.java. 

5. Run the client.java file to simulate multiple clients who will be firing the booking requests simulatneously.

6. Run the SearchProcedure.java file to find train between stations.

# Through Master File

1. Run Master.py

2. Select Options to perform the action accordingly.

## Implementation :

We have simulated clients using multithreading approach, where many clients try to book tickets simultaneously from the database through through the server.
We have created a connection pool where each client is randomly assigned an idle connection to the server ensuring parallelism. Once a client request is connected to database, it locks the table and books tickets of the particular train. Tickets are assigned to each request in a serial fashion and are updated in the ticket_records table.

## Collaborative Tools :

We have used Live Share Feature of Visual Studio Code and Github to develop the code.




