package CCAPI.parser

class Func(funcName: String, funcParams: Array[String], funcBody: Either[String, Func]) {
  override def toString: String = funcName + ", params: " + funcParams.mkString(",") + ", body: " + funcBody
}

object Func {
  def apply(funcName: String, funcParams: Array[String], funcBody: Either[String, Func]): Func =
    new Func(funcName, funcParams, funcBody)
}
