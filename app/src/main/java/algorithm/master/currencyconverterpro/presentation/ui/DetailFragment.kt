package algorithm.master.currencyconverterpro.presentation.ui

import algorithm.master.currencyconverterpro.databinding.FragmentDetailBinding
import algorithm.master.currencyconverterpro.domain.model.history.RateModel
import algorithm.master.currencyconverterpro.presentation.adapter.ConversionRateAdapter
import algorithm.master.currencyconverterpro.presentation.util.getDate
import algorithm.master.currencyconverterpro.presentation.util.getPastDate
import algorithm.master.currencyconverterpro.presentation.util.parseDate
import algorithm.master.currencyconverterpro.presentation.viewmodel.HistoryConversionViewModel
import algorithm.master.currencyconverterpro.presentation.viewmodel.RealTimeConversionViewModel
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val navArgs: DetailFragmentArgs by navArgs()
    private val historyRatesAdapter = ConversionRateAdapter()
    private val realTimeRatesAdapter = ConversionRateAdapter()

    private val historyConversionViewModel: HistoryConversionViewModel by viewModels()
    private val realTimeConversionViewModel: RealTimeConversionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMenu(activity as AppCompatActivity)
        initObservers()
        initRecyclerViews()
    }

    private fun initRecyclerViews() {
        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = historyRatesAdapter
            addItemDecoration(
                DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
            )
        }

        binding.rvLatest.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = realTimeRatesAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        }
    }

    private fun initObservers() {
        val startDate = getPastDate(3)
        val endDate = getDate(Date().time)

        //Fetch historical data for last 3 days viewed in a list
        historyConversionViewModel.getHistoryRateConversions(
            startDate, endDate, navArgs.currencyFrom, navArgs.currencyTo
        )

        //Fetch conversion from the 10 most popular currencies with the same base
        realTimeConversionViewModel.getRealTimeConversions(navArgs.symbols, navArgs.currencyFrom)

        historyConversionViewModel.historyConversions.observe(viewLifecycleOwner) {
            historyRatesAdapter.submitList(it)
            displayInGraph(it)
        }

        realTimeConversionViewModel.realTimeConversions.observe(viewLifecycleOwner) {
            realTimeRatesAdapter.submitList(it)
        }

        historyConversionViewModel.fetching.observe(viewLifecycleOwner) {
            binding.pgHistory.visibility = if (it) View.VISIBLE else View.GONE
        }

        realTimeConversionViewModel.fetching.observe(viewLifecycleOwner) {
            binding.pgOthers.visibility = if (it) View.VISIBLE else View.GONE
        }

        historyConversionViewModel.error.observe(viewLifecycleOwner) {
            showErrorMessage(it?.message)
        }

        realTimeConversionViewModel.error.observe(viewLifecycleOwner) {
            showErrorMessage(it?.message)
        }
    }

    private fun displayInGraph(data: List<RateModel>?) {
        lifecycleScope.launch(Dispatchers.IO) {
            val series: LineGraphSeries<DataPoint> = LineGraphSeries()
            try {
                data?.map { parseDate(it.date) to it.amount.toDouble() }?.sortedBy { it.first }
                    ?.forEachIndexed { index, pair ->
                        series.appendData(
                            DataPoint(
                                index.toDouble(),
                                pair.second
                            ), false, data.size - 1
                        )
                    }
            } catch (_: Exception) {
            }
            withContext(Dispatchers.Main) {
                binding.gvCurrencyGraph.addSeries(series)
                series.isDrawDataPoints = true
                series.dataPointsRadius = 8F
                binding.gvCurrencyGraph.gridLabelRenderer.apply {
                    horizontalAxisTitle = "Days"
                    verticalAxisTitle = "Price (${navArgs.currencyTo})"
                }
                binding.gvCurrencyGraph.viewport.isXAxisBoundsManual = true
                binding.gvCurrencyGraph.viewport.isYAxisBoundsManual = true
            }
        }
    }

    private fun showErrorMessage(msg: String?) {
        msg?.let { Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show() }
    }

    private fun setMenu(activity: AppCompatActivity) {
        activity.apply {
            setSupportActionBar(binding.toolbarCurrency)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            (this as MenuHost).addMenuProvider(object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {}

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    if (menuItem.itemId == android.R.id.home) findNavController().popBackStack()
                    return true
                }
            }, viewLifecycleOwner, Lifecycle.State.RESUMED)
            binding.toolbarCurrency.title =
                String.format("%s to %s", navArgs.currencyFrom, navArgs.currencyTo)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
