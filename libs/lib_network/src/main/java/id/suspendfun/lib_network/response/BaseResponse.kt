package id.suspendfun.lib_network.response

/**
 * Default response class for retrofit api call result
 */
sealed class BaseResponse<out T> {
    data object Loading : BaseResponse<Nothing>()
    data class Success<T>(val data: T) : BaseResponse<T>()
    data class Failed<T>(val code: Int, val message: String) : BaseResponse<T>()
}
