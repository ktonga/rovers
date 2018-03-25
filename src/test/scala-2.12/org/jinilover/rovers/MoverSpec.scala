package org.jinilover.rovers

import org.scalatest.{Matchers, FlatSpec}
import Mover._

class MoverSpec extends FlatSpec with Matchers {
//  it should ("execute commands of changing heading only") in {
//    val upperRight = Coordinate(5, 5)
//    val initPos = Position(Coordinate(1, 3), E)
//    val cmds = List(L, L, L, R)
//    val expectedInterPoses = List(
//      initPos.copy(heading = N),
//      initPos.copy(heading = W),
//      initPos.copy(heading = S),
//      initPos.copy(heading = W)
//    )
//
//    stepCheck(upperRight, initPos, cmds, expectedInterPoses)
//  }
//
//  it should ("execute commands of minimum reqt. 1") in {
//    val upperRight = Coordinate(5, 5)
//    val initPos = Position(Coordinate(1, 2), N)
//    val cmds = List(L, M, L, M, L, M, L, M, M)
//    val expectedInterPoses = List(
//      Position(Coordinate(1, 2), W),
//      Position(Coordinate(0, 2), W),
//      Position(Coordinate(0, 2), S),
//      Position(Coordinate(0, 1), S),
//      Position(Coordinate(0, 1), E),
//      Position(Coordinate(1, 1), E),
//      Position(Coordinate(1, 1), N),
//      Position(Coordinate(1, 2), N),
//      Position(Coordinate(1, 3), N)
//    )
//
//    stepCheck(upperRight, initPos, cmds, expectedInterPoses)
//  }
//
//  it should ("execute commands of minimum reqt. 2") in {
//    val upperRight = Coordinate(5, 5)
//    val initPos = Position(Coordinate(3, 3), E)
//    val cmds = List(M, M, R, M, M, R, M, R, R, M)
//    val expectedInterPoses = List(
//      Position(Coordinate(4, 3), E),
//      Position(Coordinate(5, 3), E),
//      Position(Coordinate(5, 3), S),
//      Position(Coordinate(5, 2), S),
//      Position(Coordinate(5, 1), S),
//      Position(Coordinate(5, 1), W),
//      Position(Coordinate(4, 1), W),
//      Position(Coordinate(4, 1), N),
//      Position(Coordinate(4, 1), E),
//      Position(Coordinate(5, 1), E)
//    )
//
//    stepCheck(upperRight, initPos, cmds, expectedInterPoses)
//  }
//
//  private def stepCheck(upperRight: Coordinate, initPos: Position, cmds: List[Command], expectedInterPoses: List[Position]) = {
//    // check the position of after each command, i.e. test executeCommand
//    val correctPos = cmds.zip(expectedInterPoses).foldLeft(initPos) {
//      case (pos, (cmd, expectedPos)) =>
//        executeCommand(upperRight, pos, cmd) shouldBe (expectedPos)
//        expectedPos
//    }
//
//    // check the final position, i.e. test executeCommand
//    executeCommands(upperRight)(initPos)(cmds) shouldBe (correctPos)
//  }
}