object Duals extends App {

  // First recreate the Product Category / Logical conjunction / AND / Tuple from the talk:

  case class Product[A, B](fst: A, snd: B)

  val pair: Product[Int, String] = Product(1, "two")
  val one: Int = pair.fst
  val two: String = pair.snd

  println(s"First is $one")
  println(s"Second is $two")

  val pairScala: ((Int, String)) = (1, "two")
  val oneScala: Int = pairScala._1
  val twoScala: String = pairScala._2

  println(s"First from scala standard lib is $oneScala")
  println(s"Second from scala standard lib is $twoScala")

  // Then recreate the Sum Category / Logical disjunction / OR / Either from the talk:

  sealed trait Sum[A, B]
  object Sum {
    case class Left[A, B](value: A) extends Sum[A, B]
    case class Right[A, B](value: B) extends Sum[A, B]
  }

  type ErrInt = Sum[String, Int]

  val err: ErrInt = Sum.Left("error")
  val rightOne: ErrInt = Sum.Right(1)

  def add(tHis: ErrInt, tHat: ErrInt): ErrInt =
    (tHis, tHat) match {
      case (Sum.Left(e), _) => Sum.Left(e)
      case (_, Sum.Left(e)) => Sum.Left(e)
      case (Sum.Right(m), Sum.Right(n)) => Sum.Right(m + n)
    }

  val test = add(rightOne, err)

  println(s"Result from own type is $test")

  type ScalaErrInt = Either[String, Int]

  val errScala: ScalaErrInt = Left("error")
  val rightScala: ScalaErrInt = Right(1)

  def add(tHis: ScalaErrInt, tHat: ScalaErrInt): ScalaErrInt =
    (tHis, tHat) match {
      case (Left(e), _) => Left(e)
      case (_, Left(e)) => Left(e)
      case (Right(m), Right(n)) => Right(m + n)
    }

  val testScala = add(rightScala, errScala)

  println(s"Result is $testScala")

  // Testing out the theory around function composition and duals (they are the inverse of each other):

  /* theory
    𝒞(C, AxB) ≅ 𝒞(C,A) x 𝒞(C,B)
    A function from C to A and a function from C to B will give you a function from C to AxB
    fst ∘ h = f
    fst andThen h = f
  */

  val fst: ((Int, String)) => Int = ab => ab._1
  val snd: ((Int, String)) => String = ab => ab._2
  val f: Int => Int = c => c / 2
  val g: Int => String = c => c.toString
  // given I have two functions f and g, where one divides c by 2 and the other makes c a string.
  // I can deduce that function h as a product - which is a tuple, must be a tuple of the two functions:
  val h: Int => ((Int, String)) = c => (f(c), g(c))
  val composedFuncCtoA = h andThen fst
  val composedFuncCtoB = h andThen snd

  val result1 = composedFuncCtoA(2) == f(2)
  val result2 = composedFuncCtoB(2) == g(2)

  println("fst ∘ h = f " + result1)
  println("snd ∘ h = g " + result2)

  /* theory
    𝒞(A+B, C) ≅ 𝒞(A,C) x 𝒞(B,C)
    Functions A to C and B to C will give you a function from A+B to C
    left ∘ h = f
    left andThen h = f
  */

  val left: String => Either[String, Int] = a => Left(a)
  val right: Int => Either[String, Int] = b => Right(b)
  val sumF: String => Int = _ => 2
  val sumG: Int => Int = b => b + 1
  //now I have functions sumF and sumG, I can create function sumH which is either sumF or sumG:

  val sumH: Either[String, Int] => Int = {
    case Right(b) => sumG(b)
    case Left(e) => sumF(e)
  }

  val sumResult1 = (left andThen sumH)("error") == sumF("error")
  val sumResult2 = (right andThen sumH)(1) == sumG(1)

  println("left ∘ h = f " + sumResult1)
  println("right ∘ h = g " + sumResult2)
}


