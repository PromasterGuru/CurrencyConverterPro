package algorithm.master.currencyconverterpro.data.remote.model


import android.icu.text.IDNA
import com.google.gson.annotations.SerializedName

data class ConvertCurrencyResponse(
    @SerializedName("date")
    val date: String,
    @SerializedName("historical")
    val historical: Boolean,
    @SerializedName("info")
    val info: Info,
    @SerializedName("query")
    val query: Query,
    @SerializedName("result")
    val result: Double,
    @SerializedName("success")
    val success: Boolean
)

data class Info(
    @SerializedName("rate")
    val rate: Double,
    @SerializedName("timestamp")
    val timestamp: Int
)

data class Query(
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("from")
    val from: String,
    @SerializedName("to")
    val to: String
)
