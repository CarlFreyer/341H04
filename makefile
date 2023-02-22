main:
	javac Utilization.java
	jar cfmv Utilization.jar Manifest.txt Utilization.class
	java -jar Utilization.jar   