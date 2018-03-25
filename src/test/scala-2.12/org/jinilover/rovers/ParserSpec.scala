package org.jinilover.rovers

import org.scalatest.{Matchers, FlatSpec}
import Parser._
import scalaz._

class ParserSpec extends FlatSpec with Matchers {
  it should ("parse commands correctly") in {
    parseCmds("LMLMLMLMM") shouldBe \/-(List(L, M, L, M, L, M, L, M, M))
    parseCmds("MMRMMRMRRM") shouldBe \/-(List(M, M, R, M, M, R, M, R, R, M))
    parseCmds("MMRLMMRMLRRM") shouldBe \/-(List(M, M, R, L, M, M, R, M, L, R, R, M))
    parseCmds(" MMRLMMRMLRRM") shouldBe -\/("Invalid command: ' '")
    parseCmds("MMRLMMRMLRR M") shouldBe -\/("Invalid command: ' '")
    parseCmds("MMRLMMRMLRERM") shouldBe -\/("Invalid command: 'E'")
  }

  it should ("parse coordinate correctly") in {
    parseCoordinate("5", "5") shouldBe \/-(Coordinate(5, 5))
    parseCoordinate("1o", "2") shouldBe -\/("""Number format error For input string: "1o"""")
  }

  it should ("parse upper right correctly") in {
    parseUpperRight("5 5") shouldBe \/-(Coordinate(5, 5))
    parseUpperRight("5") shouldBe -\/("""1 items in "5", there should be 2 items""")
    parseUpperRight("5 5 5") shouldBe -\/("""3 items in "5 5 5", there should be 2 items""")
  }

  it should ("parse position correctly") in {
    val upperRight = Coordinate(5, 5)
    parsePosition(upperRight, Nil, "1 2 E") shouldBe \/-(Position(Coordinate(1, 2), E))
    parsePosition(upperRight, Nil, "1 2 S") shouldBe \/-(Position(Coordinate(1, 2), S))
    parsePosition(upperRight, Nil, "1 2 W") shouldBe \/-(Position(Coordinate(1, 2), W))
    parsePosition(upperRight, Nil, "1 2 N") shouldBe \/-(Position(Coordinate(1, 2), N))
    parsePosition(upperRight, Nil, "1 2 e") shouldBe -\/("Invalid direction: e")
    parsePosition(upperRight, Nil, "1 x2 N") shouldBe -\/("""Number format error For input string: "x2"""")
    parsePosition(upperRight, Nil, "1 2 2 N") shouldBe -\/("""4 items in "1 2 2 N", there should be 3 items""")
    parsePosition(upperRight, Nil, "0 0 E") shouldBe \/-(Position(Coordinate(0, 0), E))
    parsePosition(upperRight, Nil, "5 5 E") shouldBe \/-(Position(Coordinate(5, 5), E))
    parsePosition(upperRight, Nil, "5 6 E") shouldBe -\/(s"(5, 6) not within (0, 0) to $upperRight")
    parsePosition(upperRight, List(Coordinate(1, 3), Coordinate(1,2)), "1 2 E") shouldBe
      -\/("(1, 2) is occupied by other rover already")
  }
}