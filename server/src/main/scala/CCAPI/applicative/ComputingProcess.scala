package CCAPI.applicative

abstract class ComputingProcess[T1, T2](input: T1, output: T2) {
  def Constant[R](cons: R): ComputingProcess[Unit, R]
  def Function[R1, R2](func: R1 => R2): ComputingProcess[R1, R2]
  def Compose[R1, R2, R3](func1: R2 => R3, func2: R1 => R2): ComputingProcess[R1, R3]
  def Pairing[R1, R2, R3](func1: R1 => R2, func2: R1 => R3): ComputingProcess[R1, (R2, R3)]
}