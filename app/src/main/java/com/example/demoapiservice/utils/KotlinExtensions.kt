package com.example.demoapiservice.utils

import android.view.View

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}