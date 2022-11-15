package algorithm.master.currencyconverterpro.presentation.ui

import algorithm.master.currencyconverterpro.databinding.FragmentConverterBinding
import algorithm.master.currencyconverterpro.presentation.viewmodel.AvailableCurrencyViewModel
import algorithm.master.currencyconverterpro.presentation.viewmodel.CurrencyConverterViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.widget.addTextChangedListener
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
        initObservers()
        binding.etFrom.addTextChangedListener {
            if (it.isNullOrEmpty()) binding.etFrom.setText("1")
            convertCurrency(true)
        }

        binding.etTo.addTextChangedListener {
            if (it.isNullOrEmpty()) binding.etTo.setText("1")
            convertCurrency(false)
        }

        binding.btnDetails.setOnClickListener {
            if (isCurrencyAvailable(
                    binding.spFrom.selectedItemPosition, binding.spTo.selectedItemPosition
                )
            ) {
                val fromAmount = binding.etFrom.text.toString()
                val fromCurrency = binding.spFrom.selectedItem.toString()
                val toCurrency = binding.spTo.selectedItem.toString()
                val directions = ConverterFragmentDirections.openCurrencyDetails(
                    fromCurrency, toCurrency, fromAmount.toInt()
                )
                findNavController().navigate(directions)
            }
        }
        binding.ivSwap.setOnClickListener {
            if (isCurrencyAvailable(
                    binding.spFrom.selectedItemPosition, binding.spTo.selectedItemPosition
                )
            ) {
                swapCurrencies(); convertCurrency(true)
            }
        }
    }

    private fun convertCurrency(leftToRight: Boolean) {
        if (isCurrencyAvailable(
                binding.spFrom.selectedItemPosition, binding.spTo.selectedItemPosition
            )
        ) {
            val fromAmount = binding.etTo.text.toString().toDouble()
            val fromCurrency = binding.spFrom.selectedItem.toString()
            val toCurrency = binding.spTo.selectedItem.toString()
            if (leftToRight) converterViewModel.convertCurrency(
                toCurrency, fromCurrency, fromAmount, ""
            )
            else converterViewModel.convertCurrency(fromCurrency, toCurrency, fromAmount, "")
        }
    }

    private fun swapCurrencies() {
        val temp = binding.spFrom.selectedItemPosition
        binding.spFrom.setSelection(binding.spTo.selectedItemPosition)
        binding.spTo.setSelection(temp)
    }

    private fun initObservers() {
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
    }

    private fun isCurrencyAvailable(fromPos: Int, toPos: Int): Boolean {
        if (fromPos == AppCompatSpinner.INVALID_POSITION || toPos == AppCompatSpinner.INVALID_POSITION) {
            showErrorMessage("No currency available")
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
