package org.jinilover.rovers

import scalaz.-\/

object RoverApp extends App {
  args.toList match {
    case file :: _ => FileProcessor.run(file) match {
      case -\/(err) => println(s"execution failed due to $err")
      case _ => 
    }
    case _ => println("Please specify a file name for the commands")
  }
}