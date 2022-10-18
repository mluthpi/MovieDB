package com.example.moviedb.ui.toprated

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.data.ResultsItem
import com.example.moviedb.databinding.FragmentTopRatedBinding
import com.example.moviedb.detail.DetailsActivity

class TopRatedFragment : Fragment() {

    private var _binding: FragmentTopRatedBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var topRatedViewModel: TopRatedViewModel

    private val topRatedAdapter = TopRatedAdapter {
        val intent = Intent(this@TopRatedFragment.requireContext(), DetailsActivity::class.java)
        intent.putExtra("KEY_ID", it.id)
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val topRatedViewModel =
            ViewModelProvider(this).get(TopRatedViewModel::class.java)

        _binding = FragmentTopRatedBinding.inflate(inflater, container, false)
        val root: View = binding.root

        topRatedViewModel.listTopRated.observe(viewLifecycleOwner, {topRated ->
            showTopRated(topRated)
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        topRatedViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()
        ).get(TopRatedViewModel::class.java)

        topRatedViewModel.getTopRated("ba7b7ec258e912a3c68b34e6dfba3ca5")

        topRatedViewModel.listTopRated.observe(requireActivity(),{topRated ->
            showTopRated(topRated)
        })

        topRatedViewModel.isLoading.observe(requireActivity(), {isLoading ->
            showLoading(isLoading)
        })
    }

    private fun showTopRated(listTopRated : List<ResultsItem>) {
        topRatedAdapter.addItems(listTopRated)
        binding.rvTopRated.apply {
            layoutManager = LinearLayoutManager(
                requireActivity(),
                RecyclerView.VERTICAL, false
            )
            adapter = topRatedAdapter
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