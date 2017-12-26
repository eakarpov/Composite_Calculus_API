package CCAPI.parser

object Store {
  private var _list: List[Any] = Nil

  def addProcess(process): Int {
    val nextIndex = Store._list.size()
    Store._list = _list :+ process
    nextIndex
  }

  def getProcess(index: Int) {
    Store._list[].getOrElse(index)
  }
}