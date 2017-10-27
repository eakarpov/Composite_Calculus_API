package CCAPI.parser

import scala.util.Try

class ExpressionParser(expression: String) {
  // в случае удачи возвращаем Success(parsedResult) иначе Failure(error)
  def parseExpression(): Try[String] = ???
}
