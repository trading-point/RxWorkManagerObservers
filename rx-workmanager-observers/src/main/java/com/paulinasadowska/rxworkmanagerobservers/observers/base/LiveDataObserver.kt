package com.paulinasadowska.rxworkmanagerobservers.observers.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import io.reactivex.rxjava3.android.MainThreadDisposable

internal abstract class LiveDataObserver<T>(
        private val liveData: LiveData<T>
) : MainThreadDisposable(), Observer<T> {

    override fun onChanged(value: T) {
        if(!isDisposed){
            onChangedAndNotDisposed(value)
        }
    }

    abstract fun onChangedAndNotDisposed(value: T)

    override fun onDispose() {
        removeObserver()
    }

    protected fun removeObserver() {
        liveData.removeObserver(this)
    }
}