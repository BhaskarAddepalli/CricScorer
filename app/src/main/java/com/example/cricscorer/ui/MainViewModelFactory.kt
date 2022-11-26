package com.example.cricscorer.ui
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cricscorer.ui.MainViewModel

class MainViewModelFactory(var bt1:Batsman,var bt2:Batsman,var bw1:Bowler):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(bt1,bt2,bw1) as T
    }
}