package com.example.testeapicatsj.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testeapicatsj.R
import com.example.testeapicatsj.databinding.ActivityMainBinding
import com.example.testeapicatsj.model.data.CatData
import com.example.testeapicatsj.model.remote.Client
import com.example.testeapicatsj.ui.adapter.RecyclerAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val retrofit by lazy {
        Client.retrofit
    }
    private lateinit var catAdapter: RecyclerAdapter

    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        catAdapter = RecyclerAdapter()
        binding.rvList.adapter = catAdapter

        binding.rvList.layoutManager = GridLayoutManager(this, 3)
    }

    override fun onStart() {
        super.onStart()
        initCats()
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }

    private fun initCats() {
        job = CoroutineScope(Dispatchers.IO).launch {

            var response: Response<CatData>? = null

            try {
                response = retrofit.getImageCat("cats")
            } catch (e: Exception) {
                e.printStackTrace()
            }

            if (response != null && response.isSuccessful) {

                val resultado = response.body()
                if (resultado != null) {

                    val lista = resultado.data
                    if (!lista.isNullOrEmpty()) {

                        val listaUrlImagens = mutableListOf<String>()
                        lista.forEach { dados ->
                            val imagens = dados.images
                            if (!imagens.isNullOrEmpty()) {
                                val imagem = imagens[0]
                                val tipo = imagem.type
                                if (tipo == "image/jpeg") {
                                    listaUrlImagens.add(imagem.link)
                                }
                            } else {
                                Log.i("teste_galeria", "Item sem imagens ou lista de imagens nula")
                            }
                        }

                        withContext(Dispatchers.Main) {
                            catAdapter.addImg(listaUrlImagens)
                        }

                    } else {
                        Log.i("teste_galeria", "Lista de dados vazia ou nula")
                    }

                } else {
                    Log.i("teste_galeria", "Erro ao recuperar imagens")
                }

            } else {
                Log.i("teste_galeria", "Erro na resposta da API")
            }

        }
    }
}
