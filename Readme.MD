# Rovers
## Design
This application is implemented in Scala.  It is written in almost pure functional programming except reading file or writing output to console.  FP has the advantages of producing the same result after countless repeatable and type safety, i.e. once compiled, run correctly.

Application structure:
* The driver app - `RoverApp`
* Functions for reading the input from the file - `FileProcessor`, it uses `Parser` functions to parse the file content and `Mover` functions to execute the parsed commands.
* Functions for parsing the text string from the file - `Parser`, parsed text string into the corresponding ADT
* Functions for executing the parsed commands - `Mover`
* Algebraic data types for the domain model - `Types`

A few open-sourced libraries are used on top of Scala core libraries:
* Scalaz - for using traversable and junction
* Scalatest for unit-test
* Scalacheck for property-based testing

## Assumption
* If the command `M` will move the rover out of the plateau, the rover will stay on the same spot with the same heading.
* To make the solution slighly simpler, if the command `M` will move the rover to a spot already occupied by another rover, it will continue to move.  Therefore assumes the input file will provide appropriate commands to avoid "crashing".

## Execution environment
Since my laptop has been set to scala 2.12 a while ago, this application will be compiled and run using scala 2.12.  However, this application doesn't use any scala 2.12 features such that the `Either` right bias.

* To run the unit-test, execute `sbt test`
* To run the main app, execute `sbt "runMain org.jinilover.rovers.RoverApp fileName"`  A sample file is available from `/rovers/src/main/resources/commandFile.txt`