package CCAPI.architecture_example

object Main extends App {
  println(Constant[Int](5))
  println(Function[Int, Int](x => x * 2)(Constant[Int](6)))
  println(Compose[Int, Int, Int](Function[Int, Int](x => x * 2), Function[Int, Int](x => x + 2))(Constant[Int](2)))
}
