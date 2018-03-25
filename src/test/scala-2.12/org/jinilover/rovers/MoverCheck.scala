package org.jinilover.rovers

import org.scalacheck.Properties
import org.scalacheck.Prop._

class MoverCheck extends Properties("Functions property-based testing") with Arbitraries {
  property("coordinates is always within the plateau boundary") =
    forAll { (upperRight: Coordinate, cmds: List[Command]) =>
      forAll(positionGenerator(upperRight).arbitrary) { (initPos: Position) =>
        val Coordinate(maxX, maxY) = upperRight
        val finalPos = Mover.executeCommands(upperRight)(initPos)(cmds)
        val Coordinate(x, y) = finalPos.coord
        0 <= x && x <= maxX && 0 <= y && y <= maxY
      }
    }

  property("position after a command") =
    forAll { (upperRight: Coordinate, cmd: Command) =>
      forAll(positionGenerator(upperRight).arbitrary) { (origPos: Position) =>
        val Coordinate(maxX, maxY) = upperRight
        val newPos = Mover.executeCommand(upperRight, origPos, cmd)
        cmd match {
          case M => origPos.heading == newPos.heading && (origPos match {
            case Position(Coordinate(x, y), E) =>
              if (x == maxX) newPos == origPos else newPos.coord == Coordinate(x+1, y)
            case Position(Coordinate(x, y), W) =>
              if (x == 0) newPos == origPos else newPos.coord == Coordinate(x-1, y)
            case Position(Coordinate(x, y), N) =>
              if (y == maxY) newPos == origPos else newPos.coord == Coordinate(x, y+1)
            case Position(Coordinate(x, y), S) =>
              if (y == 0) newPos == origPos else newPos.coord == Coordinate(x, y-1)
          })
          case _ => origPos.coord == newPos.coord && ((origPos.heading, cmd) match {
            case (E, L) => newPos.heading == N
            case (E, R) => newPos.heading == S
            case (S, L) => newPos.heading == E
            case (S, R) => newPos.heading == W
            case (W, L) => newPos.heading == S
            case (W, R) => newPos.heading == N
            case (N, L) => newPos.heading == W
            case (N, R) => newPos.heading == E
            case _ => true // shouldn't happen but scalac seems not smart enough
          })
        }
      }
    }
}