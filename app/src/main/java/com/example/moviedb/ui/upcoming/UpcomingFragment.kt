package com.example.moviedb.ui.upcoming

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
import com.example.moviedb.databinding.FragmentUpcomingBinding
import com.example.moviedb.detail.DetailsActivity

class UpcomingFragment : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var upcomingViewModel: UpcomingViewModel
    private val upcomingAdapter = UpcomingAdapter {
        val intent = Intent(this@UpcomingFragment.requireContext(), DetailsActivity::class.java )
        intent.putExtra("KEY_ID", it.id)
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val upcomingViewModel =
            ViewModelProvider(this).get(UpcomingViewModel::class.java)

        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        upcomingViewModel.listUpcoming.observe(viewLifecycleOwner, {upcoming ->
            showUpcoming(upcoming)
        })

        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        upcomingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()
        ).get(UpcomingViewModel::class.java)

        upcomingViewModel.getUpcoming("ba7b7ec258e912a3c68b34e6dfba3ca5")

        upcomingViewModel.listUpcoming.observe(requireActivity(), {upcoming ->
            showUpcoming(upcoming)
        })

        upcomingViewModel.isLoading.observe(requireActivity(), {isLoading ->
            showLoading(isLoading)
        })
    }

    private fun showUpcoming(listUpcoming: List<ResultsItem>) {
        upcomingAdapter.addItems(listUpcoming)
        binding.rvUpcoming.apply {
            layoutManager = LinearLayoutManager(
                requireActivity(),
                RecyclerView.VERTICAL, false
            )
            adapter = upcomingAdapter
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