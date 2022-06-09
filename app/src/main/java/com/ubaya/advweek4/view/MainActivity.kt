package com.ubaya.advweek4.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ubaya.advweek4.R
import com.ubaya.advweek4.util.createNotificationChannel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    companion object {
        private var instance: MainActivity? = null

        fun showNotification(title: String, content: String, icon: Int) {
            val channelId = "${instance?.packageName}-${instance?.getString(R.string.app_name)}"
            instance?.let {
                val notificationBuilder =
                    NotificationCompat.Builder(it.applicationContext, channelId).apply {
                        setSmallIcon(icon)
                        setContentTitle(title)
                        setContentText(content)
                        setStyle(NotificationCompat.BigTextStyle())
                        priority = NotificationCompat.PRIORITY_DEFAULT
                        setAutoCancel(true)

                    }
                val notificationManager = NotificationManagerCompat.from(it.applicationContext)
                notificationManager.notify(1001, notificationBuilder.build())
            }

        }
    }
    init {
        instance = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel(this,
            NotificationManagerCompat.IMPORTANCE_DEFAULT, false,
            getString(R.string.app_name), "App notification channel.")


        val observable = Observable.just("a stream of data","hellow","world")
        val observer = object  : Observer<String>{
            override fun onSubscribe(d: Disposable) {
                Log.d("Messages", "begin subscribe")

            }

            override fun onNext(t: String) {
                Log.d("Messages", "data: $t")
            }

            override fun onError(e: Throwable) {
                Log.e("Rx error Messages", "error: ${e.message.toString()}")
            }

            override fun onComplete() {
                Log.i("Messages", "complete")
            }

        }
        observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
        Observable.just("a stream of data","hellow","world")
            .subscribe(
                { Log.d("Rxkotlin", it) },
                { Log.e("rxError", it.message.toString()) },
                { Log.i("Rx Kotlin Messages", "Finish") }
        )
        Observable.timer(5, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                Log.d("Delay", "five seconds")
            }

    }
}