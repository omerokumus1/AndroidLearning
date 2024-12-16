package com.example.androidlearning.data

class FormRepository {
    private val formDataProvider = FormDataProvider()

    fun getFormFields(): List<FormField> {
        return formDataProvider.getFormFields()
    }
}


