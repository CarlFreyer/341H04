# 341H04
# Included
ojdbc8.jar - The JBDC driver used for this code
manifest.txt - for combining ojdbc8.jar w/ utilization.jar
utilization - directory containing the .java and .class files
utilization.java - the code to show the utilization of a room in the edgar1 university database
utilization.class - the class file for utilization.java (created with makefile)
utilization.jar - the executable for utilization.java (created with makefile)
makefile - a makefile, more details below

# makefile
make, make main - compiles the utilization.jar executable
make run - compiles the utilization.jar executable and runs it
make clean - restores the project to its original state. Removes the utilization class and jar file