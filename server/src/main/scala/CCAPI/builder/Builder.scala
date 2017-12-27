package CCAPI.builder

import scala.collection.mutable
import scala.reflect.runtime.currentMirror
import scala.tools.reflect.ToolBox
import CCAPI.parser._

object Builder {
  def buildFunc(input: List[String], body: String): SimpleFunction = {
    var builder = mutable.StringBuilder.newBuilder
    builder.append("(")
    input.foreach(in => builder.append(s"$in:Int,"))
    builder = builder.dropRight(1)
    builder.append(")")
    val inParams: String = builder.mkString("")
    val func: String = s"($inParams => $body)"
    // val calculation: String = s"$func.apply${calc.mkString("(", ",", ")")}"
    val toolbox = currentMirror.mkToolBox()
    new SimpleFunction(toolbox.parse(func))
  }

  def collectSP(
    body: List[Calculation], 
    collector: List[SubProcess]): List[SubProcess] = {
      val head::tail = body
      if (body == Nil) collector else collectSP(tail, collector :+ Definer(head))
  }

  def buildCompose(body: List[Calculation]): CompositeFunction = {
    val cf = new CompositeFunction()
    cf.addSubProcesses(collectSP(body, Nil))
    cf
  }

  def buildThen(input: List[String], body: List[Calculation]): ThenFunction = {
    val csp = new CompositeSubprocess(input)
    csp.addSubProcesses(collectSP(body, Nil))
    new ThenFunction(csp)
  }

  def buildZip(body: List[Calculation]): ZipFunction = {
    val zf = new ZipFunction()
    zf.addSubProcesses(collectSP(body, Nil))
    zf
  }

  def buildPair(body: List[Calculation]): PairFunction = {
    val pf = new PairFunction()
    pf.addSubProcesses(collectSP(body, Nil))
    pf
  }

  def Definer(elem: Calculation): SubProcess =
    elem match {
      case Func(input, body) => buildFunc(input, body)
      case Compose(body) => buildCompose(body)
      case Then(input, body) => buildThen(input, body)
      case Zip(body) => buildZip(body)
      case Pair(body) => buildPair(body)
    }

  def buildCP(original: List[Calculation], mapper: CompositeProcess): CompositeProcess = {
    mapper.addSubProcesses(collectSP(original, Nil))
    mapper
  }
}