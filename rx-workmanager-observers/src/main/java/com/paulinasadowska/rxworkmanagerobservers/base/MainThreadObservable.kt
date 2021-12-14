package com.paulinasadowska.rxworkmanagerobservers.base

import com.paulinasadowska.rxworkmanagerobservers.exceptions.LiveDataSubscribedOnWrongThreadException
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

abstract class MainThreadObservable<T : Any> : Observable<T>() {

    override fun subscribeActual(observer: Observer<in T>) {
        if (isOnMainThread()) {
            onSubscribeOnMainThread(observer)
        } else {
            observer.subscribeAndCallWrongThreadError()
        }
    }

    abstract fun onSubscribeOnMainThread(observer: Observer<in T>)

    private fun Observer<*>.subscribeAndCallWrongThreadError() {
        onSubscribe(Disposable.empty())
        onError(LiveDataSubscribedOnWrongThreadException())
    }
}