package CCAPI
import scala.util.{Failure, Success, Try}
import akka.actor.ActorSystem
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{HttpApp, Route}
import akka.{Done, pattern}
import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._
import scala.util.{Failure, Success}
import scala.meta.parsers._
import scala.meta._
import org.scalameta.logger
import CCAPI.parser._
import org.parboiled2._
import scala.collection.mutable

object Server extends HttpApp {

  override protected def routes: Route =
    path("calculate") {
      println("calculate")
      get {
        parameter("expression") { expression => {
          val parser = new CompositeParser("with(2,5).do(compose((a,b)=>a+b+1))")
          val res: Try[ComputingProcess] = parser.InputLine.run()
          res match {
            case Success(x) => x match {
              case ComputingProcess(params, head :: _) => head match {
                case Func(input, body) => {
                  var builder = mutable.StringBuilder.newBuilder
                  builder.append("(")
                  input.foreach(in => builder.append(s"$in:Int,"))
                  builder = builder.dropRight(1)
                  builder.append(")")
                  val inParams: String = builder.mkString("")
                  val func: String = s"($inParams => $body)"
                  params match {
                    case CalcParams(calc) => {
                      val calculation: String = s"$func.apply${calc.mkString("(", ",", ")")}"
                      println(Computation.evalSync[Int](calculation))
                      complete(StatusCodes.OK)
                    }
                    case _ => complete(StatusCodes.BadRequest)
                  }
                }
                case _ => {
                  println(head)
                  complete(StatusCodes.BadRequest)
                }
              }
              case _ => complete(StatusCodes.BadRequest)
            }
            case Failure(err) => err match {
              case pe@ParseError(_, _, _) => {
                println(parser formatError pe)
                complete(StatusCodes.BadRequest)
              }
            }
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
