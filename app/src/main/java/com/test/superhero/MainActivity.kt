package com.test.superhero

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.superhero.databinding.ActivityMainBinding
import com.test.superhero.io.APIService
import com.test.superhero.io.HeroAdapter
import com.test.superhero.io.response.HeroResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private var access:String = "EAAAAAYsX7TsBAPcO8J50qPBgKTgUiV9rJgNfYRAkNMqoxaH46RaSdmdewC6lIWZAAfMOjgCZBZBr2dhbjDcye6T1WI3I08AiBRMtSUZA6iX6XmVwZAqa9MabbYlDnMoAlXvndp09Phs5wtdXaDF3PiTZBu1yO4Xk0kO73EV5ZAv6VFfoks2zJD9J1cBirtG0mAJxokrkZCZC6ojxsZAdzzqhCn0gfLfIO2GZCAZD"

    private lateinit var binding:ActivityMainBinding
    private lateinit var adapter:HeroAdapter
    private val heroImages = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = HeroAdapter(heroImages)
        binding.rvHero.layoutManager = LinearLayoutManager(this)
        binding.rvHero.adapter = adapter
    }

    private fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://superheroapi.com/api/$access/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun serchById(query:String){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getHerosById(query)
            val heroes:HeroResponse? =call.body()
            runOnUiThread(){
                if(call.isSuccessful){
                    val images = heroes?.heros ?: emptyList()
                    heroImages.clear()
                    heroImages.addAll(images)
                    adapter.notifyDataSetChanged()
                }else{
                    showError()
                }
            }
        }
    }

    private fun showError() {
        Toast.makeText(this,"Ocurrio un error", Toast.LENGTH_SHORT).show()
    }
}