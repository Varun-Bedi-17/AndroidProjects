package com.example.goodspacesample.util

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.KeyEvent
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.ViewCompat
import com.example.goodspacesample.R

class CustomEditTextForOtpVerify @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {

    private val editTextList = mutableListOf<EditText>()

    init {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER

        // Customize the space between EditText fields
        val spacing = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 8f, resources.displayMetrics
        ).toInt()

        // Create four EditText fields
        repeat(4) {
            val editText = createEditText()
            editTextList.add(editText)

            val params = LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
            if (it != 0) {
                params.marginStart = spacing
                editTextList[it - 1].nextFocusForwardId = editText.id
            }
            editText.layoutParams = params

            addView(editText)
        }
        editTextList[0].requestFocus()
    }

    private fun createEditText(): EditText {
        return EditText(context).apply {
            id = ViewCompat.generateViewId()
            layoutParams = LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            background = AppCompatResources.getDrawable(context, R.drawable.edit_text_bg)
            gravity = Gravity.CENTER
            imeOptions = EditorInfo.IME_ACTION_NEXT
            inputType = EditorInfo.TYPE_CLASS_NUMBER

            // Set the length filter to 1 to limit the input to a single character
            filters = arrayOf(InputFilter.LengthFilter(1))

            isCursorVisible = false
            setTextColor(getColor(context, R.color.white))

            // Set text change listener to move focus to the next EditText
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s?.length == 1) {
                        setBackgroundResource(R.drawable.edit_text_filled_bg)
                        focusNextEditText(this@apply)
                    }else if(s?.length == 0)
                        setBackgroundResource(R.drawable.edit_text_bg)
                }

                override fun afterTextChanged(s: Editable?) {}
            })

            // Handle backspace to move focus to the previous EditText
            setOnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                    if (text.isNullOrEmpty()) {
                        focusPreviousEditText(this@apply)
                        return@setOnKeyListener true
                    }
                }
                false
            }
        }
    }

    private fun focusNextEditText(editText: EditText) {
        val index = editTextList.indexOf(editText)
        if (index < editTextList.size - 1) {
            editTextList[index + 1].requestFocus()
        }
    }

    private fun focusPreviousEditText(editText: EditText) {
        val index = editTextList.indexOf(editText)
        if (index > 0) {
            editTextList[index-1].text.clear()
            editTextList[index - 1].requestFocus()
        }
    }

    fun getText(): String {
        val stringBuilder = StringBuilder()
        editTextList.forEach {
            stringBuilder.append(it.text.toString())
        }
        return stringBuilder.toString()
    }

}

