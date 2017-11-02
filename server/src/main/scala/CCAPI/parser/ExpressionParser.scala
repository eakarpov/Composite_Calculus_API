package CCAPI.parser

import scala.util.matching.Regex
import scala.util.{Failure, Success, Try}

trait RegExps {
  val funcBodyRE: Regex = "(.*)\\(\\((.*)\\)=>\\{(.*)\\}\\)".r
  val funcSimpleBodyRE: Regex = "\\((.*)\\)=>\\{(.*)\\}".r
  val outerRE: Regex = "with\\((.*)\\)".r
  val outerFuncRE: Regex = "(.+?)\\((.+)\\)".r
}

class ExpressionParser(expression: String) extends RegExps {

  private def parseFunc(func: String): Either[String, Vector[Func]] = {

    val (funcName, funcParams, funcBody) = func match {
      case funcBodyRE(x, y, z) => (Some(x), y.split(',').toVector, Left(z))
      case funcSimpleBodyRE(x, y) => (None, x.split(',').toVector, Left(y))
      case funcAsString if funcAsString.contains('.') => (None, Vector[String](), Right(funcAsString.split(".").toVector))
      case _ => return Left(func)
    }

    funcBody match {
      case Right(vector) => Right(vector.map(parseFunc).foldLeft(Vector[Func]()) {
        (a, b) => b match {
          case Right(vec) => a ++ vec
          case _ => a
        }
      })
      case Left(single) =>
        if (funcName.isEmpty)  Right(Vector(Func(None, funcParams, Left(single))))
        else Right(Vector(Func(funcName, funcParams, parseFunc(single))))
    }
  }

  def parseExpression(): Try[Func] = {
    val expr = expression.filter(!_.isWhitespace)
    val (scope, func) = expr.split('.') match {
      case Array(x, y, _*) => (x, y)
      case _ => return Failure("wrong input")
    }
    val arguments: Vector[String] = scope match {
      case outerRE(args) => args.split(',').toVector
      case _ => return Failure("wrong param value")
    }
    val (funcName, funcBody) = func match {
      case outerFuncRE(x, y) => (x, y)
      case _ => return Failure("wrong func value")
    }
    if (funcName != "do") {
      return Failure("wrong func name")
    }
    val result = parseFunc(funcBody)
    Success(Func(Some("do"), arguments, result))
  }
}
