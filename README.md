# weatherForcast
This Application does the weather forecasting based on inputs

Input for the API : zipCode , countryCode (countryCode is optional in case not given it considers default as india)

Request for temperature:
curl --location 'localhost:8080/weather?zipCode=421202&countryCode=IN'

Response:
{"timezone":"19800","main":{"temp":310.2,"temp_min":309.7,"temp_max":310.2,"pressure":1016.0,"humidity":19.0},"weather":[{"main":"Clouds","description":"few clouds"}],"fromCache":true}


Error Response:
{"timestamp":"2024-03-28T07:12:29.025+00:00","status":500,"error":"Internal Server Error","path":"/weather/500070"}

fromCache is the field that defines wheather it is from redis cache or its from the API.

We are using the openweathermap apis 

We need to create account in openweathermap 

Keys that got generated after login need to replace in application.properties in openweathermap.api.key to make the api work 


To run the application:

1. Replace the key in application.properties
2. mvn clean install
3. java -jar .\target\weatherforcasting-0.0.1-SNAPSHOT.jar
