package algorithm.master.currencyconverterpro.data.remote.model


import algorithm.master.currencyconverterpro.domain.model.history.RateModel
import com.google.gson.annotations.SerializedName

data class TimeSeriesResponse(
    @SerializedName("base") val base: String,
    @SerializedName("end_date") val endDate: String,
    @SerializedName("rates") val rates: HashMap<String, HashMap<String, Double>>,
    @SerializedName("start_date") val startDate: String,
    @SerializedName("success") val success: Boolean,
    @SerializedName("timeseries") val timeseries: Boolean
) {
    fun toRates(): List<RateModel> {
        val currencyRates = mutableListOf<RateModel>()
        rates.forEach { rateEntry ->
            currencyRates.addAll(rateEntry.value.entries.map {
                RateModel(
                    base, it.key, it.value, rateEntry.key, endDate
                )
            })
        }
        return currencyRates
    }
}
