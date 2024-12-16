package com.example.androidlearning.data

class FormDataProvider {
    fun getFormFields(): List<FormField> {
        return listOf(
            FormField("name", "Name", "", true),
            FormField("email", "Email", "", true),
            FormField("phone", "Phone Number", "", false),
            // Add the rest of the 15 fields
        )
    }
}

data class FormField(
    val key: String,
    val label: String,
    val value: String,
    val isRequired: Boolean
)