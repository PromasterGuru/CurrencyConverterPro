package algorithm.master.currencyconverterpro.data.remote.model


import com.google.gson.annotations.SerializedName

data class AvailableCurrencyResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("symbols")
    val symbols: HashMap<String, String>
)
