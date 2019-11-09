package com.example.motty.motty_play

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    private val itemInterface by lazy { createService() }

    fun createService(): ItemInterface {
        val baseApiUrl = "https://app.rakuten.co.jp/services/api/"

        val httpLogging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClientBuilder = OkHttpClient.Builder().addInterceptor(httpLogging)

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseApiUrl)
            .client(httpClientBuilder.build())
            .build()

        return retrofit.create(ItemInterface::class.java)
    }

    fun getRanking(v: View){
        itemInterface.items().enqueue(object : retrofit2.Callback<RakutenRankingResult> {
            override fun onFailure(call: retrofit2.Call<RakutenRankingResult>?, t: Throwable?) {
            }

            override fun onResponse(call: retrofit2.Call<RakutenRankingResult>?, response: retrofit2.Response<RakutenRankingResult>) {
                if (response.isSuccessful) {
                    response.body()?.let {

                        var items = mutableListOf<Item>()
                        var res = response.body()?.Items?.iterator()
                        val itemList = mutableListOf<Item>()

                        var title = response.body()!!.title
                        titleRanking.text = "$title"

                        if (res != null) {
                            for (items in res) {
                                itemList.add(items)
                            }
                        }

                        findViewById<RecyclerView>(R.id.listRanking).also { recyclerView: RecyclerView ->
                            val itemDecoration = DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
                            recyclerView.addItemDecoration(itemDecoration)
                            recyclerView.adapter = ItemViewAdapter(this@MainActivity, itemList)
                            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                        }
                    }
                }
            }
        })
    }
}
