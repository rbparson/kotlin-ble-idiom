package io.intrepid.bleidiom.app

import android.app.Application
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.androidModule
import io.intrepid.bleidiom.module.Modules

/**
 */
@Suppress("PropertyName")
val DemoAppModules by lazy { DemoModules(DemoApp.INSTANCE) }

class DemoApp : Application() {
    companion object {
        lateinit var INSTANCE: DemoApp
    }

    init {
        INSTANCE = this
    }
}

class DemoModules(app: Application) : Modules(app, { configure() }) {
    companion object {
        private fun Kodein.Builder.configure() {
            import(androidModule)
        }
    }
}
