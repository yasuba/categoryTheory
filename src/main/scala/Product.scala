object CategoryTheory extends App {

  //sealed trait CategoryOject
  case object A {
    val value = 2
  }
  case object B {
    val value = 4
  }

  case object C {
    val value = 2
  }
  //case class Product[A, B](fst: A => A, snd: B => B) extends CategoryOject
  //case class Category[C](c: C, categoryObject: CategoryOject)

  //val pair = Product(fst, snd)
  //val one: Int = pair.fst(0)
  //val two: Int = pair.snd(0)



  /* theory
   𝒞(C, AxB) ≅ 𝒞(C,A) x 𝒞(C,B)
   fst ∘ h = f
   fst andThen h = f
  */

  //val firstCategory = Category(C, pair)
  //val secondCategory = Category(C, A)
  //val thirdCategory = Category(C, B)

  //firstCategory == secondCategory x thirdCategory

  val a = A
  val b = B
  val c = C

  val fst: Int => Int = ab => ab / b.value
  val snd: Int => Int = ab => ab / a.value
  val h: Int => Int = c => c * 4
  val f: Int => Int = c => c * 1
  val g: Int => Int = c => c * 2

  // proof - fst ∘ h = f

  val composedFunc = h andThen fst

  val result = composedFunc(2) == f(2)


  println("The result is: " + result)
}


