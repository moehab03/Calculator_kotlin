package com.codexcue.myapplication

import androidx.lifecycle.ViewModel
import com.codexcue.myapplication.data.model.Operation

class CalculatorViewModel : ViewModel() {
    fun calc(operation: Operation): String {
        if (operation.operationSign != "") {
            val firstNumber: Double = operation.num1.toDouble()
            val secondNumber: Double = operation.num2.toDouble()

            return when (operation.operationSign) {
                "+" -> (firstNumber + secondNumber).toString()

                "-" -> (firstNumber - secondNumber).toString()

                "*" -> (firstNumber * secondNumber).toString()

                "/" -> (firstNumber / secondNumber).toString()

                else -> ""
            }
        }
        return ""
    }
}
