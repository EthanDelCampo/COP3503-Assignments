#Dr. Andrew Steinberg
#COP3503C Student Python Script v1.1
#This script will automatically compile, run, and compare your results to a provided txt file that contains sample output.
#DO NOT MODIFY THE CONTENTS OF THIS FILE!
#You do not need understand this code as this is out of the scope of this course. However, if you are interested you can discuss with Dr. Steinberg
#New to v1.1 update, leading and trailing whitespace is automatically eliminated.

import os
import sys
import subprocess
import functools

#this is where we will put the input... yes they are global variables but this is ok to do for this scenario since this is a script
#it is list and each item will be string representing input for each question
#example  ['1 2 3', '2' , '4.2 41.4']
qinputs = [] 

def result():
    cwd = os.getcwd()
    
    f1 = open(cwd + '/samplesolutionp0.txt', "r")
    f2 = open(cwd + '/program0solution_student_output.txt', "r")
    
    #read output from each text file
    l1prestrip = f1.readlines()
    l2prestrip = f2.readlines()
    
    #remove leading and trailing whitespace
    l1 = [s.strip() for s in l1prestrip]
    l2 = [s.strip() for s in l2prestrip]
    
    
    #compare the content read
    if len(l1) == len(l2) and functools.reduce(lambda x, y : x and y, map(lambda p, q: p == q, l1, l2), True): 
        print("  ------  ")
        print(" / o  o \ ")
        print("|        |")
        print("| \____/ |")
        print(" \      / ")
        print("  ------  ")
        print("YES!!!! My file output matched Dr. Steinberg's sample output!")
    else: 
        print("  ------  ")
        print(" / o  o \ ")
        print("|        |")
        print("|  ____  |")
        print("| /    \ |")
        print(" \      / ")
        print("  ------  ")
        print("Oh no! My file output didn't match Dr. Steinberg's sample output! I better check to make sure I didn't add extra whitespace or newlines.") 
    
    

def compilerunfile():
    cwd = os.getcwd()
    
    #javac compile command the executable will be EustisSetup.class
    
    javac = "javac " + cwd + "/EustisSetup.java " #compile solution file command and generate class file
    
    proc = subprocess.run([javac], capture_output=True, text = True, shell = True)
    
    if(len(str(proc.stderr)) > 0):
        print("Ugh oh... Your program had warning/error messages! Try to fix them or else points are deducted.")
        print("------------------------------------------------------------------------")
        print(str(proc.stderr))
        print("------------------------------------------------------------------------")
    
    java = "java EustisSetup"
    
    f = open('program0solution_student_output.txt', "w")
    
    #list comprehension
    inputs = ' '.join([str(item) for item in qinputs])
    
    proc2 = subprocess.run([java], capture_output=True, input = inputs, text = True, shell = True) 
    
    if(len(str(proc2.stderr)) > 0):
        print("Ugh oh... Your program had error messages! Try to fix them or else points are deducted.")
        print("------------------------------------------------------------------------")
        print(str(proc2.stderr))
        print("------------------------------------------------------------------------")
    
    f.write(str(proc2.stdout))

    print("Done running the student's solution.")
    f.close()


def setupchecker():
    print("We are now checking to make sure you have all necessary files in the same directory.")
    cwd = os.getcwd() #this grabs the current directory as it varies for each student based on NID
    
    #first check to see if solution txt file exists in the same directory as this script
    
    if not os.path.exists(cwd + "/samplesolutionp0.txt"):
        raise FileNotFoundError("The provided sample solution text file is not in the currect directory as this script." + 
        " Please place the text file in this directory and try again. Script Exiting!")
        
    
    #check to see if java file is placed properly. Only checks parts of it. Student is responsible for naming it properly with respective name as stated in the directions.
    if not os.path.exists(cwd + "/EustisSetup.java"):
        raise FileNotFoundError("You are missing your Java source!! Please put in your Java file. Script Exiting!")
        
    print("We are done with checking to make sure necessary files were provided.")
    

def main():
    print("Beginning the test of my program compared with a provided txt file from Dr. Steinberg.")
    print("Before this script tests the program, we need to make sure you have everything in order.")
    setupchecker()
    print("Setup is now complete. Begin testing.")
    print("Now it is time to compile the file.")
    compilerunfile()
    print("Now it is time for the moment of truth. Comparing my file output with Dr. Steinberg's sample solution file.")
    result()
    
    
    

if __name__ == "__main__":
    main()