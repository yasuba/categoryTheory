object Product extends App {

  case class Product[A, B](fst: A, snd: B)

  val pair: Product[Int, String] = Product(1, "two")
  val one: Int = pair.fst
  val two: String = pair.snd

  /* theory
   𝒞(C, AxB) ≅ 𝒞(C,A) x 𝒞(C,B)
   fst ∘ h = f
   fst andThen h = f
  */

  val fst: Int => Int = ab => ab / 4
  val snd: Int => Int = ab => ab / 2
  val h: Int => Int = c => c * 4
  val f: Int => Int = c => c * 1
  val g: Int => Int = c => c * 2

  // proof - fst ∘ h = f

  val composedFuncCtoA = h andThen fst

  val composedFuncCtoB = h andThen snd

  val result1 = composedFuncCtoA(2) == f(2)

  val result2 = composedFuncCtoB(2) == g(2)

  println("fst ∘ h = f " + result1)
  println("snd ∘ h = g " + result2)
}


