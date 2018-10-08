
sealed trait CategoryOject
case object A extends CategoryOject {
  val value = 2
}
case object B extends CategoryOject {
  val value = 3
}

case object C extends CategoryOject {
  val value = 2
}
case class Product[A, B](fst: A => A, snd: B => B) extends CategoryOject

val fst: Int => Int = i => i + 1
val snd: Int => Int = i => i + 2
val pair = Product(fst, snd)
val one: Int = pair.fst(0)
val two: Int = pair.snd(0)

// TODO this should be C -> AxB
val h: Int => Int = _ => 2
// TODO this should be C -> A
val f: Int => Int = _ => 2


case class Category[C](c: C, categoryObject: CategoryOject)

/* theory
 𝒞(C, AxB) ≅ 𝒞(C,A) x 𝒞(C,B)
 fst ∘ h = f
 fst andThen h = f
*/

val firstCategory = Category(C, pair)
val secondCategory = Category(C, A)
val thirdCategory = Category(C, B)

//firstCategory == secondCategory x thirdCategory

// proof
(pair.fst andThen h) == f
