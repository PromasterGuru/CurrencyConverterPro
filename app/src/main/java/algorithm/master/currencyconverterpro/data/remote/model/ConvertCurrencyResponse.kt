package algorithm.master.currencyconverterpro.data.remote.model


import algorithm.master.currencyconverterpro.domain.model.converter.ConverterCurrencyModel
import com.google.gson.annotations.SerializedName

data class ConvertCurrencyResponse(
    @SerializedName("date") val date: String,
    @SerializedName("historical") val historical: Boolean,
    @SerializedName("info") val info: Info,
    @SerializedName("query") val query: Query,
    @SerializedName("result") val result: Double,
    @SerializedName("success") val success: Boolean
) {
    fun toCurrencyModel() = ConverterCurrencyModel(
        amount = this.query.amount,
        from = this.query.from,
        to = this.query.to,
        rate = this.info.rate,
        date = this.date,
        result = this.result
    )
}

data class Info(
    @SerializedName("rate") val rate: Double, @SerializedName("timestamp") val timestamp: Int
)

data class Query(
    @SerializedName("amount") val amount: Double,
    @SerializedName("from") val from: String,
    @SerializedName("to") val to: String
)
