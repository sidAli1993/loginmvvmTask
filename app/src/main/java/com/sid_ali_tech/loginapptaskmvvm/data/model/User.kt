package com.sid_ali_tech.loginapptaskmvvm.data.model

import androidx.room.ColumnInfo

data class User(
    val email: String = "",
    val first_name: String = "",
    val last_name: String = "",
    val picture: String = "",
    val phone:String="",
    val address:String="",
    val documentId:String?=""
    )
