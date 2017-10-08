package CCAPI.applicative

trait ComputingProcess {
  def compute(value: Any): Any
}

class ComputingProcess[T1, T2](input: T1, output: T2) {
  def compute(value: T1): T2 = {
    ???
  }
  def constant[T2](value: T2): ComputingProcess[Unit, T2] = new ComputingProcess[Unit,T2](Unit, value)
  def compose[T1, T2, T3](f1: Function[T1, T2], f2: Function[T2, T3]) = {

  }
}

object ComputingProcess {
  def constant[T2](value: T2): ComputingProcess[Unit, T2] = new ComputingProcess[Unit,T2](Unit, value)
}