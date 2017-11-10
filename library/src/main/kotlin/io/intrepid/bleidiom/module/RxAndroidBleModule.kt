package io.intrepid.bleidiom.module

import com.github.salomonbrys.kodein.*
import com.polidea.rxandroidble.RxBleClient
import com.polidea.rxandroidble.RxBleDevice
import io.intrepid.bleidiom.BleScanner

/**
 * This Kodein Module configures the D.I. for RxAndroidBLE related classes:
 * - [RxBleClient] instances (singleton).
 * - [BleScanner] instances (singleton).
 * - [RxBleDevice] instances (factory given a mac-address)
 */
@Suppress("PropertyName")
val RxAndroidBleModule = Kodein.Module {
    bind<RxBleClient>() with singleton { RxBleClient.create(instance()) }
    bind<BleScanner>() with singleton { BleScanner(instance()) }
    bind<RxBleDevice>() with factory { macAddress: String -> instance<RxBleClient>().getBleDevice(macAddress) }
}
