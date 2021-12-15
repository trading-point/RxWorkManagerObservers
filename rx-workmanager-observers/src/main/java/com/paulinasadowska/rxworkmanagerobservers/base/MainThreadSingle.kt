package com.paulinasadowska.rxworkmanagerobservers.base

import com.paulinasadowska.rxworkmanagerobservers.exceptions.LiveDataSubscribedOnWrongThreadException
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable

abstract class MainThreadSingle<T : Any> : Single<T>() {

    override fun subscribeActual(observer: SingleObserver<in T>) {
        if (isOnMainThread()) {
            onSubscribeOnMainThread(observer)
        } else {
            observer.subscribeAndCallWrongThreadError()
        }
    }

    abstract fun onSubscribeOnMainThread(observer: SingleObserver<in T>)

    private fun SingleObserver<*>.subscribeAndCallWrongThreadError() {
        onSubscribe(Disposable.empty())
        onError(LiveDataSubscribedOnWrongThreadException())
    }
}