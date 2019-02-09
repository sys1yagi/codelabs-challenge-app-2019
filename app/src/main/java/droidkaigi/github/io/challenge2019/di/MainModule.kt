package droidkaigi.github.io.challenge2019.di

import droidkaigi.github.io.challenge2019.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel {
        MainViewModel(get(), get(), get())
    }
}
