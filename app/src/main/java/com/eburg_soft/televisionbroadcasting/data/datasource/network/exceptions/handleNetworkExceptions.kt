package com.eburg_soft.televisionbroadcasting.data.datasource.network.exceptions

import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

fun handleNetworkExceptions(ex: Exception): Exception {
    return when (ex) {
        is IOException -> NetworkConnectionException("Network connection error")
        is UnknownHostException -> NetworkConnectionException("IP address of a host could not be determined")
        is HttpException -> apiErrorFromCodeException(ex.code())
        else -> GenericNetworkException("Unknown error")
    }
}

private fun apiErrorFromCodeException(code: Int): Exception {
    return when (code) {
        400 -> BadRequestException("Bad Request")

        else ->
            GenericNetworkException("Unknown error")
    }
}