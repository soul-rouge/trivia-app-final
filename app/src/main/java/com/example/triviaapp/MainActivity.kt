package com.example.triviaapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.triviaapp.databinding.ActivityMainBinding
import com.google.gson.Gson
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getQuestion().start()
    }
    private fun getQuestion(): Thread
    {
        return Thread{
            val url = URL("https://opentdb.com/api.php?amount=40&type=multiple")
            val connection = url.openConnection() as HttpsURLConnection

            if(connection.responseCode==200)
            {
                val inputSystem = connection.inputStream
                val inputStreamReader = InputStreamReader(inputSystem,"UTF-8")
                val result = Gson().fromJson(inputStreamReader, RESULT::class.java)
                updateUI(result)
                inputStreamReader.close()
                inputSystem.close()
            }
            else
            {
                binding.question.text= "Failed Connection"
            }
        }
    }

    private fun updateUI(result: RESULT)
    {
        runOnUiThread {
            kotlin.run{
                var number:Int =0
                var score:Int = 0
                binding.score.text="Score: ${score}"
                Textsetter(number , result = result)
                binding.buttonA.setOnClickListener {
                    if(Textsetter(number, result) ==1){
                        correctORnot(1)
                        score=score+10
                        binding.score.text="Score: ${score}"
                    }else {
                        correctORnot(0)

                    }
                    Thread.sleep(2000)

                    number++
                    Textsetter(number, result = result)
                }
                binding.buttonB.setOnClickListener {
                    if(Textsetter(number, result) ==2){
                        correctORnot(1)
                        score=score+10
                        binding.score.text="Score: ${score}"
                    }else correctORnot(0)

                    Thread.sleep(2000)
                    number++
                    Textsetter(number, result = result)
                }
                binding.buttonC.setOnClickListener {
                    if(Textsetter(number, result) ==3){
                        correctORnot(1)
                        score=score+10
                        binding.score.text="Score: ${score}"
                    }else correctORnot(0)

                    Thread.sleep(2000)
                    number++
                    Textsetter(number, result = result)
                }
                binding.buttonD.setOnClickListener {
                    if(Textsetter(number, result) ==4){
                        correctORnot(1)
                        score=score+10
                        binding.score.text="Score: ${score}"
                    }else correctORnot(0)

                    Thread.sleep(2000)
                    number++
                    Textsetter(number, result = result)
                }
            }
        }
    }
    private fun Textsetter(i:Int , result: RESULT): Int {
        binding.question.text= result.results[i].question
        binding.questionNo.text= "${i+1}."
        val correctNo = (1..4).random()
        when(correctNo){
            1 -> binding.buttonA.text = result.results.elementAt(i).correct_answer
            2 -> binding.buttonB.text = result.results.elementAt(i).correct_answer
            3 -> binding.buttonC.text = result.results.elementAt(i).correct_answer
            4 -> binding.buttonD.text = result.results.elementAt(i).correct_answer
        }
        if(correctNo==1){
            binding.buttonB.text = result.results.elementAt(i).incorrect_answers[0]
            binding.buttonC.text = result.results.elementAt(i).incorrect_answers[1]
            binding.buttonD.text = result.results.elementAt(i).incorrect_answers[2]
        }else if(correctNo==2){
            binding.buttonA.text = result.results.elementAt(i).incorrect_answers[0]
            binding.buttonC.text = result.results.elementAt(i).incorrect_answers[1]
            binding.buttonD.text = result.results.elementAt(i).incorrect_answers[2]
        }else if(correctNo==3){
            binding.buttonA.text = result.results.elementAt(i).incorrect_answers[0]
            binding.buttonB.text = result.results.elementAt(i).incorrect_answers[1]
            binding.buttonD.text = result.results.elementAt(i).incorrect_answers[2]
        }else if(correctNo==4){
            binding.buttonA.text = result.results.elementAt(i).incorrect_answers[0]
            binding.buttonB.text = result.results.elementAt(i).incorrect_answers[1]
            binding.buttonC.text = result.results.elementAt(i).incorrect_answers[2]
        }
        return correctNo
    }
    private fun correctORnot(binary:Int){
        if(binary == 1){
            val toast = Toast.makeText(this, "Correct Answer!", Toast.LENGTH_SHORT)
            toast.show()
        }
        else{
            val toast = Toast.makeText(this, "Wrong Answer!", Toast.LENGTH_SHORT)
            toast.show()
        }
    }
}