package algorithm.master.currencyconverterpro.presentation.adapter

import algorithm.master.currencyconverterpro.domain.model.history.RateModel
import algorithm.master.currencyconverterpro.presentation.ui.ConversionRateViewHolder
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

/**
 * Created by promasterguru on 15/11/2022.
 */
class ConversionRateAdapter :
    ListAdapter<RateModel, ConversionRateViewHolder>(CONVERSION_RATE_COMPARATOR) {

    companion object {
        val CONVERSION_RATE_COMPARATOR = object : DiffUtil.ItemCallback<RateModel>() {
            override fun areItemsTheSame(oldItem: RateModel, newItem: RateModel): Boolean {
                return oldItem.base == newItem.base && oldItem.to == newItem.to && oldItem.date == newItem.date
            }

            override fun areContentsTheSame(oldItem: RateModel, newItem: RateModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ConversionRateViewHolder.create(parent)

    override fun onBindViewHolder(holder: ConversionRateViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}
