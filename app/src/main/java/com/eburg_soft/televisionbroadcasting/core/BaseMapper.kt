package com.eburg_soft.televisionbroadcasting.data.repository.mappers

interface BaseMapper<in A, out B> {

    fun map(type: A?): B
}