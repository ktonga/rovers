package org.jinilover.rovers

import org.scalacheck.{Gen, Arbitrary}

/**
 * Generates random data for property-based testing
 */
trait Arbitraries {
  private val MAX_X = 25
  private val MAX_Y = MAX_X

  implicit val upperRightGenerator: Arbitrary[Coordinate] =
    Arbitrary {
      for {
        x <- Gen.choose(0, MAX_X)
        y <- Gen.chooseNum(0, MAX_Y)
      } yield Coordinate(x, y)
    }

  implicit val directionGenerator: Arbitrary[Direction] =
    Arbitrary { Gen.oneOf(E, S, W, N) }

  def positionGenerator(upperRight: Coordinate): Arbitrary[Position] =
    Arbitrary {
      for {
        direction <- directionGenerator.arbitrary
        Coordinate(xLimit, yLimit) = upperRight
        x <- Gen.choose(0, xLimit)
        y <- Gen.choose(0, yLimit)
      } yield Position(Coordinate(x, y), direction)
    }

  implicit val commandGenerator: Arbitrary[Command] =
    Arbitrary { Gen.oneOf(L, R, M) }
  
}