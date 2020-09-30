package com.eburg_soft.televisionbroadcasting.core

import android.content.Context
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class BaseContract {
    interface View {

//        fun getScreenComponent(context: Context): ScreenComponent =
//            (context.applicationContext as App)
//                .component
//                .createScreenComponent(ScreenContextModule(context))
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