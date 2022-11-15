package algorithm.master.currencyconverterpro.data.util

import algorithm.master.currencyconverterpro.domain.model.ExceptionModel
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by promasterguru on 15/11/2022.
 */
fun Exception.resolve(): ExceptionModel {
    return try {
        when (this) {
            is HttpException -> {
                val errorModel = Gson().fromJson(
                    this.response()?.errorBody()?.string(), ExceptionModel::class.java
                )
                ExceptionModel(errorModel.message ?: NetworkConstants.GENERIC_ERROR)
            }
            is SocketTimeoutException -> ExceptionModel(message = NetworkConstants.CONNECTION_TIMEOUT_ERROR)
            is UnknownHostException -> ExceptionModel(message = NetworkConstants.UNKNOWN_HOST_ERROR)
            is JsonSyntaxException -> ExceptionModel(message = NetworkConstants.RESPONSE_ERROR)
            else -> ExceptionModel(message = NetworkConstants.GENERIC_ERROR)
        }
    } catch (ex: java.lang.Exception) {
        ExceptionModel(message = NetworkConstants.GENERIC_ERROR)
    }
}
