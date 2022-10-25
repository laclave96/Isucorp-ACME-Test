package com.isucorp.acmetest.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.isucorp.acmetest.R
import com.isucorp.acmetest.databinding.FragmentWorkTicketBinding
import com.isucorp.acmetest.presentation.viewmodel.MainActivityViewModel
import com.isucorp.acmetest.utils.DateFormat
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WorkTicketFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WorkTicketFragment : Fragment() {
    private lateinit var binding: FragmentWorkTicketBinding
    private val mainActivityViewModel: MainActivityViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_work_ticket,
            container, false
        )
        binding.lifecycleOwner = this
        binding.viewModel = mainActivityViewModel
        binding.getDirectionsBtn?.setOnClickListener {
           mainActivityViewModel.goToDestination(R.id.getDirectionsFragment)
        }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeToObservables()

    }

    private fun subscribeToObservables() {
        mainActivityViewModel.workTicket.observe(this) {
            binding.fullDateText.text = DateFormat.getString(
                it.scheduledTime, "EEEE, MMMM dd, yy hh:mm a "
            )
        }

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WorkTicketFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WorkTicketFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}