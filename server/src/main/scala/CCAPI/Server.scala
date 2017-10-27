package CCAPI

import CCAPI.parser.ExpressionParser
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn
import scala.util.{Failure, Success}

object Server {
  def startServer() {
    implicit val system: ActorSystem = ActorSystem("calculus-system")
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext: ExecutionContextExecutor = system.dispatcher

    val route =
      path("calculate") {
        get {
          parameter("expression") { expression => {
            println(s"expression we get from client: $expression")
            val parser = new ExpressionParser(expression)
            parser.parseExpression() match {
              case Success(parsed) =>
                println(s"parsed expr: $parsed")
                complete(StatusCodes.OK)
              case Failure(error) =>
                println(s"error: $error")
                complete(StatusCodes.BadRequest, error)
            }
          }}
        }
      }

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}
