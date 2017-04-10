package com.yalantis.jellyanimation.demo.ff

import android.os.IBinder
import com.yalantis.jellyanimation.demo.PARSEINT
import com.yalantis.jellytoolbar.QUNIMAD

/**
 * Created by arvin on 2017-4-9.
 */

val user = "xiaoyi"
var name: String = "nidie"
var any: Any = 2;
val items = listOf<String>("a", "b", "c")

fun main(args: Array<String>) {
    print("hello kotlin, i'm ${user}")
    if (any is String) {
        println("${add("xiao", "yi")}")
    } else {
        println(false)
    }
    for (item in items) {

    }
    for (index in items.indices) {
        println(index)
    }
    items.forEachIndexed { i, s ->

        println("$i  :    $s")
    }
    items.forEach {
        print(it+"__A")
    }

    val nums = listOf(1,2,3,"nimabi")
    for (num in nums) {
        println(parseString(num))
        when (num) {
            1 -> print("")
        }
        when{
            num is String -> print("")
            else -> print("xx")
        }
  }
    val  i = 13;
    if (i in 1..53)
        print("sss")
    for (x in 10 downTo  0 step 3){
        print(x)
    }



    val fruits = listOf("banana", "avocado", "apple", "kiwi")
    fruits.filter { it.startsWith("a") }
            .sortedBy { it }
            .map { it.toUpperCase()
            }
            .forEach {
                it.qunimabi(4)
                print(it) }


}

fun add(x1: String, x2: String): String {

    return "$x1 : $x2"
}

fun String.qunimabi(count : Int){
    for (i in 0..count){
        print("qunimabi")
    }
}

fun parseString(str: Any): String =
        when (str) {
            1 -> "one"
            2 -> "two"
            3 -> "three"
            "nimabi" -> "godie"
            else -> "null"

        }