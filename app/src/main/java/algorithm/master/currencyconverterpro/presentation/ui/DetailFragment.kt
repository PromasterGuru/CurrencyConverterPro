package algorithm.master.currencyconverterpro.presentation.ui

import algorithm.master.currencyconverterpro.databinding.FragmentDetailBinding
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController


class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMenu(activity as AppCompatActivity)
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
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
