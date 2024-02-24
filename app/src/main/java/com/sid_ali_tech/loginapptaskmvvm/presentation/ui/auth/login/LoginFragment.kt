package com.sid_ali_tech.loginapptaskmvvm.presentation.ui.auth.login

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.sid_ali_tech.loginapptaskmvvm.R
import com.sid_ali_tech.loginapptaskmvvm.common.CustomProgressBar
import com.sid_ali_tech.loginapptaskmvvm.databinding.FragmentLoginBinding
import com.sid_ali_tech.loginapptaskmvvm.databinding.FragmentSplashBinding
import com.sid_ali_tech.loginapptaskmvvm.utils.Constants
import com.sid_ali_tech.loginapptaskmvvm.utils.Constants.TAG
import com.sid_ali_tech.loginapptaskmvvm.utils.Response
import com.sid_ali_tech.loginapptaskmvvm.utils.makeGone
import com.sid_ali_tech.loginapptaskmvvm.utils.makeVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val binding by lazy {
        FragmentLoginBinding.inflate(layoutInflater)
    }
    private val navController by lazy {
        findNavController()
    }

    @Inject
    lateinit var customProgressBar: CustomProgressBar
    @Inject
    lateinit var firebaseAuth: FirebaseAuth
    private val viewModel: LoginViewModel by viewModels()

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
        binding.tvForgetPassword.setOnClickListener {
            navController.navigate(R.id.navForgetPwd)
        }
        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            val checkValid = Constants.isEmailValid(email)

            if (checkValid && email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.signIn(email,password)
            } else {
                Snackbar.make(it, "Email Or Password is empty", Snackbar.LENGTH_LONG)
                    .setAction("Action") {
                    }.show()
            }


        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.signInState.collect { uiState ->
                when (uiState) {
                    is Response.Success -> {
                        customProgressBar.getDialog()?.dismiss()
                        if (uiState.data) {
                            Log.e(TAG, "user is login ")
                            navController.navigate(R.id.navDashboard)
                        }
                    }

                    is Response.Error -> {
                        customProgressBar.getDialog()?.dismiss()
                        Toast.makeText(requireContext(), "${uiState.message}", Toast.LENGTH_SHORT).show()
                        Log.e(TAG, "error: ${uiState.message}")
                    }

                    is Response.Loading -> {
                        customProgressBar.show(requireContext(),"Loading")
                        Log.e(TAG, "progressbar is loading: ")
                    }
                }
            }
        }
    }

}