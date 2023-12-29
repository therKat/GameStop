package com.example.base_app.ui.frags.home

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.base_app.R
import com.example.base_app.api.service.RetrofitInstance
import com.example.base_app.data.model.GameModel
import com.example.base_app.databinding.FragmentHomeBinding
import com.example.base_app.ui.base.BaseFragment
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    private lateinit var gameAdapter: GameAdapter

    companion object {
        fun newInstance() = HomeFragment().apply {
            arguments = Bundle().apply {
            }
        }

        val call = RetrofitInstance.gameApiService.getGames()
    }

    override fun layoutRes(): Int = R.layout.fragment_home

    override fun viewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java
    override fun initView() {
        binding.rvGame.layoutManager = GridLayoutManager(context, 2)
        gameAdapter = GameAdapter(emptyList())
        binding.rvGame.adapter = gameAdapter

        getMyGames()
    }

    private fun getMyGames() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                call.enqueue(object : Callback<Map<String, GameModel>> {
                    override fun onResponse(
                        call: Call<Map<String, GameModel>>,
                        response: Response<Map<String, GameModel>>
                    ) {
                        if (response.isSuccessful) {
                            val games: List<GameModel> =
                                response.body()?.values?.toList() ?: emptyList()
                            gameAdapter = GameAdapter(games)
                            binding.rvGame.adapter = gameAdapter
                        } else {
                            Log.e("namnamnam", "onResponse: csafasfasfas")
                        }
                    }

                    override fun onFailure(call: Call<Map<String, GameModel>>, t: Throwable) {
                        Log.e("namnamnam", "onResponse: " + t.message)
                    }
                })
            }
        }


    }

}