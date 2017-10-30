package CCAPI.parser

import scala.util.{Failure, Success, Try}

class ExpressionParser(expression: String) {

  private def parseFunc(func: String): Either[String, Either[Func,List[Func]]] = {
    val funcBodyRE = "(.*)\\(\\((.*)\\)=>\\{(.*)\\}\\)".r
    val funcSimpleBodyRE = "\\((.*)\\)=>\\{(.*)\\}".r
    val composedFuncRE = "\\.".r
    println(func)
    val (funcName, funcParams, funcBody) = func match {
      case funcBodyRE(x, y, z) => (x, y.split(',').toList, z)
      case funcSimpleBodyRE(x, y) => ("", x.split(',').toList, y)
      case composedFuncRE(x) => ("", Nil, x.split("."))
      case _ => return Left(func)
    }
    funcBody match {
      case x: Array[String] => {
        val result = Nil
        x.foreach(el => result :+ parseFunc(el))
        Right(Right(result)) //Need to be [Func] available also
      }
      case x: String => {
        if (funcName == "") return Right(Left(Func("", funcParams, Left(x))))
        val result = parseFunc(x)
        Right(Left(Func(funcName, funcParams, result)))
      }
    }
  }

  def parseExpression(): Try[Func] = {
    val expr = expression.filter(!_.isWhitespace)
    val (scope, func) = expr.split('.') match {
      case Array(x, y, _*) => (x, y)
      case _ => return Failure("wrong input")
    }
    val outerRE = "with\\((.*)\\)".r
    val arguments = scope match {
      case outerRE(args) => args.split(',').toList
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
    Success(Func("do", arguments, result))
  }
}
