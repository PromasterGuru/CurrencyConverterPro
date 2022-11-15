package algorithm.master.currencyconverterpro.presentation.ui

import algorithm.master.currencyconverterpro.databinding.FragmentConverterBinding
import algorithm.master.currencyconverterpro.presentation.viewmodel.AvailableCurrencyViewModel
import algorithm.master.currencyconverterpro.presentation.viewmodel.CurrencyConverterViewModel
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ConverterFragment : Fragment() {
    private var _binding: FragmentConverterBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var availableCurrencyViewModel: AvailableCurrencyViewModel

    @Inject
    lateinit var converterViewModel: CurrencyConverterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentConverterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Fetch available currencies
        availableCurrencyViewModel.getAvailableCurrencies()

        //List all available currencies in FROM/TO drop down
        availableCurrencyViewModel.availableCurrencies.observe(viewLifecycleOwner) {
            val spinnerAdapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, it)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spFrom.adapter = spinnerAdapter
            binding.spTo.adapter = spinnerAdapter
        }

        binding.btnDetails.setOnClickListener {

            val fromAmount = binding.etFrom.text.toString()
            val fromCurrency = binding.spFrom.selectedItem?.toString() ?: ""
            val toCurrency = binding.spTo.selectedItem?.toString() ?: ""

            if (isValidEntry(fromAmount, fromCurrency, toCurrency)) {
                val directions = ConverterFragmentDirections.openCurrencyDetails(
                    fromCurrency, toCurrency, fromAmount.toInt()
                )
                findNavController().navigate(directions)
            }
        }
    }

    private fun isValidEntry(txtFrom: String, spFrom: String, spTo: String): Boolean {
        if (TextUtils.isEmpty(txtFrom)) {
            showErrorMessage("Enter from amount")
            return false
        }
        if (TextUtils.isEmpty(spFrom)) {
            showErrorMessage("Select currency from")
            return false
        }
        if (TextUtils.isEmpty(spTo)) {
            showErrorMessage("Select currency to")
            return false
        }
        return true
    }

    private fun showErrorMessage(msg: String) {
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
