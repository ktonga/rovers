package org.jinilover.rovers

import scalaz.{-\/, \/-}

object Mover {
  // execute the whole series of commands
  def executeCommands(occupied: List[Coordinate])(upperRight: Coordinate)(initialPos: Position)(cmds: List[Command]): MaybeSuccess[Position] =
    cmds.foldLeft[MaybeSuccess[Position]](initialPos) {
      executeCommand(occupied, upperRight, _, _)
    }

  // execute single command
  def executeCommand(occupied: List[Coordinate], 
                     upperRight: Coordinate, 
                     origPos: Position, 
                     cmd: Command): MaybeSuccess[Position] = {
    val Coordinate(maxX, maxY) = upperRight
    (origPos, cmd) match {
      case (Position(c, E), M) =>
        moveHorizontal(changeStep(c.x, 1, maxX))(c)(occupied)(origPos)
      case (Position(c, W), M) =>
        moveHorizontal(changeStep(c.x, -1, maxX))(c)(occupied)(origPos)
      case (Position(c, S), M) =>
        moveVertical(changeStep(c.y, -1, maxY))(c)(occupied)(origPos)
      case (Position(c, N), M) =>
        moveVertical(changeStep(c.y, 1, maxY))(c)(occupied)(origPos)
      case (Position(_, E), L) | (Position(_, W), R) => rotateNorS(false)(origPos)
      case (Position(_, E), _) | (Position(_, W), _) => rotateNorS(true)(origPos)
      case (Position(_, S), L) | (Position(_, N), R) => rotateEorW(false)(origPos)
      case _ => rotateEorW(true)(origPos)
    }
  }

  val changeDirection: Direction => Direction => Boolean => Direction =
    left => right => pickRight => if (pickRight) right else left

  val rotateNorS: Boolean => Position => MaybeSuccess[Position] = 
    changeDirection(N)(S) andThen rotateRover
  
  val rotateEorW: Boolean => Position => MaybeSuccess[Position] = 
    changeDirection(E)(W) andThen rotateRover
  
  val rotateRover: Direction => Position => MaybeSuccess[Position] = 
    heading => origPos => \/-(origPos.copy(heading = heading))

  def changeStep(n: Int, change: Int, limit: Int): Int = {
    val newN = n + change
    if (newN < 0 || newN > limit) n else newN
  }

  val moveHorizontal: Int => Coordinate => List[Coordinate] => Position => MaybeSuccess[Position] =
    newX => {(_: Coordinate).copy(x = newX)} andThen moveRover

  val moveVertical: Int => Coordinate => List[Coordinate] => Position => MaybeSuccess[Position] =
    newY => {(_: Coordinate).copy(y = newY)} andThen moveRover

  val moveRover: Coordinate => List[Coordinate] => Position => MaybeSuccess[Position] =
    newCoord => occupied => origPos =>
      if (occupied contains newCoord)
        -\/(s"(${newCoord.x}, ${newCoord.y}) is already occuped by another rover")
      else
        \/-(origPos.copy(coord = newCoord))



}