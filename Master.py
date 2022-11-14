import os
from threading import Thread
from functools import partial 
import time
import subprocess

print("WELCOME TO THE RAILWAY RESERVATION SYSTEM:")
print("1. Admin Procedure")
print("2. Booking Procedure");
print("3. Search Procedure");

option = int(input("Enter Option: "))

while(True) :
        
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



