package com.example.lesson4

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class MainActivity : AppCompatActivity() {
    lateinit var textInputEditTextRu: TextInputEditText
    lateinit var textInputEditTextEn: TextInputEditText
    lateinit var textInputLayoutEn: TextInputLayout
    lateinit var textInputLayoutRu: TextInputLayout
    lateinit var buttonRuEn: Button
    lateinit var buttonEnRu: Button

    companion object {
        val cyrillicArr = charArrayOf(' ', 'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я', 'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я')
        val latinArr = charArrayOf(' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z')
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textInputLayoutEn = findViewById(R.id.textInputLayoutEn);
        textInputLayoutRu = findViewById(R.id.textInputLayoutRu);
        textInputEditTextEn = findViewById(R.id.editTextEn);
        textInputEditTextRu = findViewById(R.id.editTextRu);
        buttonRuEn = findViewById(R.id.buttonRuEn);
        buttonEnRu = findViewById(R.id.buttonEnRu);

        buttonRuEn.setOnClickListener {     // отлично первый ClickListener на Kotlin
            if (checkEditableIsInArray(cyrillicArr, textInputEditTextRu.text!!))
                textInputEditTextEn.setText(transliterateRuEn(textInputEditTextRu.text.toString()))
        }

        buttonEnRu.setOnClickListener {     // тут надо полный синтаксис делать, тк getText() в короткой форме .text возвращает какой-то editable. В итоге, чтобы было короче исходного варианта надо самим в своих методах делать все не строкой, а editable.
            if (checkEditableIsInArray(latinArr, textInputEditTextEn.text!!))
                textInputEditTextRu.setText(transliterateEnRu(textInputEditTextEn.text.toString()))
        }
    }

    override fun onStart() {
        super.onStart()
        textInputEditTextEn.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                if (checkEditableIsInArray(latinArr, s))      // прекрасно, работает
                    textInputLayoutEn.error = null
                else
                    textInputLayoutEn.error = "Field supports only Latin"
            }
        })

        textInputEditTextRu.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                if (checkEditableIsInArray(cyrillicArr, s))
                    textInputLayoutRu.error = null
                else
                    textInputLayoutRu.error = "Field supports only Cyrillic"
            }
        })

    }

    fun transliterateRuEn(message: String): String? {
        val abcCyr = charArrayOf(' ', 'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я', 'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я')
        val abcLat =     arrayOf(" ", "a", "b", "v", "g", "d", "e", "e", "zh", "z", "i", "i", "k", "l", "m", "n", "o", "p", "r", "s", "t", "u", "f", "kh", "ts", "ch", "sh", "shch", "ie", "y", "", "e", "iu", "ia", "A", "B", "V", "G", "D", "E", "E", "Zh", "Z", "I", "I", "K", "L", "M", "N", "O", "P", "R", "S", "T", "U", "F", "Kh", "Ts", "Ch", "Sh", "Shch", "Ie", "Y", "", "E", "Iu", "Ia")

        val builder = StringBuilder()
        for (element in message) {
            for (x in abcCyr.indices) {
                if (element == abcCyr[x]) {
                    builder.append(abcLat[x])
                }
            }
        }
        return builder.toString()
    }

    fun transliterateEnRu(message: String): String? {
        val abcLat = charArrayOf(' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z')
        val abcCyr =     arrayOf(" ", "а", "б", "с", "д", "е", "ф", "г", "х", "и", "ж", "к", "л", "м", "н", "о", "п", "к", "р", "с", "т", "у", "в", "в", "кс", "й", "з", "А", "Б", "С", "Д", "Е", "Ф", "Г", "Х", "И", "Ж", "К", "Л", "М", "Н", "О", "П", "К", "Р", "С", "Т", "У", "В", "В", "Кс", "Й", "З")

        val builder = StringBuilder()
        for (element in message) {
            for (x in abcLat.indices) {
                if (element == abcLat[x]) {
                    builder.append(abcCyr[x])
                }
            }
        }
        return builder.toString()
    }

    fun checkEditableIsInArray(alphabet: CharArray, s: Editable): Boolean {
        for (symbol in s)
            if (symbol !in alphabet)
                return false
        return true
    }

    /*
    val listener = object : TextWatcher {        // anonymous class implementing TextWatcher
                                                 // не можем в этом случае применить, тк разные внешние переменные юзаем внутри
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable) {
            if (s.isEmpty() || checkEditableIsInArray(latinArr, s)) {
                buttonEnRu.isActivated = true;
                buttonEnRu.setBackgroundColor(resources.getColor(R.color.purple_500))
                textInputLayoutEn.error = null
            } else {
                buttonEnRu.isActivated = false;
                buttonEnRu.setBackgroundColor(resources.getColor(R.color.red_alert))
                textInputLayoutEn.error = "Field supports only Latin"
            }
        }
    }
*/
}