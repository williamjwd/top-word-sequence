# Top 'n' Word Sequence

## Problem Statement
Create a program executable from the command line that when given text(s) will return 100 of most common three word sequences in descending order of frequency.

## Actual Work Done
Top 'n' word sequence finder has a few enhancements in addition to solving the above problem statement
* It is a SpringBoot command-line application 
* Can process large files and runs efficiently
* Can run via Docker as well as via the command-line
* Can handle Unicode characters
* Following parameters are configurable
  - Word pattern used to split the string into words
  - Word sequence is default to 3 and it configurable to any number between 1 and 10
  - Top 'n' results to display is default to 100 and is configurable to any number between 1 and 100

### Design/Code Considerations
In addition to the configurable parameters, following design criteria were considered
* Uses Spring Framework and the classes are structured similar to production code
* Separation of index data store and the top 'n' cache.  This allows to maintain different depth for the index data store, and it can be reused for other use cases like type ahead word completion, or building interactive word cloud.
* Strategy pattern implemented for Input Reader and the word splitter classes.  Using the strategy pattern provides better readability and option for extension without changing a lot of code
* Separation of the service layer from the data store and cache layer.  This provides better readability and option for extension without changing a lot of code
* Use of interfaces to provide extensibility
* Separation of concerns in individual class files with unit tests for each class file
* Java docs for all public methods, classes, and interfaces.  Clear naming convention and comments where ever needed for better understandability

## How to run the program?

### Prerequisites
* Maven - I used Maven version 3.8.7 to build the project
* Java - I have tested the application with Java 19.0.1 

### Clone
Clone the top-word-sequence Git repository
```bash
git clone https://github.com/williamjwd/top-word-sequence.git
```

### Build
Navigate to the cloned repository folder in a terminal.  The project needs to be built using maven with the following command
```bash
mvn clean package
```
The above command generates `top-word-sequence-0.0.1-SNAPSHOT.jar` file in the target folder.

### Run the Application

Run the following command from the application folder by providing the file path as the parameter
```bash
java -jar target/top-word-sequence-0.0.1-SNAPSHOT.jar src/test/resources/moby-dick.txt
```
or by providing input via StdIn
```bash
cat src/test/resources/*.txt | java -jar target/top-word-sequence-0.0.1-SNAPSHOT.jar
```
Following parameters can be overridden by passing it as vm arguments

| Name                | Description                                                                                          | 
|---------------------|------------------------------------------------------------------------------------------------------|
| tws.word.pattern    | Pattern used to split a line to words                                                                | 
| tws.storage.depth   | Index storage depth.  This is used to index the words in a tree data structure.  Default is set to 3 |
| tws.cache.depth     | Cache storage depth.  This property is used for the word sequence and is default to 3                |
| tws.cache.size      | Top 'n' cache size.  This property is used for the cache storage to maintain the top 'n' records     |
| tws.print.separator | Separator used while printing the key and the code                                                   |

Parameters can be passed into the program as below

```bash
java -jar -Dtws.cache.size=50 -Dtws.cache.depth=2 target/top-word-sequence-0.0.1-SNAPSHOT.jar src/test/resources/moby-dick.txt
```
## Running via Docker

### Build Docker Image

```bash
docker build -t top-word-sequence .
```
### Run the Docker Image
```bash
docker run -it --rm --name top-word-sequence-app top-word-sequence
```
It uses `moby-dick.txt` available in the test/resources folder to run the program

## How to run the tests?

Run the following command to run all the tests
```bash
mvn test
```

## What you would do next, given more time (if anything)?
* Add more tests.  Although the existing tests provide `100% Class coverage`, `93% Method coverage`, and `92% Line coverage`, more tests needs to be added to test all the corner cases
* Improve the word splitting strategy to make it fool-proof.  Although the current strategy handles most use cases, it is built to handle only basic use cases
* Make the data store classes thread safe and process multiple files in separate threads
* File handling line by line to optimize memory usage.  Currently, the application parses the entire file and holds all the lines in the memory
* Change `Map<Long, Set<String>>` in `TopNCacheImpl` to `SetMultimap`
* Separate the cache out of the storage.  Currently, the index storage code manages the cache

## Are there bugs that you are aware of?
* The word splitting strategy isn't fool-proof.  Need to fix handling '-' at the end of the line, square brackets, etc.
* The `tws.word.pattern` property in the `application.properties` has issues with certain characters.  Works fine if the property is provided as program argument