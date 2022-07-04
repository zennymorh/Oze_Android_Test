package com.zennymorh.ozeandroidtest.ui.userdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.zennymorh.ozeandroidtest.R
import com.zennymorh.ozeandroidtest.data.model.UserDetail
import com.zennymorh.ozeandroidtest.data.repository.ListState
import com.zennymorh.ozeandroidtest.databinding.FragmentUserDetailBinding

class UserDetailFragment : Fragment() {

    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<UserDetailViewModel>()

    private val userName = UserDetailFragmentArgs.fromBundle(requireArguments()).user


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentUserDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserDetails(username = userName)
        observe()
    }

    private fun observe() {
        viewModel.listState.observe(viewLifecycleOwner, Observer {
            when (it) {
                ListState.Loading -> {

                }
                is ListState.Error -> {

                }
                is ListState.Success -> {
                    it.data?.let { data -> bindDataToView(data = data) }
                }
                else -> {}
            }
        })
    }

    private fun bindDataToView(data: UserDetail) {
        with(binding) {
            name.text = data.name
            bio.text = data.bio
            blog.text = data.blog
            location.text = data.location
            twitterUsername.text = data.twitterUsername
            followers.text = data.followers.toString()
            following.text = data.following.toString()

            if (data.avatarUrl != null) {
                Glide.with(requireContext())
                    .load(data.avatarUrl)
                    .into(avatar)
            } else {
                avatar.setImageResource(R.drawable.default_user_ic)
            }
        }
    }
}