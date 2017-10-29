package CCAPI.parser

import scala.util.Try

class Func(funcName: String, funcParams: Array[String], funcBody: Either[String,Func]) {
  override def toString(): String = {
    funcName + ", params: " + funcParams.mkString(",") + ", body: " + funcBody
  }
}
