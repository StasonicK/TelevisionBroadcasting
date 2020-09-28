package com.eburg_soft.televisionbroadcasting.core.datatype

import com.eburg_soft.televisionbroadcasting.core.datatype.ResultType.ERROR
import com.eburg_soft.televisionbroadcasting.core.datatype.ResultType.SUCCESS

data class Result<out T>(
    var resultType: ResultType,
    val data: T? = null,
    val error: Exception? = null
) {

    companion object {

        fun <T> success(data: T?): Result<T> {
            return Result(SUCCESS, data)
        }

        fun <T> error(error: Exception? = null): Result<T> {
            return Result(ERROR, error = error)
        }
    }
}