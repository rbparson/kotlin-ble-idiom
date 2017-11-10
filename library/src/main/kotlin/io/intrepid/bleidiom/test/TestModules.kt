package io.intrepid.bleidiom.test

import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.conf.ConfigurableKodein
import com.polidea.rxandroidble.RxBleClient
import com.polidea.rxandroidble.RxBleDevice
import com.polidea.rxandroidble.mockrxandroidble.RxBleClientMock
import com.polidea.rxandroidble.mockrxandroidble.RxBleDeviceMock
import io.intrepid.bleidiom.module.RxAndroidBleModule

/*
 * This file contains (a list of) Kodein module(s) used for testing.
 */

/**
 * Modules for BLE related D.I.
 */
class BleTestModules {
    companion object {
        val kodein get() = testKodein

        var testDevices: Iterable<RxBleDeviceMock> = listOf()

        private val testKodein: ConfigurableKodein = ConfigurableKodein(true)

        fun load(factory: Companion.() -> Unit) {
            testKodein.addImport(RxAndroidBleModule)
            testKodein.addImport(RxAndroidBleModuleOverride, allowOverride = true)
            this.factory()
        }

        fun unload() {
            testKodein.clear()
        }

        private val RxAndroidBleModuleOverride = Kodein.Module {
            // Override: Make a provider instead of a singleton.
            // Return a RxBleClientMock instead.
            bind<RxBleClient>(overrides = true) with provider {
                RxBleClientMock.Builder()
                        .setDeviceDiscoveryObservable(rx.Observable.from(testDevices))
                        .build()
            }

            // Add this one: Basically just a cast from RxBleDevice to ServerDevice...
            bind<ServerDevice>() with factory { macAddress: String ->
                with(macAddress).instance<RxBleDevice>() as ServerDevice
            }
        }
    }
}
