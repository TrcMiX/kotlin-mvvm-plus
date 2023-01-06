package com.mvvm.utilspack.ext.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch

object KtxLifecycleExt {
    fun (() -> Unit).repeatOnResumed(lifecycleOwner: LifecycleOwner) {
        lifecycleOwner.lifecycleScope.launch {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                this@repeatOnResumed.invoke()
            }
        }
    }

    fun LifecycleOwner.repeatOnResumed(function: () -> Unit) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                function.invoke()
            }
        }
    }
}