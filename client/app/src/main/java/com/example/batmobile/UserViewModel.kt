package com.example.batmobile

import androidx.lifecycle.ViewModel
import com.example.batmobile.models.User

class UserViewModel: ViewModel() {
    var user: User? = null
    var selectedOption: String? = null
}