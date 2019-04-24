package com.sal3awy.thed.dagger.App

import com.google.gson.Gson
import dagger.Component
import retrofit2.Retrofit
import java.util.concurrent.Executor


@AppScope
@Component(modules = [NetworkModule::class])
interface AppComponent {

    fun retrofit(): Retrofit

    fun executor(): Executor

    fun gson(): Gson

}