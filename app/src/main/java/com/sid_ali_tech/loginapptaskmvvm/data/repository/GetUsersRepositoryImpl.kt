package com.sid_ali_tech.loginapptaskmvvm.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.gson.Gson
import com.sid_ali_tech.loginapptaskmvvm.data.model.User
import com.sid_ali_tech.loginapptaskmvvm.domain.repository.GetUsersRepository
import com.sid_ali_tech.loginapptaskmvvm.utils.Constants.TAG
import com.sid_ali_tech.loginapptaskmvvm.utils.Response
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject


class GetUsersRepositoryImpl @Inject constructor
    (private val fireStore: FirebaseFirestore,
            private val firebaseAuth: FirebaseAuth) :
    GetUsersRepository {

    override fun getUsersList(): Flow<Response<List<User>>> = channelFlow {
        val userList: ArrayList<User> = ArrayList()
        try {
            send(Response.Loading)

            fireStore.collection("users").get().addOnSuccessListener { local ->
                for (usr in local) {
                    val docId=usr.id
                    val email = usr.getString("email")
                    val phone = usr.getString("phone")
                    val address = usr.getString("address")
                    val first_name = usr.getString("first_name")
                    val last_name = usr.getString("last_name")
                    val picture = usr.getString("picture")

//                    val obj = usr.toObject(User::class.java)
                    userList.add(User(email.toString(),first_name.toString(),last_name.toString(),picture.toString(),phone.toString(),address.toString(),documentId = docId))

                    Log.e(TAG, "getCategories: ${userList[0].email}")

                    Log.e(TAG, "ID: ${userList[0].first_name}")
                    Log.e(TAG, "getCategories: ${userList[0].address}")
                }
                launch {
                    send(Response.Success(userList))
                }
            }.addOnFailureListener { e ->
                launch {
                    send(Response.Error(e.localizedMessage ?: " unexpected error cat "))
                }
            }

        } catch (e: Exception) {
            send(Response.Error(e.localizedMessage ?: " unexpected error cat "))
        }

        awaitClose()
    }

}