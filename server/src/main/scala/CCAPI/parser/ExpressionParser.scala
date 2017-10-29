package CCAPI.parser

import scala.util.{Failure, Success, Try}

class ExpressionParser(expression: String) {

  implicit def convertToException(msg: String): Throwable = {
    new IllegalArgumentException(msg)
  }

  def parseFunc(func: String): Either[String, Func] = {
    val funcBodyRE = "(.*)\\(\\((.*)\\)=>\\{(.*)\\}\\)".r
    val funcSimpleBodyRE = "\\((.*)\\)=>\\{(.*)\\}".r
    println(func)
    val (funcName, funcParams, funcBody) = func match {
      case funcBodyRE(x,y,z) => (x,y.split(','),z)
      case funcSimpleBodyRE(x,y) => ("", x.split(','), y)
      case _ => return Left(func)
    }
    if (funcName == "") return Right(new Func("", funcParams, Left(funcBody)))
    val result = parseFunc(funcBody)
    Right(new Func(funcName, funcParams, result))
  }

  def parseExpression(): Try[Func] = {
    val (scope, func) = expression.split('.') match {
      case Array(x, y, _*) => (x, y)
      case _ => return Failure("wrong input")
    }
    val outerRE = "with\\((.*)\\)".r
    val arguments = scope match {
      case outerRE(args) => args.split(',')
      case _ => return Failure("wrong param value")
    }
    val outerFuncRE = "(.+?)\\((.+)\\)".r
    val (funcName, funcBody) = func match {
      case outerFuncRE(x, y) => (x, y)
      case _ => return Failure("wrong func value")
    }
    if (funcName != "do") {
      return Failure("wrong func name")
    }
    val result = parseFunc(funcBody)
    println(result)
    Success(new Func("do", arguments, result))
  }
}
