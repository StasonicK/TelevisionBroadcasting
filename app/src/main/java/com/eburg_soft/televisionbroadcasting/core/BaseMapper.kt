package com.eburg_soft.televisionbroadcasting.core

interface BaseMapper<in A, out B> {

    fun map(type: A?): B
}