package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var textView: TextView
    lateinit var button: Button
    var collectDataInSB = StringBuilder()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        textView = findViewById(R.id.textView)
        button = findViewById(R.id.button)
        button.setOnClickListener{
            retroFitData()
        }

    }
    private fun retroFitData(){
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://zenquotes.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData()
        retrofitData.enqueue( object : Callback<List<QuoteData>?> {
            override fun onResponse(
                call: Call<List<QuoteData>?>,
                response: Response<List<QuoteData>?>
            ) {
                if (response.body()!==null){
                    var productList = response.body()!!
                    for (myData in productList){
                        collectDataInSB.replace(0,collectDataInSB.length,myData.q + "")
                    }
                    textView.text = collectDataInSB
                }
                else{
                    Toast.makeText(applicationContext,"Failure",Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<List<QuoteData>?>, t: Throwable) {
               Toast.makeText(applicationContext,"Failure${t}",Toast.LENGTH_SHORT).show()
            }
        })
    }
}