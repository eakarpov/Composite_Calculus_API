package CCAPI.parser

import org.parboiled2._

import scala.collection.mutable
import scala.util.{Failure, Success, Try}

sealed trait Func
case class ComputingProcess(params: CalcParams, functions: List[Func])
case class CalcParams(list: List[Int])

case class ThenFunc(input: List[String], body: Func) extends Func
case class ComposeFunc(input: List[String], body: Func) extends Func
case class ZipFunc(input: List[String], body: Func) extends Func
case class PairFunc(input: List[String], body: Func) extends Func
case class Function(input: List[String], body: String) extends Func

class CompositeParser(val input: ParserInput) extends Parser {
  def InputLine: Rule1[ComputingProcess] = rule { Expression ~ EOI }

  def Expression: Rule1[ComputingProcess] = rule { Params ~ '.' ~ MainFunc ~> ComputingProcess }

  def Params: Rule1[CalcParams] = rule {
    "with" ~ '(' ~ ParamsArr ~ ')' ~> ((s: Seq[Int]) => CalcParams(s.toList))
  }
  def ParamsArr: Rule1[Seq[Int]] = rule { zeroOrMore(Number).separatedBy(',') }

  def MainFunc: Rule1[List[Func]] = rule {
    "do" ~ '(' ~ MainFuncBody ~ ')' ~> ((s: Seq[Func]) => s.toList)
  }
  def MainFuncBody: Rule1[Seq[Func]] = rule { oneOrMore(Func).separatedBy('.') }

  def Func: Rule1[Func] = rule {
    ThenFunc | ComposeFunc | ZipFunc | PairFunc | Function
  }

  def ThenFunc: Rule1[Func] = rule {
      "then" ~ '(' ~  FunctionParams ~ "=>" ~ MainFuncBody ~ ')' ~> ThenFunc
  }

  def ComposeFunc: Rule1[Func] = rule {
     "compose" ~ '(' ~ FuncList ~ ')' ~> ComposeFunc
  } 
  
  def ZipFunc: Rule1[Func] = rule {
     "zip" ~ '(' ~ FuncList ~ ')' ~> ZipFunc
  }
  
  def PairFunc: Rule1[Func] = rule {
    "pair" ~ '(' ~ FuncList ~ ')' ~> PairFunc
  }
  
  def Function: Rule1[Func] = rule {
     "" ~ '(' ~ FunctionParams ~ "=>" ~ FunctionBody ~ ')' ~> Function
  }

  def FuncList: Rule1[List[Func]] = rule {
    oneOrMore(Func).separatedBy(',') ~> ((s: Seq[Func]) => s.toList)
  }

  def FunctionParams: Rule1[List[String]] = rule { '(' ~ FuncInput ~ ')' }
  def FuncInput: Rule1[List[String]] = rule { capture(Input) ~> ((inp: String) => inp.split(',').toList) }
  def Input: Rule0 = rule { zeroOrMore(CharPredicate.LowerAlpha).separatedBy(',') }
  def FunctionBody: Rule1[String] = rule { capture(Body) }
  def Body: Rule0 = rule { oneOrMore(CharPredicate.LowerAlpha ++ CharPredicate.Digit ++ "+" ++ "-" ++ "*" ++ "/") }

  def Number: Rule1[Int] = rule { capture(Digits) ~> ((chars: String) => chars.toInt) }
  def Digits: Rule0 = rule { oneOrMore(CharPredicate.Digit) }
}
