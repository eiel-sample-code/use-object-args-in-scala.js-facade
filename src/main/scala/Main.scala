import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

object Main extends App {
  Hoge1.sample()
  Hoge2.sample()
  Hoge3.sample()
}

/// Facade

@JSImport("hoge.js", JSImport.Namespace)
@js.native
object Hoge extends js.Any {

  /* aの値はundefinedやnullになってもよいが必須 */
  /* function hoge(x: {| a: ?number |}) */
  def hoge(hoge: HogeArgs): Unit = js.native

  /* a はあってもなくてもよい。nullはだめ */ // null はだめは何らかの仕組みを考えないと制限できない。
  /* function hoge2(x: {| a?: number |}) */
  def hoge2(hoge: Hoge2Args): Unit = js.native

  /* a は必須 */
  /* function hoge3(x: {| a: number |}) */
  def hoge3(hoge: Hoge3Args): Unit = js.native
}


/// Hoge1

object Hoge1 {
  import Hoge._

  def sample(): Unit = {
    println("hoge1")
    hoge(HogeArgs(a = 1))  // => { a: 1 }
    hoge(HogeArgs(a = js.undefined))  // => { a: undefiend }
    hoge(HogeArgs(a = null))  // => { a: null } // しかし、nullはかきたくない。
    hoge(HogeArgs()) // => {a: undefiend }
  }
}

trait HogeArgs extends js.Object {
  val a: js.UndefOr[Int] = js.undefined
}

object HogeArgs {
  def apply(a: js.UndefOr[Int] = js.undefined): HogeArgs = {
    val a_ = a
    new HogeArgs {
      override val a: js.UndefOr[Int] = a_
    }
  }
}

/// Hoge2
object Hoge2 {
  import Hoge._

  def sample(): Unit = {
    println("hoge2")
    hoge2(Hoge2Args(a = 1)) // => { a: 1 }
    // hoge2(Hoge2Args(a = js.undefined)) // compile error
    // hoge2(Hoge2Args(a = null)) // compile error
    hoge2(Hoge2Args()) // => {}  // { a: undefined } とは等価と考える
  }
}

trait Hoge2Args extends js.Object

trait Hoge2ArgWithA extends Hoge2Args {
  val a: Int
}

object Hoge2Args {
  def apply(a: Int): Hoge2Args = {
    val a_ = a
    new Hoge2ArgWithA {
      override val a: Int = a_
    }
  }

  def apply(): Hoge2Args = {
    new Hoge2Args {}
  }
}

/// Hoge3

object Hoge3 {
  import Hoge._

  def sample(): Unit = {
    println("hoge3")
    hoge3(Hoge3Args(a = 1)) // => { a: 1 }
    // hoge3(Hoge3Args(a = js.undefined)) // compile error
    // hoge3(Hoge3Args(a = null)) // compile error
    // hoge3(Hoge3Args()) // compile error
  }
}

trait Hoge3Args extends js.Object {
  val a: Int
}

object Hoge3Args {
  def apply(a: Int): Hoge3Args = {
    val a_ = a
    new Hoge3Args {
      override val a: Int = a_
    }
  }
}

