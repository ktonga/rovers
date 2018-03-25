package org.jinilover.rovers

import scalaz.{-\/, \/-}
import io._

object FileProcessor {
  def run(fileName: String): MaybeSuccess[Unit] =
    checkAndProcess(Source.fromFile(fileName), Source.fromFile(fileName))

  def checkAndProcess(sourceForSizeCheck: Source, sourceForProcess: => Source): MaybeSuccess[Unit] =
    for {
      count <- MaybeSuccess(sourceForSizeCheck.getLines.size)
      _ <- if (count % 2 == 1) \/-(()) else -\/(s"Invalid number of lines: $count from the file")
      _ <- process(sourceForProcess)
    } yield ()
  
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