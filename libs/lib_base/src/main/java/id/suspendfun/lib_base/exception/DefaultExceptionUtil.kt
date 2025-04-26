package id.suspendfun.lib_base.exception

import java.io.IOException

/**
 * Use this class to handle the exception
 * This class is still too simple, need improvement later
 */

// The IOException is exception from retrofit
fun remoteExceptionHandler(throwable: Throwable): DefaultExceptionData {
    return when (throwable) {
        is IOException -> DefaultExceptionData(
            code = DEFAULT_REMOTE_EXCEPTION_CODE,
            message = DEFAULT_NO_CONNECTION_MESSAGE
        )

        else -> DefaultExceptionData(
            code = DEFAULT_REMOTE_EXCEPTION_CODE,
            message = throwable.message.toString()
        )
    }
}

fun getDefaultRemoteException(): DefaultExceptionData {
    return DefaultExceptionData(
        code = NOT_FOUND_ERROR_CODE,
        message = NOT_FOUND_MESSAGE
    )
}

fun localExceptionHandler(ioException: IOException): DefaultExceptionData {
    return DefaultExceptionData(
        code = NOT_FOUND_ERROR_CODE,
        message = ioException.message.toString()
    )
}

// Assume that throw error is bad gateway, so 502 will be used as error code
private const val DEFAULT_REMOTE_EXCEPTION_CODE = 502
private const val DEFAULT_NO_CONNECTION_MESSAGE = "No connection"

// Assume that get data from local storage is failed
private const val NOT_FOUND_ERROR_CODE = 404
private const val NOT_FOUND_MESSAGE = "Error getting new data or data not found"
