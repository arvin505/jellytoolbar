package com.yalantis.jellyanimation.demo

/**
 * Created by arvin on 2017-4-9.
 */
data class Customer(val name: String) {

}

sealed class Expr {
    class Const(val number: Double) : Expr()
    class Sum(val e1: Expr, val e2: Expr) : Expr()
    object NotANumber : Expr()
}

fun eval(expr: Expr): Double = when(expr) {
    is Expr.Const -> expr.number
    is Expr.Sum -> eval(expr.e1) + eval(expr.e2)
    Expr.NotANumber -> Double.NaN
// 不再需要 `else` 子句，因为我们已经覆盖了所有的情况
}

fun main(args: Array<String>) {
    val expr : Expr = Expr.Sum(Expr.Const(4.toDouble()),Expr.Const(4.toDouble()))

    print("expr  ｉｓ　${eval(expr)}")
}