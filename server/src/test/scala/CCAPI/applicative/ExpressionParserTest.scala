package CCAPI.applicative

import CCAPI.parser.ExpressionParser
import org.scalatest._

class ExpressionParserTest extends FlatSpec with Matchers {

  "some you can test" should "be cool" in {
    val parser = new ExpressionParser("somenewString")
    a [IllegalArgumentException] should be thrownBy {
      parser.parseExpression().get
    }
    the [IllegalArgumentException] thrownBy parser.parseExpression().get should have message "wrong input"
  }

  "some another test" should "be something cool" in {
    val parser = new ExpressionParser("with(abs).dsd")
    a [IllegalArgumentException] should be thrownBy {
      parser.parseExpression().get
    }
    the [IllegalArgumentException] thrownBy parser.parseExpression().get should have message "wrong func value"
  }
}
