package com.mvvm.utilspack.ext.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object KtxLifecycleExt {
    /**
     * suspend {
     *     try {
     *         stateFlow.collect { value: String ->
     *             println("Received $value")
     *         }
     *     } catch (e: Exception) {
     *         e.printStackTrace()
     *     }
     * }.repeatOnResumed(this)
     */
    fun (suspend (CoroutineScope) -> Unit).repeatOnResumed(lifecycleOwner: LifecycleOwner) {
        lifecycleOwner.lifecycleScope.launch {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                this@repeatOnResumed.invoke(this)
            }
        }
    }

    /**
     * repeatOnResumed {
     *     try {
     *         stateFlow.collect { value: String ->
     *             println("Received $value")
     *         }
     *     } catch (e: Exception) {
     *         e.printStackTrace()
     *     }
     * }
     */
    fun LifecycleOwner.repeatOnResumed(function: suspend (CoroutineScope) -> Unit) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                function.invoke(this)
            }
        }
    }
}