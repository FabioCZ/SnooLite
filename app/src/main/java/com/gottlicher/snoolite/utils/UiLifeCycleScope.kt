package com.gottlicher.snoolite.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * Coroutine context that automatically is cancelled when UI is destroyed
 */
class UiLifecycleScope : CoroutineScope, LifecycleObserver {

    val job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun destroy() = job.cancel()
}