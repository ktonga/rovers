package org.jinilover.rovers

import org.scalatest.{Matchers, FlatSpec}
import FileProcessor._
import io._
import scalaz.{\/-, -\/}

class FileProcessorSpec extends FlatSpec with Matchers {
  it should ("fail when no. of lines in file doesn't match") in {
    val resrcName = "/InvalidNoOfLines.txt"
    checkAndProcess(getSource(resrcName), getSource(resrcName)) shouldBe
      -\/(s"Invalid number of lines: 4 from the file")
  }

  it should ("fail when containing invalid coordinate") in {
    val resrcName = "/InvalidUpperRight.txt"
    checkAndProcess(getSource(resrcName), getSource(resrcName)) shouldBe
      -\/("""Number format error For input string: "*"""")
  }

  it should ("fail when containing invalid direction") in {
    val resrcName = "/InvalidDirection.txt"
    checkAndProcess(getSource(resrcName), getSource(resrcName)) shouldBe
      -\/("Invalid direction: n")
  }

  it should ("fail when containing invalid command") in {
    val resrcName = "/InvalidCommand.txt"
    checkAndProcess(getSource(resrcName), getSource(resrcName)) shouldBe
      -\/("Invalid command: 'm'")
  }

  it should ("fail when containing landing coordinate out of plateau") in {
    val resrcName = "/OutOfPlateau.txt"
    checkAndProcess(getSource(resrcName), getSource(resrcName)) shouldBe
      -\/("(3, 6) not within (0, 0) to Coordinate(5,5)")
  }

  it should ("process a valid file correctly") in {
    val resrcName = "/commandFile.txt"
    checkAndProcess(getSource(resrcName), getSource(resrcName)) shouldBe \/-(())
  }

  private def getSource(srcName: String): Source =
    Source.fromInputStream(getClass getResourceAsStream srcName)
}