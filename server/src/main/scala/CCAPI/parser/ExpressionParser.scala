package CCAPI.parser

import scala.util.{Failure, Success, Try}

class ExpressionParser(expression: String) {

  implicit def convertToException(msg: String): Throwable = {
    new IllegalArgumentException(msg)
  }

  def parseExpression(): Try[String] = {
    val check: (String, String) = expression.split('.') match {
      case Array(x, y, _*) => (x, y)
      case _ => return Failure("wrong param")
    }
    Success(expression)
  }
}
