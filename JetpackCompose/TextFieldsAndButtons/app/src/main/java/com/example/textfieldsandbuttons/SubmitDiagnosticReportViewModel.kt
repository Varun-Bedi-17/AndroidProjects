package com.example.textfieldsandbuttons

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.regex.Pattern

class SubmitDiagnosticReportViewModel : ViewModel() {
    val error = MutableLiveData<String>()

    var inputName by mutableStateOf("")
    var inputEmail by mutableStateOf("")
    var inputIssueDescription by mutableStateOf("")


    /** This function is used to check whether all the entries are valid or not */
    fun isValidateLayout(){
        if(inputName.trim().isEmpty()){
            error.value = "emptyName"
        }
        else if(inputEmail.trim().isEmpty()){
            error.value = "emptyEmail"
        }
        else if(!isValidateEmail(inputEmail)){
            error.value = "notValidEmail"
        }
        else if(inputIssueDescription.trim().isEmpty()){
            error.value = "emptyIssueDescription"
        }
        else{
            error.value = "returnToSettings"
        }
    }


    /** To validate whether the entered text is type of email or not
     * @param email It is the email which is entered by the user
     */
    private fun isValidateEmail(email: String): Boolean {
        val emailPattern =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        val pattern = Pattern.compile(emailPattern)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }
}