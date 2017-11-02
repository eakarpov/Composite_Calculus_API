package CCAPI.applicative

import CCAPI.parser.{ExpressionParser, Func}
import org.scalatest._
import org.scalatest.TryValues._

class ExpressionParserTest extends FlatSpec with Matchers {

  "ExpressionParser" should "throw IAE on bad input" in {
    val parser = new ExpressionParser("somenewString")
    a [IllegalArgumentException] should be thrownBy {
      parser.parseExpression().get
    }
    the [IllegalArgumentException] thrownBy parser.parseExpression().get should have message "wrong input"
  }

  it should "throw IAE on bad func value" in {
    val parser = new ExpressionParser("with(abs).dsd")
    a [IllegalArgumentException] should be thrownBy {
      parser.parseExpression().get
    }
    the [IllegalArgumentException] thrownBy parser.parseExpression().get should have message "wrong func value"
  }

  it should "successfully parse expression" in {
    val parser = new ExpressionParser("with(12).do(((a) => { a + 1 }).then((a) => { a + 1 }))")
    noException should be thrownBy parser.parseExpression().get
    parser.parseExpression().success.value shouldBe
      Func(Some("do"), Vector("12"), Right(Vector(Func(None, Vector("a"), Left("a+1")), Func(Some("then"), Vector("a"), Left("a+1")))))
  }
}
