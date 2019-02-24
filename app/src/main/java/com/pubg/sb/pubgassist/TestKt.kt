package com.pubg.sb.pubgassist



fun main(args: Array<String>) {
    val list = arrayListOf("1","2")
    changeList(list)
    println(list)
}

fun changeList(list: ArrayList<String>) {
    list.removeAt(0)
}
