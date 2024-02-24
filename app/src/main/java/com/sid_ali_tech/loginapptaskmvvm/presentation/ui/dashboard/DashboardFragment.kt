package com.sid_ali_tech.loginapptaskmvvm.presentation.ui.dashboard

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.sid_ali_tech.loginapptaskmvvm.R
import com.sid_ali_tech.loginapptaskmvvm.common.CustomProgressBar
import com.sid_ali_tech.loginapptaskmvvm.common.userClick
import com.sid_ali_tech.loginapptaskmvvm.data.model.User
import com.sid_ali_tech.loginapptaskmvvm.databinding.FragmentDashboardBinding
import com.sid_ali_tech.loginapptaskmvvm.databinding.FragmentLoginBinding
import com.sid_ali_tech.loginapptaskmvvm.presentation.ui.auth.login.LoginViewModel
import com.sid_ali_tech.loginapptaskmvvm.utils.Constants.TAG
import com.sid_ali_tech.loginapptaskmvvm.utils.Response
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : Fragment() {
    private val binding by lazy {
        FragmentDashboardBinding.inflate(layoutInflater)
    }
    private val navController by lazy {
        findNavController()
    }

    @Inject
    lateinit var customProgressBar: CustomProgressBar
    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    private val viewModel: DashboardViewModel by viewModels()
    private var arrayList: ArrayList<User> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUsersList()
        initObservers()
    }


    fun initObservers(){
        lifecycleScope.launch {
            viewModel.usersList.collect { result ->
                when (result) {
                    is Response.Loading -> {
                        customProgressBar.show(requireContext(), "Loading")
                    }
                    is Response.Success -> {
                        if (result.data.isNotEmpty()) {
                            Log.e(TAG, result.data[0].email)
                            arrayList = result.data as ArrayList
                            binding.recyclerUsers.layoutManager =LinearLayoutManager(requireContext())
                            val adapter = UsersAdapter(arrayList)
                            binding.recyclerUsers.adapter = adapter
                        } else {
                            Log.e(TAG, "it is empty")
                        }
                        customProgressBar.getDialog()?.dismiss()
                    }
                    is Response.Error -> {
                        Log.e(TAG, "error ${result.message}")
                        customProgressBar.getDialog()?.dismiss()
                    }
                }
            }
        }

    }


}