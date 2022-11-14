package algorithm.master.currencyconverterpro.data.remote.api

import algorithm.master.currencyconverterpro.data.remote.model.AvailableCurrencyResponse
import algorithm.master.currencyconverterpro.data.remote.model.ConvertCurrencyResponse
import algorithm.master.currencyconverterpro.data.remote.model.LatestRatesResponse
import algorithm.master.currencyconverterpro.data.remote.model.TimeSeriesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

/**
 * Created by promasterguru on 14/11/2022.
 */
interface CurrencyService {
    @GET("/exchangerates_data/convert")
    suspend fun convertCurrency(
        @Path("to") to: String,
        @Path("from") from: String,
        @Path("amount") amount: Int,
        @Query("date") data: Date
    ): ConvertCurrencyResponse

    @GET("/exchangerates_data/symbols")
    suspend fun getAllAvailableCurrencies(): AvailableCurrencyResponse

    @GET("/exchangerates_data/latest")
    suspend fun getRealTimeExchangeRate(
        @Path("symbols") symbols: String,
        @Path("base") base: String,
    ): LatestRatesResponse

    @GET("/exchangerates_data/timeseries")
    suspend fun getLatestConversions(
        @Path("end_date") endDate: Date,
        @Path("start_date") startDate: Date,
        @Query("base") base: String,
        @Query("symbols") symbols: String,
    ): TimeSeriesResponse
}
