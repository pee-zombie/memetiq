package memetiq

//import cats.effect.{ExitCode, IO, IOApp}

import cats.effect._
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.server.blaze._
import scala.concurrent.ExecutionContext.global


object Main extends IOApp {
  def greeting(): String = "sup nerds"

  def run(args: List[String]): IO[ExitCode] =
    IO.println(greeting()).as(ExitCode.Success)
}