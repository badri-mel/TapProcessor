

## Getting Started

**How the hypothetical system works**

Before boarding a bus at a bus stop, passengers tap their credit card (identified by the PAN, or Primary
Account Number) on a card reader. This is called a tap on. When the passenger gets off the bus, they tap their
card again. This is called a tap off. The amount to charge the passenger for the trip is determined by the stops
where the passenger tapped on and tapped off. The amount the passenger will be charged for the trip will be
determined as follows:
Trips between Stop 1 and Stop 2 cost $3.25
Trips between Stop 2 and Stop 3 cost $5.50
Trips between Stop 1 and Stop 3 cost $7.30
Note that the above prices apply for travel in either direction (e.g. a passenger can tap on at stop 1 and tap off
at stop 2, or they can tap on at stop 2 and can tap off at stop 1. In either case, they would be charged $3.25).

**Completed Trips**
If a passenger taps on at one stop and taps off at another stop, this is called a complete trip. The amount the
passenger will be charged for the trip will be determined based on the table above. For example, if a passenger
taps on at stop 3 and taps off at stop 1, they will be charged $7.30.

**Incomplete trips**
If a passenger taps on at one stop but forgets to tap off at another stop, this is called an incomplete trip. The
passenger will be charged the maximum amount for a trip from that stop to any other stop they could have
travelled to. For example, if a passenger taps on at Stop 2, but does not tap off, they could potentially have
travelled to either stop 1 ($3.25) or stop 3 ($5.50), so they will be charged the higher value of $5.50.

**Cancelled trips**
If a passenger taps on and then taps off again at the same bus stop, this is called a cancelled trip. The
passenger will not be charged for the trip.

**The problem**
Given an input file in CSV format containing a single tap on or tap off per line you will need to create an output
file containing trips made by customers.
taps.csv [input file]
You are welcome to assume that the input file is well formed and is not missing data.

`Example input file:
ID, DateTimeUTC, TapType, StopId, CompanyId, BusID, PAN
1, 22-01-2018 13:00:00, ON, Stop1, Company1, Bus37, 5500005555555559
2, 22-01-2018 13:05:00, OFF, Stop2, Company1, Bus37, 5500005555555559`

trips.csv [output file]

You will need to match the tap ons and tap offs to create trips. You will need to determine how much to
charge for the trip based on whether it was complete, incomplete or cancelled and where the tap on and tap
off occurred. You will need to write the trips out to a file called trips.csv.

Example output file:

`Started, Finished, DurationSecs, FromStopId, ToStopId, ChargeAmount, CompanyId, BusID, PAN,
Status
22-01-2018 13:00:00, 22-01-2018 13:05:00, 900, Stop1, Stop2, $3.25, Company1, B37,
5500005555555559, COMPLETED`

Important: Do not use real credit card numbers (PANs) in the test data you provide to us.
You can find credit card numbers suitable for testing purposes at
http://support.worldpay.com/support/kb/bg/testandgolive/tgl5103.html

### Assumptions 
* The Tap data(input) csv file is assumed to be wellformed.
* When an Incomplete trip is detected, the Trip response is assumed to have empty values in Started , Finished and Duration columns
* There is no validation on PAN number , it is assumed to be always correct.
* The Main Class expects the arguments to be always present and correct. 

### Future Enhancements 
* May be convert this application onto Spring Boot CLI application to handle more custom prompt when Main program arguments are missing


### Prerequisites

* Install JAVA 17.
    If you have multiple installations. 
     Use SDKMAN to manage and install JDK 17
     https://mydeveloperplanet.com/2022/04/05/how-to-manage-your-jdks-with-sdkman/ 

* Install Maven => 3.8.6 using SDKMAN or anyother means and setup MAVENHOME.


### Installing and Usage

```
mvn clean package
```
Above command will generate the `TapProcessor-1.0-jar-with-dependencies.jar`

```
 java -jar <path to the TapProcessor-1.0-jar-with-dependencies.jar> <pathto the input csv file> <path to the outputcsv> 

```

For Example 

```
 java -jar target/TapProcessor-1.0-SNAPSHOT-jar-with-dependencies.jar /Users/badri/Downloads/TapProcessor/src/main/resources/tapsComplexCase.csv tripResult.csv 

```

Will generate TripResult csv data from the input Tap data csv file tapsComplexCase.csv
## Running the tests

Explain how to run the automated tests for this system
* The solution have implemented tests are various levels , the tests are written
first before the actual service was completed. 

```
mvn test
```

### Break down each test:

 
* `TapProcessorTest` , contains method to test happy scenario , incomplete , cancelled
and a mixed case scenarios
* `TapFileReaderTest` to test if the Fares configuration can be converted into a Map<Source,List<Fares>>
* `TapTest` test of the if csv Tap data is converted to Tap POJO correctly
* `TripFileWriterTest` test if the writer has actual generated an csv with expected headers and values. 


## Authors

* *Badri Narayanan Sugavanam* https://www.linkedin.com/in/badrisugavanam/


## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Inspiration for fat JAR plugins
  https://ktor.io/docs/maven-assembly-plugin.html
* Use of OpenCSV for reading and writing CSVs
  https://opencsv.sourceforge.net/
* Use of Lombok for automatic generation of Setters ,Getters ,
  Constructors and Builders
  https://projectlombok.org/setup/maven
* Jackson for Json support
  https://github.com/FasterXML/jackson
  


