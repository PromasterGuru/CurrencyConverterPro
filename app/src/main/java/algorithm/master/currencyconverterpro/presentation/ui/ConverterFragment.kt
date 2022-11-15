package algorithm.master.currencyconverterpro.presentation.ui

import algorithm.master.currencyconverterpro.databinding.FragmentConverterBinding
import algorithm.master.currencyconverterpro.presentation.util.getDate
import algorithm.master.currencyconverterpro.presentation.viewmodel.AvailableCurrencyViewModel
import algorithm.master.currencyconverterpro.presentation.viewmodel.CurrencyConverterViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ConverterFragment : Fragment() {
    private var _binding: FragmentConverterBinding? = null
    private val binding get() = _binding!!
    private val availableCurrencyViewModel: AvailableCurrencyViewModel by viewModels()
    private val converterViewModel: CurrencyConverterViewModel by viewModels()

    private var _animator: RotateAnimation? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentConverterBinding.inflate(layoutInflater, container, false)
        initAnimator()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        binding.etFrom.doAfterTextChanged {
            if (!it.isNullOrEmpty()) {
                val typedAmount = binding.etFrom.text.toString().toFloat()
                val serverAmount = converterViewModel.currencyModel.value?.amount ?: 0F
                val shouldFetch = binding.etFrom.hasFocus() && typedAmount != serverAmount
                if (converterViewModel.fetching.value == false && shouldFetch) {
                    convertCurrency(true)
                }
            }
        }

        binding.etTo.doAfterTextChanged {
            if (!it.isNullOrEmpty()) {
                val typedAmount = binding.etTo.text.toString().toFloat()
                val serverAmount = converterViewModel.currencyModel.value?.amount ?: 0F
                val shouldFetch = binding.etTo.hasFocus() && typedAmount != serverAmount
                if (converterViewModel.fetching.value == false && shouldFetch) {
                    convertCurrency(false)
                }
            }
        }

        binding.spFrom.onItemSelectedListener = onSpinnerItemSelected { convertCurrency(true) }

        binding.spTo.onItemSelectedListener = onSpinnerItemSelected { convertCurrency(false) }

        binding.btnDetails.setOnClickListener {
            if (isCurrencyAvailable(
                    binding.spFrom.selectedItemPosition, binding.spTo.selectedItemPosition
                )
            ) {
                val fromCurrency = binding.spFrom.selectedItem.toString()
                val toCurrency = binding.spTo.selectedItem.toString()
                val symbols = availableCurrencyViewModel.availableCurrencies.value?.take(10)
                    ?.joinToString(",") ?: ""
                val directions = ConverterFragmentDirections.openCurrencyDetails(
                    fromCurrency, toCurrency, symbols
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

    private fun initAnimator() {
        _animator = RotateAnimation(
            0F, 359F, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F
        )
        _animator?.duration = 1500
        _animator?.repeatCount = Animation.INFINITE
    }

    private fun onSpinnerItemSelected(convertCurrency: () -> Unit) =
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                binding.etFrom.clearFocus()
                binding.etTo.clearFocus()
                convertCurrency.invoke()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


    private fun convertCurrency(leftToRight: Boolean) {
        if (isCurrencyAvailable(
                binding.spFrom.selectedItemPosition, binding.spTo.selectedItemPosition
            )
        ) {
            val fromAmount = binding.etFrom.text?.toString()?.toFloat() ?: 1F
            val toAmount = binding.etTo.text?.toString()?.toFloat() ?: 1F
            val fromCurrency = binding.spFrom.selectedItem.toString()
            val toCurrency = binding.spTo.selectedItem.toString()
            val date = getDate(Date().time)
            if (converterViewModel.fetching.value == false) {
                if (leftToRight) converterViewModel.convertCurrency(
                    toCurrency, fromCurrency, fromAmount, date
                )
                else converterViewModel.convertCurrency(fromCurrency, toCurrency, toAmount, date)
            }
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

        //Display converted currencies
        converterViewModel.currencyModel.observe(viewLifecycleOwner) {
            if (it.from == binding.spFrom.selectedItem) {
                binding.etFrom.setText(it.amount.toString())
                binding.etTo.setText(it.result.toString())
            } else {
                binding.etFrom.setText(it.result.toString())
                binding.etTo.setText(it.amount.toString())
            }
        }

        //Show animation while fetching data
        converterViewModel.fetching.observe(viewLifecycleOwner) {
            if (it) binding.ivConverterLogo.startAnimation(_animator)
            else binding.ivConverterLogo.clearAnimation()
        }

        //Display any error thrown during currency conversion
        converterViewModel.error.observe(viewLifecycleOwner) {
            showErrorMessage(it?.message)
        }

        //Display any error thrown when fetching currencies
        availableCurrencyViewModel.error.observe(viewLifecycleOwner) {
            showErrorMessage(it?.message)
        }
    }

    private fun isCurrencyAvailable(fromPos: Int, toPos: Int): Boolean {
        if (fromPos == AppCompatSpinner.INVALID_POSITION || toPos == AppCompatSpinner.INVALID_POSITION) {
            showErrorMessage("No currency available")
            return false
        }
        return true
    }

    private fun showErrorMessage(msg: String?) {
        msg?.let { Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _animator = null
    }
}
