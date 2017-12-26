package CCAPI.builder

import scala.collection.mutable
import scala.reflect.runtime.currentMirror
import scala.tools.reflect.ToolBox

object Builder {
  def buildFunc(input, body) {
    var builder = mutable.StringBuilder.newBuilder
    builder.append("(")
    input.foreach(in => builder.append(s"$in:Int,"))
    builder = builder.dropRight(1)
    builder.append(")")
    val inParams: String = builder.mkString("")
    val func: String = s"($inParams => $body)"
    val calculation: String = s"$func.apply${calc.mkString("(", ",", ")")}"
    val toolbox = currentMirror.mkToolBox()
    val func = toolbox.parse(calculation)
    new SimpleFunction(func)
  } 

  def buildCompose(body) {
    val sp = new ComposeFunction()
    for (var x <- 0 to body.size) {
       sp.addElem(Definer(x))
    }
    sp
  }

  def buildThen(input, body) {
    val cp = new CompositeSubprocess(input)
    for ( x <- body) {
      cp.addElem(Definer(x))
    }
    val sp = new ThenFunction(cp)
    sp
  }

  def buildZip(body) {
    val sp = new ZipFunction()
    for (var x <- 0 to body.size) {
      sp.addElem(Definer(x))
    }
    sp
  }

  def buildPair(body) {
    val sp = new PairFunction()
    for (var x <- 0 to body.size) {
      sp.addElem(Definer(x))
    }
    sp
  }

  def Definer(elem) {
    elem match {
      case Func(input, body) => {
        buildFunc(input, body)
      }
      case Compose(body) {
        buildCompose(body)
      }
      case Then(input, body) {
        buildThen(input, body)
      }
      case Zip(body) {
        buildZip(body)
      }
      case Pair(body) {
        buildPair(body)
      }
      case _ => {
        None
      }
    }
  }

  def buildCP(original, mapper) {
    var sp = Nil
    for ( var x <- 0 to original.size) {
      x match {
        case Func(input, body) => {
          sp = sp :+ buildFunc(input, body)
        }
        case _ {
          sp = sp :+ buildCompose(body)
        }
        case Then(input, body) {
         sp = sp :+ buildThen(input, body)
        }
        case Zip(body) {
          sp = sp :+ buildZip(body)
        }
        case Pair(body) {
          sp = sp :+ buildPair(body)
        }
        case _ => {
        }
      }
    }
    mapper.addSubProcesses(sp)
    mapper
  }
}