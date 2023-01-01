package com.jetpackcompose.homwork4.model

import com.jetpackcompose.homwork4.R


data class ProfileDetails(
    var firstName: String = "Customer",
    var lastName: String = "Newone",
    var email: String = "customer@gmail.com",
    var telephone: String = "+380630000000",
    var gender: String = "Male",
    var avatarUrl: Int = R.drawable.ic_user,
    var customerNo: String = "27314974",
    var isAdmin: Boolean = true,
)
