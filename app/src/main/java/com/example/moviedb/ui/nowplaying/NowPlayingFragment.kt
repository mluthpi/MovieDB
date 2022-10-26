package com.example.moviedb.ui.nowplaying

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.data.ResultsItem
import com.example.moviedb.databinding.FragmentNowPlayingBinding
import com.example.moviedb.databinding.FragmentTopRatedBinding
import com.example.moviedb.detail.DetailsActivity
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
        val nowPlayingViewModel =
            ViewModelProvider(this).get(NowPlayingViewModel::class.java)

        _binding = FragmentNowPlayingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        nowPlayingViewModel.listNowPlaying.observe(viewLifecycleOwner, {nowPlaying ->
            showNowPlaying(nowPlaying)
        })


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        nowPlayingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()
        ).get(NowPlayingViewModel::class.java)

        nowPlayingViewModel.getNowPlaying("ba7b7ec258e912a3c68b34e6dfba3ca5")


        nowPlayingViewModel.listNowPlaying.observe(requireActivity(), {nowPlaying ->
            showNowPlaying(nowPlaying)
        })

        nowPlayingViewModel.isLoading.observe(requireActivity(), {isLoading ->
            showLoading(isLoading)
        })


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
}