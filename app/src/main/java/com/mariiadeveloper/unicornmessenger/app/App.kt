package com.mariiadeveloper.unicornmessenger.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
// инициализировать и запусть модули, которые должны работать только когда приложение запускается
// то их нужно запускать в классе app
// тк тут есть метод onCreate callBack ЖЦ приложения
@HiltAndroidApp
// приложение использует хилт для инжекта зависимостей
class App: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}