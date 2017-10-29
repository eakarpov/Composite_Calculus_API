package CCAPI

package object parser {
  implicit def convertErrorMsgToThrowable(msg: String): Throwable = {
    new IllegalArgumentException(msg)
  }
}
