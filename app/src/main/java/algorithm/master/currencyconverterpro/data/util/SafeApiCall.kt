package algorithm.master.currencyconverterpro.data.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by promasterguru on 15/11/2022.
 */
suspend inline fun <T> safeApiCall(crossinline body: suspend () -> T): ResponseWrapper<T> {
    return try {
        val res = withContext(Dispatchers.IO) { body.invoke() }
        ResponseWrapper.Success(res)
    } catch (ex: Exception) {
        ResponseWrapper.Failure(ex)
    }
}

sealed class ResponseWrapper<out T> {
    data class Success<D>(val data: D) : ResponseWrapper<D>()
    data class Failure<E>(val error: Exception) : ResponseWrapper<E>()
}
