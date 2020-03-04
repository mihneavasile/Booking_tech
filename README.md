#Booking Technical Test

##Setup:
```
These are the instructions to build my code, including any dependencies:
 
- Using the IntelliJ maven tool: run clean and install.
- Using the terminal run: ./mvnw clean install

To run the tests use: 
- Using the IntelliJ maven tool: run test.
- Using the terminal run: ./mvnw test

JDK 1.8 is required.

My program can be run using the IntelliJ IDEA or the terminal. 
I used the IntelliJ IDEA version 2019.3.
 
If you want to run it from a terminal then you have to be in the project directory tech/target.
```


##Part 1 
```
Command structure:
java -jar tech-0.0.1-SNAPSHOT.jar  {pickup} {drop-off} {min number of passengers (optional)} {supplier (optional)}
```

###Console application to print the search results for Dave's Taxis:
```
Command structure:
java -jar tech-0.0.1-SNAPSHOT.jar {pickup} {drop-off} 0 {supplier}

Examples:
java -jar tech-0.0.1-SNAPSHOT.jar 50,50 50,50 0 dave
java -jar tech-0.0.1-SNAPSHOT.jar 50,50 50,50 0 eric
java -jar tech-0.0.1-SNAPSHOT.jar 50,50 50,50 0 jeff

If you want all rides just from a certain supplier you have to run the above command, with the min number of passengers 
0 and one of the 3 suppliers (dave, jeff, eric). The reason you have to run it like this is because of the way I 
designed the application. These 2 are optional arguments {min number of passengers (optional)} {supplier (optional)}, 
but you can’t just ignore the number of passengers and still introduce a supplier. There is room for improvement here, 
but I noticed this issue too late.
```


###Console application to filter by number of passengers:
```
Command structure:
java -jar tech-0.0.1-SNAPSHOT.jar {pickup} {drop-off} {number of passengers} {supplier (optional)}

If you want to get a list of rides taking into account all three suppliers, the don’t provide a supplier argument, like 
in the first example below. If you specify a supplier you also filter results by supplier.

Examples:
java -jar tech-0.0.1-SNAPSHOT.jar 50,50 50,50 5
java -jar tech-0.0.1-SNAPSHOT.jar 50,50 50,50 8 dave
java -jar tech-0.0.1-SNAPSHOT.jar 50,50 50,50 8 eric
```


##Part 2:
```
To start the API use: 
java -jar tech-0.0.1-SNAPSHOT.jar

To send requests use one of the links http://localhost:8080/ride or http://localhost:8080/ride/{supplier}.

To filter by supplier, replace {supplier} by one of dave, jeff, eric.

If there are no rides available or a problem has occurred, then an empty list will be returned and displayed. A problem 
may be a server error (supplier unavailable), a bad request or a timeout. These will be printed in the terminal so you 
can see what is going on with your request.
After completing each request the message “Ride options displayed.” will be printed in the terminal.


Example requests:
http://localhost:8080/ride?pickup=50,50&dropoff=50,50
http://localhost:8080/ride?pickup=50,50&dropoff=50,50&numberOfPassengers=2

http://localhost:8080/ride/dave?pickup=50,50&dropoff=50,50
http://localhost:8080/ride/dave?pickup=50,50&dropoff=50,50&numberOfPassengers=2
```





