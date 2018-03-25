package org.jinilover.rovers

import scalaz.{-\/, \/-}
import io._

/**
 * Processor that interpretes the input from the given file to land and navigate the rovers one by one
 */
object FileProcessor {
  def run(fileName: String): MaybeSuccess[Unit] =
    checkAndProcess(Source.fromFile(fileName), Source.fromFile(fileName))

  /**
   * check no. of lines to make sure it has odd no. of lines, i.e. line for the upper right corner,
   * 2 input lines for each rover
   *
   * If no. of lines is valid, it will proceed to processing the file, o.w. it will exit
   * @param sourceForSizeCheck
   * @param sourceForProcess
   * @return
   */
  def checkAndProcess(sourceForSizeCheck: Source, sourceForProcess: => Source): MaybeSuccess[Unit] =
    for {
      count <- MaybeSuccess(sourceForSizeCheck.getLines.size)
      _ <- if (count % 2 == 1) \/-(()) else -\/(s"Invalid number of lines: $count from the file")
      _ <- process(sourceForProcess)
    } yield ()

  /**
   * Parse the file content into coordinates of the rovers and commands to navigate the rovers
   * @param source
   * @return
   */
  private def process(source: Source): MaybeSuccess[ProcessStatus] =
    source.getLines.toStream.foldLeft[MaybeSuccess[ProcessStatus]](\/-(SetupPlateau)) {
      case (-\/(err), _) =>
        -\/(err)
      case (\/-(SetupPlateau), s) =>
        Parser.parseUpperRight(s).map{ upperRight => LandNextRover(upperRight, Mover.executeCommands(upperRight)) }
      case (\/-(LandNextRover(upperRight, f)), s) =>
        Parser.parsePosition(upperRight, s).map{ pos => NavigateRover(upperRight, f(pos)) }
      case (\/-(NavigateRover(upperRight, f)), s) =>
        Parser.parseCmds(s).map { cmds =>
          println(f(cmds).format)
          LandNextRover(upperRight, Mover.executeCommands(upperRight))
        }
    }
}