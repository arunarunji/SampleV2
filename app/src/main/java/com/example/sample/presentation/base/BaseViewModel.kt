package com.example.sample.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.job
import java.util.Stack


abstract class BaseViewModel : ViewModel() {

    private var coroutineJob: Stack<Job> = Stack()

    private suspend fun <T> executeCoroutine(block: suspend () -> T): T {
        coroutineJob.add(currentCoroutineContext().job)
        return block()
    }

    suspend fun <T> executeCoroutine(onCancelled: () -> T, block: suspend () -> T): T {
        return try {
            executeCoroutine(block)
        } catch (cancelled: CancellationException) {
            onCancelled()
        }
    }

    protected fun cancelRunningJobs() {
        while (!coroutineJob.empty()) {
            coroutineJob.pop().cancel()
        }
    }

}