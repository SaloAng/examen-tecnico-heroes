package com.test.superhero

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
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
import java.util.*

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private var access:String = "EAAAAAYsX7TsBAPcO8J50qPBgKTgUiV9rJgNfYRAkNMqoxaH46RaSdmdewC6lIWZAAfMOjgCZBZBr2dhbjDcye6T1WI3I08AiBRMtSUZA6iX6XmVwZAqa9MabbYlDnMoAlXvndp09Phs5wtdXaDF3PiTZBu1yO4Xk0kO73EV5ZAv6VFfoks2zJD9J1cBirtG0mAJxokrkZCZC6ojxsZAdzzqhCn0gfLfIO2GZCAZD"

    private lateinit var binding:ActivityMainBinding
    private lateinit var adapter:HeroAdapter
    private val heroImages = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.svHeros.setOnQueryTextListener(this)
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

    private fun hideKeyBoard(){
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRoot.windowToken, 0)
    }

    private fun showError() {
        Toast.makeText(this,"Ocurrio un error", Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            serchById(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}