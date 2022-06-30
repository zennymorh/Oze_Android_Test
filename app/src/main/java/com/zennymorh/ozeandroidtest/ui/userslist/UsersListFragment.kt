package com.zennymorh.ozeandroidtest.ui.userslist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zennymorh.ozeandroidtest.R
import com.zennymorh.ozeandroidtest.adapters.DataListDiffCallback
import com.zennymorh.ozeandroidtest.adapters.UserClickListener
import com.zennymorh.ozeandroidtest.adapters.UsersListAdapter
import com.zennymorh.ozeandroidtest.data.model.UsersDB
import com.zennymorh.ozeandroidtest.data.repository.ListState
import com.zennymorh.ozeandroidtest.databinding.FragmentUsersListBinding
import com.zennymorh.ozeandroidtest.ui.userdetail.UserDetailFragmentArgs
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class UsersListFragment : Fragment() {
    private var _binding: FragmentUsersListBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<UsersListViewModel>()

    private val disposable by lazy { CompositeDisposable() }

    private val onUserItemSelected by lazy {
        object : UserClickListener {
            override fun invoke(p1: UsersDB.UserDb) {
                val action = UsersListFragmentDirections.actionUsersListFragmentToUserDetailFragment(p1)
                findNavController().navigate(action)
            }

        }
    }

    private val userListAdapter: UsersListAdapter by lazy{
        UsersListAdapter(DataListDiffCallback, onUserItemSelected)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUsersListBinding.inflate(inflater, container, false)

        binding.placesListRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL, false)
            adapter = userListAdapter
        }

        disposable.add(viewModel.getUsers().subscribe {
            if (it != null ) Log.d("USERS", it.toString())
            userListAdapter.submitData(lifecycle, it)
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroy() {
        disposable.dispose()

        super.onDestroy()
    }
}