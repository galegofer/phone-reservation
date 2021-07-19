# Phone Reservation API

#### Pre-requisites

* Docker installed and running

#### Build

```
$ mvn clean install
``` 

#### Execution

```
$ java -jar ./target/phone-reservation-0.0.1-SNAPSHOT.jar
``` 

#### Endpoints

* GET /phone/list-bookings - Get all the booked phones
* POST /phone/book - Creates a booking for a phone
    * Body:
      ```
      {
          "personCorpKey": "uu0000", // The person corporate key who will make the booking
          "serialNumber": "j" // The phone unique serial number
      }
      ``` 
* POST /phone/return - Returns a previously created phone booking
  * Body:
     ```
      {
          "serialNumber": "j" // The phone unique serial number to be returned
      }
      ```
#### Example Data:

* Users:
    * FirstName - LastName  - CorpKey - Email
    * 'Damian', 'Fernandez', 'uu0000', 'ing.damianfernandez@gmail.com'
    * 'John', 'Doe', 'xz1234', 'johndoe@live.com'
    
* Phones:
    * Serial - Brand - Model
    * 'a', 'Samsung', 'Galaxy S9'
    * 'b', 'Samsung', 'Galaxy S8'
    * 'c', 'Samsung', 'Galaxy S7'
    * 'd', 'Motorola', 'Nexus 6'
    * 'e', 'LG', 'Nexus 5X'
    * 'f', 'Huawei', 'Honor 7X'
    * 'g', 'Apple', 'iPhone X'
    * 'h', 'Apple', 'iPhone 8'
    * 'i', 'Apple', 'iPhone 4s'
    * 'j', 'Nokia', '3310'

#### Questions

* What aspect of this exercise did you find most interesting?
   * The need to check an external API to enrich the mobile phones data.
* What did you find most cumbersome?
   * It was quiet hard to find a reliable alternative to FonoAPI (it is down), so I took another one Grabaphone.
   * The enrichment phone data process has its own tricks.