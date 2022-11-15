package algorithm.master.currencyconverterpro.presentation.ui

import algorithm.master.currencyconverterpro.databinding.CurrencyItemBinding
import algorithm.master.currencyconverterpro.domain.model.history.RateModel
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by promasterguru on 15/11/2022.
 */
class ConversionRateViewHolder(private val binding: CurrencyItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(rateModel: RateModel) {
        binding.tvDate.text = rateModel.date
        binding.tvCurrencyAmount.text = String.format("%s %s", rateModel.amount, rateModel.to)
    }

    companion object {
        fun create(parent: ViewGroup) = ConversionRateViewHolder(
            CurrencyItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}
