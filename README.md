Bogdan Viktor

MOVIE MANAGEMENT
This is a simple web application in which you can add, edit and delete directors and their movies.

Short description of how you can run this application.

    Software you'll need: JDK 8 or higher, Tomcat server, browser.

    Startup of the application:
    	- download the app
		- go to the root directory BogdanViktor
		- run the command: mvn clean install 
		- go to the "BogdanViktor/rest/target/" directory
		- copy WAR file "rest-1.0-SNAPSHOT.war" to the webapps directory of your tomcat server
		- go to the "BogdanViktor/web-app/target/" directory
		- copy WAR file "web-app-1.0-SNAPSHOT.war" to the webapps directory of your tomcat server
		- restart tomcat
		- now the application is available on the path: "http://localhost:8080/web-app-1.0-SNAPSHOT/movies" which is initial page
	

	You can also run aplication on the Jetty server

		- open file "BogdanViktor/client-rest/src/main/resources/url.properties"
		- set variables "port=8088" and "prefix="
		- go to the root directory "BogdanViktor"
		- run the command: mvn clean install 
    	- go to "BogdanViktor/rest/"
		- run the following command: mvn jetty:run
		- go to "BogdanViktor/web-app/"
		- run the following command: mvn jetty:run
		- now the application is available on the path: "http://localhost:8080/movies" which is initial page

