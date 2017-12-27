package CCAPI.parser

import CCAPI.builder._

object Store {
  private var _list: List[CompositeProcess] = Nil

  def addProcess(process: CompositeProcess): Int = {
    val nextIndex: Int = Store._list.size
    Store._list = _list :+ process
    nextIndex
  }

  def getProcess(index: Int): CompositeProcess = {
    Store._list(index)
  }
}