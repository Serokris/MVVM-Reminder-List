package com.example.remindersaboutmeetingswithclients.presentation.clientlist

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.remindersaboutmeetingswithclients.domain.models.Client
import com.example.remindersaboutmeetingswithclients.databinding.FragmentClientListBinding
import android.net.ConnectivityManager
import android.os.Build
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.example.remindersaboutmeetingswithclients.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ClientListFragment : Fragment(), ClientListAdapter.ClientClickListener {

    private val viewModel: ClientListViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentClientListBinding.inflate(inflater)

        navController = findNavController()
        val adapter = ClientListAdapter(this, requireContext())

        binding.clientRecyclerView.adapter = adapter

        if (isInternetConnected()) {
            viewModel.viewModelScope.launch {
                val responseBody = viewModel.getClientList(15).body()
                if (responseBody != null) {
                    adapter.submitList(responseBody.clients)
                } else {
                    Toast.makeText(requireContext(), R.string.failed_to_get_client_list,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            binding.noInternetImage.visibility = View.VISIBLE
        }

        return binding.root
    }

    override fun onClientClick(client: Client) {
        navController.navigate(
            ClientListFragmentDirections.actionClientListFragmentToCreateReminderFragment(client)
        )
    }

    private fun isInternetConnected(): Boolean {
        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val netWork = connectivityManager.activeNetwork
            netWork != null
        } else {
            val netWorkInfo = connectivityManager.activeNetworkInfo
            netWorkInfo != null && netWorkInfo.isConnected
        }
    }
}