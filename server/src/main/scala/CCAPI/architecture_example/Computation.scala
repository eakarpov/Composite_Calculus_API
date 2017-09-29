package CCAPI.architecture_example

sealed trait Computation

trait Computation2[T1, T2] extends Computation {
  def compute(value: T1): T2
}

trait Computation3[T1, T2, T3] extends Computation {
  def compute(value1: T1, value2: T2): T3
}

class Constant[T] extends Computation2[T, T] {
  override def compute(value: T): T = value
}

object Constant {
  def apply[T](value: T): T = new Constant().compute(value)
}

class Function[T1, T2] extends Computation2[T1 => T2, T1 => T2] {
  override def compute(func: T1 => T2): T1 => T2 = func
}

object Function {
  def apply[T1, T2](func: T1 => T2): T1 => T2 = new Function().compute(func)
}

class Compose[T1, T2, T3] extends Computation3[T2 => T3, T1 => T2, T1 => T3] {
  override def compute(compose1: T2 => T3, compose2: T1 => T2): T1 => T3 = compose1 compose compose2
}

object Compose {
  def apply[T1, T2, T3](compose1: T2 => T3, compose2: T1 => T2): T1 => T3 = new Compose().compute(compose1, compose2)
}
