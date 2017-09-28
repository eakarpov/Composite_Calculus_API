package CCAPI.traits

import CCAPI.applicative.ComputingProcess

trait Constituents[T1, T2] {
  def constant(value: T2): ComputingProcess[T1, T2]
}
