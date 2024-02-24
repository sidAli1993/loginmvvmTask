package com.sid_ali_tech.loginapptaskmvvm.presentation.ui.auth.editscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.sid_ali_tech.loginapptaskmvvm.R
import com.sid_ali_tech.loginapptaskmvvm.common.CustomProgressBar
import com.sid_ali_tech.loginapptaskmvvm.data.model.User
import com.sid_ali_tech.loginapptaskmvvm.databinding.FragmentEditUserBinding
import com.sid_ali_tech.loginapptaskmvvm.databinding.FragmentForgetPasswordBinding
import com.sid_ali_tech.loginapptaskmvvm.utils.setDisabled
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EditUser : Fragment() {
    private val binding by lazy {
        FragmentEditUserBinding.inflate(layoutInflater)
    }
    private val navController by lazy {
        findNavController()
    }
    lateinit var user: User
    @Inject
    lateinit var customProgressBar: CustomProgressBar

    @Inject
    lateinit var firebaseFirestore: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPrevData()
    }

    fun getPrevData() {
        arguments?.let {
            user = Gson().fromJson(it.getString("usrObj"), User::class.java)
            binding.edtEmail.setDisabled()
            binding.edtEmail.setText(user.email)
            binding.edtAddress.setText(user.address)
            binding.edtFName.setText(user.first_name)
            binding.edtLastName.setText(user.last_name)
            binding.edtPhone.setText(user.phone)
            setEvents()
        }

    }
    fun setEvents(){
        binding.btnUpdate.setOnClickListener {
            val email=binding.edtEmail.text.toString()
            val address=binding.edtAddress.text.toString()
            val fname=binding.edtFName.text.toString()
            val lname=binding.edtLastName.text.toString()
            val phone=binding.edtPhone.text.toString()
            val updatesMap = hashMapOf<String, Any>(
                "address" to address,
                "first_name" to fname,
                "last_name" to lname,
                "phone" to phone
            )
            if (::user.isInitialized){
                val docId=user.documentId
                val usersCollection = firebaseFirestore.collection("users")
                usersCollection.document(docId!!)
                    .update(updatesMap)
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(), "Data is updated successfully", Toast.LENGTH_SHORT).show()
                    navController.navigateUp()
                    }
                    .addOnFailureListener { exception ->
                        println("Error while updating data: $exception")
                        Toast.makeText(requireContext(), "error in update", Toast.LENGTH_SHORT).show()
                    }

            }
        }
        binding.btnDelete.setOnClickListener {
            if (::user.isInitialized){
                val docId=user.documentId
                val usersCollection = firebaseFirestore.collection("users")
                usersCollection.document(docId!!)
                    .delete()
                    .addOnSuccessListener {
                        navController.navigateUp()
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(requireContext(), "Error in delete", Toast.LENGTH_SHORT).show()
                    }
            }
           
        }
    }
}