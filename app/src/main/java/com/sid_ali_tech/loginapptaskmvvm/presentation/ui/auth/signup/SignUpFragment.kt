package com.sid_ali_tech.loginapptaskmvvm.presentation.ui.auth.signup

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.sid_ali_tech.loginapptaskmvvm.R
import com.sid_ali_tech.loginapptaskmvvm.common.CustomProgressBar
import com.sid_ali_tech.loginapptaskmvvm.data.model.User
import com.sid_ali_tech.loginapptaskmvvm.databinding.FragmentLoginBinding
import com.sid_ali_tech.loginapptaskmvvm.databinding.FragmentSignUpBinding
import com.sid_ali_tech.loginapptaskmvvm.utils.Constants
import com.sid_ali_tech.loginapptaskmvvm.utils.Constants.TAG
import com.sid_ali_tech.loginapptaskmvvm.utils.Response
import com.sid_ali_tech.loginapptaskmvvm.utils.setDisabled
import com.sid_ali_tech.loginapptaskmvvm.utils.setEnabled
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private val binding by lazy {
        FragmentSignUpBinding.inflate(layoutInflater)
    }
    private val navController by lazy {
        findNavController()
    }
    @Inject
    lateinit var customProgressBar: CustomProgressBar

    private val viewModel: SignupViewModel by viewModels()
    private var isEmailOK: Boolean? = null
    private var isPwdOK: Boolean? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        setEvents()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.signUpState.collect { uiState ->
                when (uiState) {
                    is Response.Loading -> {
                        customProgressBar.show(requireContext(),"Loading")
                    }

                    is Response.Success -> {
                        customProgressBar.getDialog()?.dismiss()
                        Log.e(
                            TAG, "user is signup ${uiState.data}"
                        )
                        if (uiState.data) {
                            navController.navigate(R.id.navLogin)
                        }

                    }

                    is Response.Error -> {
                        customProgressBar.getDialog()?.dismiss()
                        Toast.makeText(requireContext(), "${uiState.message}", Toast.LENGTH_SHORT).show()
                        Log.e(TAG, "error: ${uiState.message}")
                    }
                }

            }
        }
    }

    fun setEvents() {
        binding.btnSignUp.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            val fName = binding.edtFName.text.toString()
            val lName = binding.edtLastName.text.toString()
            val phone = binding.edtPhone.text.toString()
            val address = binding.edtAddress.text.toString()
            val checkValid = Constants.isEmailValid(email)
            viewModel.password.value = password
            
            if (checkValid && email.isNotEmpty() && password.isNotEmpty() && fName.isNotEmpty() && lName.isNotEmpty()) {
                viewModel.signUp(
                    User(
                        email = binding.edtEmail.text.toString(),
                        first_name = binding.edtFName.text.toString(),
                        last_name = binding.edtLastName.text.toString(),
                        "",
                        phone = phone,
                        address=address
                    )
                )
            } else {
                Snackbar.make(it, "Email Or Password is empty", Snackbar.LENGTH_LONG)
                    .setAction("Action") {
                    }.show()
            }


        }
        binding.tvLogin.setOnClickListener {
            navController.navigate(R.id.navLogin)
        }
    }

}