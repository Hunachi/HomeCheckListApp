package io.github.hunachi.homechecklistapp.ui

import android.app.Activity
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch

fun ViewModel.launchDataLoad(spinner: MutableLiveData<Boolean>, block: suspend () -> Unit): Job {
    return viewModelScope.launch {
        try {
            spinner.value = true
            block()
        } catch (error: Exception) {
            throw error
        } finally {
            spinner.value = false
        }
    }
}

// https://android.benigumo.com/20190731/cannot-inline-bytecode-built-with-jvm-target-1-8-into-bytecode-that-is-being-built-with-jvm-target-1-6-please-specify-proper-jvm-target-option/
// 参考にする。
@ExperimentalCoroutinesApi
fun TextView.textChangeAsFlow(): Flow<String?> =
    channelFlow {
        channel.offer(text.toString())
        val textWatcher = addTextChangedListener {
            channel.offer(it?.toString())
        }
        awaitClose {
            removeTextChangedListener(textWatcher)
        }
    }

fun TextView.leaveFromTextAsFlow(): Flow<String?> = channelFlow {
    setOnFocusChangeListener { view, hasFocus ->
        if (!hasFocus) {
            channel.offer(text.toString())
        }
    }
    awaitClose {
        onFocusChangeListener = null
    }
}


fun <T> LiveData<T>.nonNullObserver(
    context: LifecycleOwner, observer: (T) -> Unit
) {
    observe(context, Observer<T> {
        if (it != null) observer(it)
    })
}

fun Activity.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}