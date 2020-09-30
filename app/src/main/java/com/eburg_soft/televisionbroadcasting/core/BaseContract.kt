package com.eburg_soft.televisionbroadcasting.core

import android.content.Context
import com.eburg_soft.televisionbroadcasting.data.di.tvmenu.TVMenuComponent
import com.eburg_soft.televisionbroadcasting.data.di.tvmenu.TVMenuContextModule
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class BaseContract {
    interface View {

    }

    abstract class Presenter<V : View> {

        private val subscriptions = CompositeDisposable()
        protected var view: V? = null

        fun subscribe(subscription: Disposable) {
            subscriptions.add(subscription)
        }

        private fun unsubscribe() {
            subscriptions.clear()
        }

        open fun attach(view: V) {
            this.view = view
        }

        open fun detach() {
            unsubscribe()
        }
    }
}