main:
	javac ./Utilization/Utilization.java
	mv ./Utilization/Utilization.class ./Utilization.class
	jar cfmv Utilization.jar Manifest.txt ./Utilization.class
run: main
	java -jar Utilization.jar  
clean:
	rm -rf Utilization.jar
	rm -rf Utilization.class