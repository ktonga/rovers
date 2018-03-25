package org.jinilover.rovers

import scalaz.\/

trait Types {

  sealed trait Command
  case object L extends Command
  case object R extends Command
  case object M extends Command

  sealed trait Direction
  case object E extends Direction
  case object S extends Direction
  case object W extends Direction
  case object N extends Direction

  case class Coordinate(x: Int, y: Int)

  case class Position(coord: Coordinate, heading: Direction) {
    def format: String = s"${coord.x} ${coord.y} $heading"
  }

  type MaybeSuccess[T] = String \/ T

  object MaybeSuccess {
    def apply[T](v: => T): MaybeSuccess[T] =
      \/.fromTryCatchNonFatal(v).leftMap(_.getMessage)
  }

  // for representing the processing status of the file
  sealed trait ProcessStatus
  // about to parse the upper right coordinate to setup the plateau
  case object SetupPlateau extends ProcessStatus
  // about to parse the next rover's position for landing
  case class LandNextRover(upperRight: Coordinate,
                             f: Position => List[Command] => Position) extends ProcessStatus
  // about to parse the commands to navigate the landed rover
  case class NavigateRover(upperRight: Coordinate,
                           f: List[Command] => Position) extends ProcessStatus
}
