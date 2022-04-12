package com.example.learning.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class LearningViewModelProviderFactory(
    val learningRepository: LearningRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LearningViewModel(learningRepository) as T
    }
}

