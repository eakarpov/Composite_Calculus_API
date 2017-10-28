package CCAPI

import CCAPI.parser.ExpressionParser
import akka.actor.ActorSystem
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{HttpApp, Route}
import akka.{Done, pattern}

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._
import scala.util.{Failure, Success}

object Server extends HttpApp {

  override protected def routes: Route =
    path("calculate") {
      println("calculate")
      get {
        parameter("expression") { expression => {
          println(s"expression we get from client: $expression")
          val parser = new ExpressionParser(expression)
          parser.parseExpression() match {
            case Success(parsed) =>
              println(s"parsed expression: $parsed")
              complete(StatusCodes.OK)
            case Failure(error) =>
              println(s"error: ${error.getMessage}")
              complete(StatusCodes.BadRequest -> error.getMessage)
          }
        }
        }
      }
    }

  override protected def postHttpBindingFailure(cause: Throwable): Unit = {
    println(s"The server could not be started due to $cause")
  }

  override def waitForShutdownSignal(actorSystem: ActorSystem)
                                    (implicit executionContext: ExecutionContext): Future[Done] = {
    pattern.after(5 days, actorSystem.scheduler)(Future.successful(Done))
  }

}
