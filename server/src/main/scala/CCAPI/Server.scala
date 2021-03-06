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
import CCAPI.builder._
import CCAPI.executor._
import org.parboiled2._

object Server extends HttpApp {

  override protected def routes: Route =
    path("calculate") {
      println("calculate")
      get {
        parameters(('rType.as[String], 'expression.as[String])) { (expression, rType) => {
          val parser = new CompositeParser("with(1,2).do(compose((a)=>a+2,(b)=>2+3))")
          val res: Try[ComputingProcess] = parser.InputLine.run()
          res match {
            case Success(x) => x match {
              case ComputingProcess(params, funcs) => {
                params match {
                  case CalcParams(calc) => {
                    val cProcess = Builder.buildCP(funcs, new CompositeProcess(calc))
                    val res = Executor.execute(cProcess, rType)
                    println(res)
                    complete(StatusCodes.OK)
                  }
                  case _ => complete(StatusCodes.BadRequest)
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
