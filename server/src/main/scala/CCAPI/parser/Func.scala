package CCAPI.parser

class Func(funcName: Option[String], funcParams: Vector[String], funcBody: Either[String,  Vector[Func]]) {
  override def toString: String = s"$funcName, params: ${funcParams.mkString(",")}, body: $funcBody"
}

object Func {
  def apply(funcName: Option[String], funcParams: Vector[String], funcBody: Either[String, Vector[Func]]): Func =
    new Func(funcName, funcParams, funcBody)
}
