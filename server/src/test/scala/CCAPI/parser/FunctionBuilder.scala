package CCAPI.applicative

import scala.meta.parsers._
import scala.meta._
import org.scalameta.logger
import org.scalatest._
import org.scalatest.TryValues._

class FunctionBuilderTest extends FlatSpec with Matchers {

  "some" should "be some" in {
    q"val func: Int = (x: Int) => { x * 2 }".syntax
    // "val func: Int = (x: Int) => { x * 2 }".parse[Stat].get
    // println(get)

    // val func = "x + y /* adds x and y */".parse[Term]
    // val d = "val x = 2".tokenize.get
    // print(d)
    // println(func)

    val tokens = "val x = 2".tokenize.get
        logger.elem(tokens.syntax)
        logger.elem(tokens.structure)

    val tree = "val x = 2".parse[Stat].get
        logger.elem(tree.syntax)
        logger.elem(tree.structure)
  }
}
