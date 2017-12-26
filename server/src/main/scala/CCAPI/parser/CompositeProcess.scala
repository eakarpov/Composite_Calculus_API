package CCAPI.parser

class CompositeProcess(val args: List[Int]) {
  val func: List[SubProcess] 

  def addSubProcesses(subProcesses: List[SubProcess]) {
    this.func = subProcesses
  }
}

sealed trait SubProcess(val elems = Nil)

class CompositeFunction extends SubProcess {
   def addElem(elem: SubProcess): Unit {
    this.elems :+ elem
  }
}

class ThenFunction(val sp: SubProcess) extends SubProcess

class PairFunction extends SubProcess {
   def addElem(elem: SubProcess): Unit {
    this.elems :+ elem
  }
}

class ZipFunction extends SubProcess {
   def addElem(elem: SubProcess): Unit {
    this.elems :+ elem
  }
}

class SimpleFunction(val func: Int => Int) extends SubProcess