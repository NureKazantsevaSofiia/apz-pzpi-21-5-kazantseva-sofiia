package com.logihub.util

import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class Validator {

    fun formatDate(date: String): String? {
        try {
            date.trim()
            val inputFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy")

            val localDate = LocalDate.parse(date, inputFormat)

            val currentTime = ZonedDateTime.now(ZoneId.of("UTC"))

            val combinedDateTime =
                ZonedDateTime.of(localDate, currentTime.toLocalTime(), ZoneId.of("UTC"))

            val outputFormat = DateTimeFormatter.ISO_OFFSET_DATE_TIME

            return combinedDateTime.format(outputFormat)
        } catch (e: Exception) {
            return null
        }
    }

    fun formatDateTime(date: String): String? {
        try {
            date.trim()
            val inputFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy")

            val localDate = LocalDate.parse(date, inputFormat)

            val currentTime = ZonedDateTime.now(ZoneId.of("UTC"))

            val combinedDateTime =
                ZonedDateTime.of(localDate, currentTime.toLocalTime(), ZoneId.of("UTC"))

            val outputFormat = DateTimeFormatter.ISO_OFFSET_DATE_TIME

            return combinedDateTime.format(outputFormat)
        } catch (e: Exception) {
            return null
        }
    }

    fun validateEmail(email: String): String {
        email.trim()
        if (email == "") {
            return "Required"
        }
        if (email.length < 5) {
            return "Invalid email"
        }
        val pattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        val isMatch = Regex(pattern).matches(email)
        return if (isMatch) {
            ""
        } else {
            "Invalid email"
        }
    }

    fun validateLogin(login: String): String {
        login.trim()
        if (login == "") {
            return "Required"
        }
        val pattern = "^[a-zA-Z0-9_-]{4,32}\$"
        val isMatch = Regex(pattern).matches(login)
        return if (isMatch) {
            ""
        } else {
            "Invalid username"
        }
    }

    fun validatePassword(password: String): String {
        password.trim()
        if (password == "") {
            return "Required"
        }
        val pattern = "^[a-zA-Z0-9!@#\$%^&*-_]{4,32}\$"
        val isMatch = Regex(pattern).matches(password)
        return if (isMatch) {
            ""
        } else {
            "Invalid password"
        }
    }

    fun validateFirstName(firstName: String): String {
        firstName.trim()
        return if (firstName == "") {
            "Required"
        } else if (firstName.length < 4 || firstName.length > 50) {
            "Invalid first name"
        } else {
            ""
        }
    }

    fun validateLastName(lastName: String): String {
        lastName.trim()
        return if (lastName == "") {
            "Required"
        } else if (lastName.length < 4 || lastName.length > 50) {
            "Invalid last name"
        } else {
            ""
        }
    }

    fun validateDescription(description: String): String {
        description.trim()
        return if (description == "") {
            "Required"
        } else if (description.length < 15 || description.length > 500) {
            "Invalid description"
        } else {
            ""
        }
    }
}