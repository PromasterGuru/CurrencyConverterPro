package algorithm.master.currencyconverterpro.data.remote.model


import algorithm.master.currencyconverterpro.domain.model.history.RateModel
import com.google.gson.annotations.SerializedName

data class LatestRatesResponse(
    @SerializedName("base") val base: String,
    @SerializedName("date") val date: String,
    @SerializedName("rates") val rates: HashMap<String, Double>,
    @SerializedName("success") val success: Boolean,
    @SerializedName("timestamp") val timestamp: Int
) {
    fun toRates() = rates.map { RateModel(base, it.key, it.value.toFloat(), date, "") }
}
