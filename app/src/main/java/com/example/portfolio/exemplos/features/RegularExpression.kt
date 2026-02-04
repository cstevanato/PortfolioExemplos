package com.example.portfolio.exemplos.features

fun example1()  {
    val pattern = "younes".toRegex(RegexOption.IGNORE_CASE)
    println(pattern.containsMatchIn("hello YouNes from"))
}

fun example2()  {
    val pattern1 = "[abc]younes".toRegex(RegexOption.IGNORE_CASE)
    var result = pattern1.containsMatchIn("hello YouNes from")
    println( "Test 1 result: $result")

    val pattern2 = "[abc]younes".toRegex(RegexOption.IGNORE_CASE)
    result = pattern2.containsMatchIn("hello aYouNes from")
    println( "Test 2 result: $result")

    val pattern3 = "youn[abce]s".toRegex(RegexOption.IGNORE_CASE)
    result = pattern3.containsMatchIn("hello aYouNes from")
    println( "Test 3 result: $result")

    val pattern4 = "youn[abce]s".toRegex(RegexOption.IGNORE_CASE)
    result = pattern4.containsMatchIn("hello aYounes from")
    println( "Test 4 result: $result")

    val pattern5 = "youn[a-kA-K]s".toRegex(RegexOption.IGNORE_CASE)
    result = pattern5.containsMatchIn("hello aYounws from")
    println( "Test 5 result: $result")

    val pattern6 = "yo(un)?es".toRegex(RegexOption.IGNORE_CASE)
    result = pattern6.containsMatchIn("hello Younes from")
    println( "Test 6 result: $result")
    result = pattern6.containsMatchIn("hello Yoes from")
    println( "Test 7 result: $result")

    val pattern7 = "yo(un)*es".toRegex(RegexOption.IGNORE_CASE)
    result = pattern7.containsMatchIn("hello Younunes from")
    println( "Test 8 result: $result")
    result = pattern7.containsMatchIn("hello Yoes from")
    println( "Test 9 result: $result")
}


fun execExamples() {
    example2()
}