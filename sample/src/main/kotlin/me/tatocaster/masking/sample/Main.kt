package me.tatocaster.masking.sample

import me.tatocaster.masking.annotation.Masking

fun main() {
    println("Hello World")
    println(5123.toString())

    test()
}

//@CustomMasking
@Masking
fun test() {
    println(User("First Name", "PassW0rD"))
}
