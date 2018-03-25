package org.jinilover.rovers

object Mover {
  // execute the whole series of commands
  def executeCommands(upperRight: Coordinate)(initialPos: Position)(cmds: List[Command]): Position =
    cmds.foldLeft(initialPos)(executeCommand(upperRight, _, _))

  // execute single command
  def executeCommand(upperRight: Coordinate, origPos: Position, cmd: Command): Position = {
    val Coordinate(maxX, maxY) = upperRight
    (origPos, cmd) match {
      case (Position(c@Coordinate(x, _), E), M) => origPos.copy(coord = c.copy(x = changeStep(x, 1, maxX)))
      case (Position(c@Coordinate(x, _), W), M) => origPos.copy(coord = c.copy(x = changeStep(x, -1, maxX)))
      case (Position(c@Coordinate(_, y), S), M) => origPos.copy(coord = c.copy(y = changeStep(y, -1, maxY)))
      case (Position(c@Coordinate(_, y), N), M) => origPos.copy(coord = c.copy(y = changeStep(y, 1, maxY)))
      case (Position(_, E), L) | (Position(_, W), R) => origPos.copy(heading = turnNorS(false))
      case (Position(_, E), _) | (Position(_, W), _) => origPos.copy(heading = turnNorS(true))
      case (Position(_, S), L) | (Position(_, N), R) => origPos.copy(heading = turnEorW(false))
      case _ => origPos.copy(heading = turnEorW(true))
    }
  }

  val turn: Direction => Direction => Boolean => Direction =
    left => right => pickRight => if (pickRight) right else left

  val turnNorS: Boolean => Direction = turn(N)(S)
  val turnEorW: Boolean => Direction = turn(E)(W)

  def changeStep(n: Int, change: Int, limit: Int): Int = {
    val newN = n + change
    if (newN < 0 || newN > limit) n else newN
  }


}