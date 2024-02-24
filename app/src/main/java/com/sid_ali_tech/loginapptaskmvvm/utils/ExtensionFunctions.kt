package com.sid_ali_tech.loginapptaskmvvm.utils

import android.view.View

fun View.makeGone(){
    visibility=View.GONE
}

fun View.makeVisible(){
    visibility=View.VISIBLE
}

fun View.setEnabled(){
    isEnabled=true
}

fun View.setDisabled(){
    isEnabled=false
}