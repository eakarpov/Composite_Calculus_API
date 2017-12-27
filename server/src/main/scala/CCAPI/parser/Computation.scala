package CCAPI.parser

import scala.reflect.runtime.currentMirror
import scala.tools.reflect.ToolBox

object Computation {
  def evalSync[A](string: String): A = {
    val toolbox = currentMirror.mkToolBox()
    val tree = toolbox.parse(string)
    println(tree)
    toolbox.eval(tree).asInstanceOf[A]
  }
}