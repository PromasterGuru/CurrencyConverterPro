package algorithm.master.currencyconverterpro.data.remote.model


import com.google.gson.annotations.SerializedName

data class TimeSeriesResponse(
    @SerializedName("base") val base: String,
    @SerializedName("end_date") val endDate: String,
    @SerializedName("rates") val rates: HashMap<String, HashMap<String, String>>,
    @SerializedName("start_date") val startDate: String,
    @SerializedName("success") val success: Boolean,
    @SerializedName("timeseries") val timeseries: Boolean
)
