package org.jinilover.rovers

object Utility {
  /**
   * check if the given coordinate is within the plateau outlined by the upperRight
   * @param coord
   * @param upperRight
   * @return
   */
  def withPlateau(coord: Coordinate, upperRight: Coordinate): Boolean = {
    val Coordinate(x, y) = coord
    val Coordinate(maxX, maxY) = upperRight
    0 <= x && x <= maxX && 0 <= y && y <= maxY
  }

}