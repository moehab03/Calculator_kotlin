package com.codexcue.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.codexcue.myapplication.data.model.Operation
import com.codexcue.myapplication.databinding.ActivityCalculatorBinding

class CalculatorActivity : AppCompatActivity() {
    private lateinit var vm: CalculatorViewModel
    private lateinit var binding: ActivityCalculatorBinding

    private var rhs: String = ""
    private var lhs: String = ""
    private var operator: String = ""
    private var isThereAPoint: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initVM()
        //enableEdgeToEdge()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_calculator)

    }

    private fun initVM() {
        vm = ViewModelProvider(this)[CalculatorViewModel::class.java]
        binding.vm = vm
    }

    fun onDigitClick(view: View) {
        val button = view as Button

        if (rhs.isNotEmpty()) clear()
        binding.resultTV.append(button.tag.toString())

    }

    fun onPointClick(view: View) {
        val button = view as Button
        val number: String = binding.resultTV.getText().toString()

        for (element in number) {
            isThereAPoint = element == '.'
            if (isThereAPoint) {
                break
            }
        }
        if (!isThereAPoint) {
            if (rhs.isNotEmpty()) clear()

            isThereAPoint = true
            binding.resultTV.append(button.tag.toString())

        }
    }

    fun onOperatorClick(view: View) {
        val button = view as Button

        if (binding.resultTV.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter Number first", Toast.LENGTH_SHORT).show()
        } else if (lhs.isEmpty()) {
            lhs = binding.resultTV.getText().toString()
            binding.resultTV.text = getString(R.string.emptyString)
            operator = button.tag.toString()
        } else if (rhs.isEmpty()) {
            rhs = binding.resultTV.getText().toString()
            operator = button.tag.toString()
            binding.resultTV.text = vm.calc(Operation(lhs, rhs, operator))
        } else {
            rhs = ""
            lhs = binding.resultTV.getText().toString()
            operator = button.tag.toString()
            binding.resultTV.text = getString(R.string.emptyString)
        }
    }

    fun onClearClick(view: View?) {
        clear()
    }

    fun onDelClick(view: View) {
        val button = view as Button
        if (binding.resultTV.getText().isNotEmpty())
            binding.resultTV.text =
                binding.resultTV.getText().subSequence(0, binding.resultTV.getText().length - 1)
        button.setOnLongClickListener {
            clear()
            false
        }
    }

    fun onEqualClick(view: View?) {
        if (operator.isNotEmpty() && binding.resultTV.getText().toString().isNotEmpty()) {
            rhs = binding.resultTV.getText().toString()
            val res = vm.calc(Operation(lhs, rhs, operator))
            binding.resultTV.text = res
            lhs = res
            operator = ""
        }
    }

    private fun clear() {
        binding.resultTV.text = getString(R.string.emptyString)
        lhs = ""
        rhs = ""
        operator = ""
    }
}