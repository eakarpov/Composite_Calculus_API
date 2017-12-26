package CCAPI.parser

import org.parboiled2._

case class ComputingProcess(params: CalcParams, functions: List[Calculation])
case class CalcParams(list: List[Int])

sealed trait Calculation
case class Then(input: List[String], body: List[Calculation]) extends Calculation
case class Compose(body: List[Calculation]) extends Calculation
case class Zip(body: List[Calculation]) extends Calculation
case class Pair(body: List[Calculation]) extends Calculation
case class Func(input: List[String], body: String) extends Calculation

class CompositeParser(val input: ParserInput) extends Parser {
  def InputLine: Rule1[ComputingProcess] = rule { Expression ~ EOI }

  def Expression: Rule1[ComputingProcess] = rule { Params ~ '.' ~ MainFunc ~> ComputingProcess }

  def Params: Rule1[CalcParams] = rule {
    "with" ~ '(' ~ ParamsArr ~ ')' ~> ((s: Seq[Int]) => CalcParams(s.toList))
  }
  def ParamsArr: Rule1[Seq[Int]] = rule { zeroOrMore(Number).separatedBy(',') }

  def MainFunc: Rule1[List[Calculation]] = rule {
    "do" ~ '(' ~ MainFuncBody ~ ')'
  }
  def MainFuncBody: Rule1[List[Calculation]] = rule {
    oneOrMore(Calc).separatedBy('.') ~> ((s: Seq[Calculation]) => s.toList)
  }

  def Calc: Rule1[Calculation] = rule {
    ThenFunc | ComposeFunc | ZipFunc | PairFunc | Function
  }
  def ThenFunc: Rule1[Then] = rule { "then" ~ '(' ~ FunctionParams ~ "=>" ~ MainFuncBody ~ ')' ~> Then }
  def ComposeFunc: Rule1[Compose] = rule { "compose" ~ '(' ~ FuncList ~ ')' ~> Compose }
  def ZipFunc: Rule1[Zip] = rule { "zip" ~ '(' ~ FuncList ~ ')' ~> Zip }
  def PairFunc: Rule1[Pair] = rule { "pair" ~ '(' ~ FuncList ~ ')' ~> Pair }
  def Function: Rule1[Func] = rule { '(' ~ FuncInput ~ ')' ~ "=>" ~ FunctionBody ~> Func }

  def FuncList: Rule1[List[Calculation]] = rule {
    oneOrMore(Calc).separatedBy(",") ~> ((s: Seq[Calculation]) => s.toList)
  }

  def FunctionParams: Rule1[List[String]] = rule { '(' ~ FuncInput ~ ')' }
  def FuncInput: Rule1[List[String]] = rule { capture(Input) ~> ((inp: String) => inp.split(',').toList) }
  def Input: Rule0 = rule { zeroOrMore(CharPredicate.LowerAlpha).separatedBy(',') }
  def FunctionBody: Rule1[String] = rule { capture(Body) }
  def Body: Rule0 = rule {
   oneOrMore(CharPredicate.LowerAlpha ++ CharPredicate.Digit ++ "+" ++ "-" ++ "*" ++ "/")
  }

  def Number: Rule1[Int] = rule { capture(Digits) ~> ((chars: String) => chars.toInt) }
  def Digits: Rule0 = rule { oneOrMore(CharPredicate.Digit) }
}