package org.jinilover.rovers

import scalaz._, Scalaz._

object Parser {
  def parseCoordinate(x: String, y: String): MaybeSuccess[Coordinate] =
    for {
      xValue <- parseInt(x)
      yValue <- parseInt(y)
    } yield Coordinate(xValue, yValue)

  def parseUpperRight(s: String): MaybeSuccess[Coordinate] = {
    val xs = s.split(" ").toList
    xs match {
      case x :: y :: Nil => parseCoordinate(x, y)
      case _ => -\/(s"""${xs.size} items in "$s", there should be 2 items""")
    }
  }

  def parsePosition(upperRight: Coordinate, s: String): MaybeSuccess[Position] = {
    val xs = s.split(" ").toList
    xs match {
      case x :: y :: d :: Nil =>
        for {
          coord <- parseCoordinate(x, y)
          _ <- if (Utility.withPlateau(coord = coord, upperRight = upperRight)) \/-(())
               else -\/(s"($x, $y) not within (0, 0) to $upperRight")
          dir <- parseDirection(d)
        } yield Position(coord, dir)
      case _ => -\/(s"""${xs.size} items in "$s", there should be 3 items""")
    }
  }

  val parseDirection: String => MaybeSuccess[Direction] = {
    case "E" => \/-(E)
    case "S" => \/-(S)
    case "W" => \/-(W)
    case "N" => \/-(N)
    case unknown => -\/(s"Invalid direction: $unknown")
  }

  def parseCmds(s: String): MaybeSuccess[List[Command]] =
    s.map(parseCmd).toList.sequenceU

  var parseCmd: Char => MaybeSuccess[Command] = {
    case 'L' => \/-(L)
    case 'R' => \/-(R)
    case 'M' => \/-(M)
    case unknown => -\/(s"Invalid command: '$unknown'")
  }

  private def parseInt(s: String): MaybeSuccess[Int] =
    s.parseInt.disjunction.leftMap(err => s"Number format error ${err.getMessage}")
}