package com.sid_ali_tech.loginapptaskmvvm.presentation.ui.auth.forgetpassword

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.sid_ali_tech.loginapptaskmvvm.R
import com.sid_ali_tech.loginapptaskmvvm.common.CustomProgressBar
import com.sid_ali_tech.loginapptaskmvvm.databinding.FragmentForgetPasswordBinding
import com.sid_ali_tech.loginapptaskmvvm.databinding.FragmentSignUpBinding
import com.sid_ali_tech.loginapptaskmvvm.presentation.ui.auth.login.LoginViewModel
import com.sid_ali_tech.loginapptaskmvvm.utils.Constants
import com.sid_ali_tech.loginapptaskmvvm.utils.Response
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ForgetPasswordFragment : Fragment() {
    private val binding by lazy {
        FragmentForgetPasswordBinding.inflate(layoutInflater)
    }
    private val navController by lazy {
        findNavController()
    }
    @Inject
    lateinit var customProgressBar: CustomProgressBar
    private val viewModel: ForgetPasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setEvents()
        initObservers()
    }
    fun setEvents() {
        binding.tvSignUp.setOnClickListener {
            navController.navigate(R.id.navRegister)
        }

        binding.btnSubmit.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val checkValid = Constants.isEmailValid(email)

            if (checkValid && email.isNotEmpty()) {
                viewModel.forgetPassword(email)
            } else {
                Snackbar.make(it, "Email is empty", Snackbar.LENGTH_LONG)
                    .setAction("Action") {
                    }.show()
            }
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.forgetPwdState.collect { uiState ->
                when (uiState) {
                    is Response.Success -> {
                        customProgressBar.getDialog()?.dismiss()
                        if (uiState.data) {
                            Toast.makeText(requireContext(), "Password reset email sent", Toast.LENGTH_SHORT).show()
                            Log.e(Constants.TAG, "Password Email sent ")
                            navController.navigate(R.id.navLogin)
                        }
                    }

                    is Response.Error -> {
                        customProgressBar.getDialog()?.dismiss()
                        Toast.makeText(requireContext(), "${uiState.message}", Toast.LENGTH_SHORT).show()
                        Log.e(Constants.TAG, "error: ${uiState.message}")
                    }

                    is Response.Loading -> {
                        customProgressBar.show(requireContext(),"Loading")
                        Log.e(Constants.TAG, "progressbar is loading: ")
                    }
                }
            }
        }
    }
}