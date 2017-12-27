package CCAPI.builder

trait WithSubProcesses {
  var elems: List[SubProcess] = Nil

  def addSubProcesses(subProcesses: List[SubProcess]) = {
    this.elems = subProcesses
  }
}

class CompositeProcess(val args: List[Int]) extends WithSubProcesses

class CompositeSubprocess(val args: List[String]) extends WithSubProcesses

sealed trait SubProcess

class CompositeFunction extends SubProcess with WithSubProcesses

class ThenFunction(val cp: CompositeSubprocess) extends SubProcess

class PairFunction extends SubProcess with WithSubProcesses

class ZipFunction extends SubProcess with WithSubProcesses

class SimpleFunction(val func: Int => Int) extends SubProcess