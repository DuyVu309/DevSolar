package com.base.baselibrary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {
    private val composites = CompositeDisposable()
    var event = MutableLiveData<Event>()

    protected fun <T> getTask(execute: () -> T, action: EventAction): Observable<T> {
        return Observable.create<T> {
            it.onNext(execute())
            it.onComplete()
        }
            .doRegister(action)
    }

    protected fun <T> Observable<T>.doRegister(action: EventAction): Observable<T> {
        return doOnSubscribe {
            event.postValue(Event(action, true))
        }.doOnComplete {
            event.postValue(Event(action, false))
        }
    }

    protected fun <T> Observable<T>.executeSubscribe(
        resultSubscribe: (T) -> Unit,
        subscribeOn: Scheduler = Schedulers.io(),
        observeOn: Scheduler = Schedulers.io()
        ): Disposable {
        val disposable =
            subscribeOn(subscribeOn)
                .observeOn(observeOn)
                .subscribe({
                    resultSubscribe(it)
                }, {
                    val s = event.value
                    s?.apply {
                        isLoading = false
                        error = it
                        event.postValue(s)
                    }
                })
        composites.add(disposable)
        return disposable
    }

    override fun onCleared() {
        super.onCleared()
        if (!composites.isDisposed) {
            composites.clear()
        }
    }
}