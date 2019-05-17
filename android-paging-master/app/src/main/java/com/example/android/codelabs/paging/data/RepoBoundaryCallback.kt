package com.example.android.codelabs.paging.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.example.android.codelabs.paging.api.GithubService
import com.example.android.codelabs.paging.api.searchRepos
import com.example.android.codelabs.paging.db.GithubLocalCache
import com.example.android.codelabs.paging.model.Repo

class RepoBoundaryCallback(
        private val query: String,
        private val service: GithubService,
        private val cache: GithubLocalCache
) : PagedList.BoundaryCallback<Repo>() {

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
    }

    // keep the last requested page.
    // When the request is successful, increment the page number.
    private var lastRequestedPage = 1

    private val _networkErrors = MutableLiveData<String>()
    // LiveData of network errors.
    val networkErrors: LiveData<String>
        get() = _networkErrors

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    override fun onZeroItemsLoaded() {
        Log.d("RepoBoundaryCallback", "===> onZeroItemsLoaded, lastRequestedPage: $lastRequestedPage")
        requestAndSaveData(query)
    }

    override fun onItemAtEndLoaded(itemAtEnd: Repo) {
        Log.d("RepoBoundaryCallback", "===> onItemAtEndLoaded, lastRequestedPage: $lastRequestedPage")
        requestAndSaveData(query)
    }

    private fun requestAndSaveData(query: String) {
        if (isRequestInProgress) return

        isRequestInProgress = true
        searchRepos(service, query, lastRequestedPage, NETWORK_PAGE_SIZE, { repos ->
            cache.insert(repos) {
                lastRequestedPage++
                Log.d("RepoBoundaryCallback", "===> lastRequestedPage: $lastRequestedPage")
                isRequestInProgress = false
            }
        }, { error ->
            _networkErrors.postValue(error)
            isRequestInProgress = false
        })
    }
}