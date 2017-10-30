package CCAPI.parser

class Func(funcName: String, funcParams: List[String], funcBody: Either[String,  Either[Func, List[Func]]]) {
  override def toString: String = s"$funcName, params: ${funcParams.mkString(",")}, body: $funcBody"
}

object Func {
  def apply(funcName: String, funcParams: List[String], funcBody: Either[String, Either[Func, List[Func]]]): Func =
    new Func(funcName, funcParams, funcBody)
}
