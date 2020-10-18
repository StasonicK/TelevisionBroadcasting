package com.eburg_soft.televisionbroadcasting.presentation.utils

import kotlin.random.Random

fun <T> getRandomElementFromList(list: List<T>): T {
    val randomValue = Random.nextInt(list.size)
    return list.elementAt(randomValue)
}

fun <T> getRandomElementFromSet(list: Set<T>): T {
    val randomValue = Random.nextInt(list.size)
    return list.elementAt(randomValue)
}

//fun getItem(supportId: String, goalId: String?, generateId: (m: String) -> Unit, saveId: (n: String) -> Unit) {
//    if (goalId.isNullOrBlank()) generateId(supportId) else saveId(goalId)
//}
//
//fun getItem(goalId: String?, generateId: () -> Unit, saveId: (n: String) -> Unit) {
//     if (goalId.isNullOrBlank()) generateId() else saveId(goalId)
//}
