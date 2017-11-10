package io.intrepid.bleidiom.module

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.github.salomonbrys.kodein.*

/**
 * Contains the Kodein Modules configured for the entire app that hosts this library.
 */
@SuppressLint("StaticFieldLeak")
internal lateinit var appModules: Modules

/**
 * Configures the Kodein Modules for an app.
 * To configure, subclass it and instantiate an instance of this subclass.
 * @param appContext The app for which this Modules will be subclassed.
 * @param additionalConfig Lambda calling code that does additional Kodein configuration for this app.
 */
open class Modules(
        private val appContext: Application,
        private val additionalConfig: (Kodein.Builder.() -> Unit) = {})
    : KodeinAware {

    override val kodein by Kodein.lazy {
        import(RxAndroidBleModule)

        bind<Context>() with singleton { appContext }
        bind<Application>() with singleton { appContext }

        additionalConfig()
    }

    init {
        @Suppress("LeakingThis")
        appModules = this
    }
}
