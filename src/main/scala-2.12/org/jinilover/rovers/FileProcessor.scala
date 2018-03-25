package org.jinilover.rovers

import scalaz.{-\/, \/-}
import io._

object FileProcessor {
  def run(fileName: String): MaybeSuccess[Unit] =
    for {
      count <- MaybeSuccess(Source.fromFile(fileName).getLines.size)
      _ <- if (count % 2 == 1) \/-(()) else -\/(s"Invalid number of lines: $count from $fileName")
      _ <- process(Source.fromFile(fileName))
    } yield ()

  def process(source: Source): MaybeSuccess[ProcessStatus] =
    source.getLines.toStream.foldLeft[MaybeSuccess[ProcessStatus]](\/-(SetupPlateau)) {
      case (-\/(err), _) =>
        -\/(err)
      case (\/-(SetupPlateau), s) =>
        Parser.parseUpperRight(s).map{ upperRight => LaunchNextRover(upperRight, Mover.executeCommands(upperRight)) }
      case (\/-(LaunchNextRover(upperRight, f)), s) =>
//        Parser.parsePosition(upperRight, s).map{ pos => LaunchedRover(upperRight, f(pos)) }
???
      case (\/-(LaunchedRover(upperRight, f)), s) =>
        Parser.parseCmds(s).map { cmds =>
          println(f(cmds).format)
          LaunchNextRover(upperRight, Mover.executeCommands(upperRight))
        }
    }
}