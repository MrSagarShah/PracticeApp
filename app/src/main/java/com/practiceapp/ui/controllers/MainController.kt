package com.practiceapp.ui.controllers

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bluelinelabs.conductor.Controller
import com.practiceapp.App
import com.practiceapp.R
import com.practiceapp.data.Videos
import com.practiceapp.ui.main.MainContract
import com.practiceapp.ui.main.MainPresenter
import com.practiceapp.ui.adapters.ImagesAdapter
import kotlinx.android.synthetic.main.controller_main.view.*
import retrofit2.Retrofit
import javax.inject.Inject

class MainController() : Controller(), MainContract.View {
    constructor(bundle: Bundle) : this()

    @Inject
    lateinit var retrofit: Retrofit
    lateinit var viewController: View
    private val mutableList: MutableList<Videos> = mutableListOf()

    private var mainPresenter: MainPresenter = MainPresenter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.controller_main, container, false)
        App.myComponent.inject(this)
        viewController = view
        setListeners()
        return view
    }

    private fun setListeners() {
        viewController.search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s?.isNotEmpty() == true) {
                    mainPresenter.doSearch(retrofit, s.toString())
                } else {
                    showList(emptyList())
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
        viewController.rvImageList.layoutManager = GridLayoutManager(getContext(), 2)
        viewController.rvImageList.adapter = ImagesAdapter(mutableList)
    }

    override fun showList(list: List<Videos>) {
        mutableList.clear()
        mutableList.addAll(list)
        viewController.rvImageList.adapter.notifyDataSetChanged()
    }

    override fun showMessage(msg: String) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show()
    }

    override fun getContext(): Context? {
        return activity?.baseContext
    }

}