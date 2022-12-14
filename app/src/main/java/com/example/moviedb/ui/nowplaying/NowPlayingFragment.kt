package com.example.moviedb.ui.nowplaying

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.R
import com.example.moviedb.data.ResultsItem
import com.example.moviedb.databinding.FragmentNowPlayingBinding
import com.example.moviedb.databinding.FragmentTopRatedBinding
import com.example.moviedb.detail.DetailsActivity
import com.example.moviedb.favorite.FavoriteActivity
import com.example.moviedb.ui.toprated.TopRatedViewModel

class NowPlayingFragment : Fragment() {

    private var _binding: FragmentNowPlayingBinding? = null
    private val binding get() = _binding!!

    private lateinit var nowPlayingViewModel : NowPlayingViewModel

    private val movieAdapter = NowPlayingAdapter {
        val intent = Intent(this@NowPlayingFragment.requireContext(), DetailsActivity::class.java)
        intent.putExtra("KEY_ID", it.id)
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNowPlayingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        nowPlayingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()
        )[NowPlayingViewModel::class.java]

        nowPlayingViewModel.getNowPlaying("ba7b7ec258e912a3c68b34e6dfba3ca5")

        Log.d("TEST_DEBUG", "NowPlayingFragment running..")
        nowPlayingViewModel.listNowPlaying.observe(requireActivity()) { nowPlaying ->
            Log.d("TEST_DEBUG", "listNowPlaying.observe running..")
            showNowPlaying(nowPlaying)
            Log.d("TEST_DEBUG", "nowPlaying: ${nowPlaying.size}")
        }

        nowPlayingViewModel.isLoading.observe(requireActivity()) { isLoading ->
            showLoading(isLoading)
        }


    }

    private fun showNowPlaying(listNowPlaying: List<ResultsItem>) {
        movieAdapter.addItems(listNowPlaying)
        binding.rvNowPlaying.apply {
            layoutManager = LinearLayoutManager(
                requireActivity(),
                RecyclerView.VERTICAL, false
            )
            adapter = movieAdapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


}