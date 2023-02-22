main:
	javac ./Utilization/Utilization.java
	jar cfmv Utilization.jar Manifest.txt ./Utilization/Utilization.class
run: main
	java -jar Utilization.jar  
clean:
	rm -rf Utilization.jar
	rm -rf ./Utilization/Utilization.class