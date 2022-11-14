package algorithm.master.currencyconverterpro.data.remote.model


import com.google.gson.annotations.SerializedName

data class LatestRatesResponse(
    @SerializedName("base") val base: String,
    @SerializedName("date") val date: String,
    @SerializedName("rates") val rates: HashMap<String, String>,
    @SerializedName("success") val success: Boolean,
    @SerializedName("timestamp") val timestamp: Int
)
