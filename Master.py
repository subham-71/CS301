import os
from threading import Thread
from functools import partial 
import time

print("WELCOME TO THE RAILWAY RESERVATION SYSTEM: \n")

while(True) :

        print("===================================")
        print("1. Admin Procedure")
        print("2. Booking Procedure");
        print("3. Search Procedure");
        print("=================================== \n")


        option = int(input("Enter Option: "))
        
        if option == 1:
                os.system('javac Admin.java')
                os.system('java Admin')

        elif option == 2:
                os.system('javac client.java')
                os.system('java client')
        
        elif option == 3:
                os.system('javac SearchProcedure.java')
                os.system('java SearchProcedure')
        else :
                break



