package CCAPI.traits

trait Constituents[T1, T2] {
  def constant(value: T2): ComputingProcess[T1, T2]
}
