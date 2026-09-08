package com.koalasat.pokey.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import java.net.InetSocketAddress
import java.net.Proxy

object TorProxyManager {
    private const val TAG = "TorProxyManager"
    const val ORBOT_PACKAGE = "org.torproject.android"
    const val ORBOT_SOCKS_PORT = 9050
    const val ORBOT_HTTP_PORT = 8118

    fun isOrbotInstalled(context: Context): Boolean {
        return try {
            context.packageManager.getPackageInfo(ORBOT_PACKAGE, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    fun requestOrbotStart(context: Context) {
        val intent = Intent("org.torproject.android.intent.action.START")
        intent.setPackage(ORBOT_PACKAGE)
        intent.putExtra("org.torproject.android.intent.extra.PACKAGE_NAME", context.packageName)
        try {
            context.sendBroadcast(intent)
            Log.d(TAG, "Requested Orbot start")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to request Orbot start", e)
        }
    }

    fun getSocksProxy(): Proxy {
        return Proxy(
            Proxy.Type.SOCKS,
            InetSocketAddress("127.0.0.1", ORBOT_SOCKS_PORT),
        )
    }

    fun getHttpProxy(): Proxy {
        return Proxy(
            Proxy.Type.HTTP,
            InetSocketAddress("127.0.0.1", ORBOT_HTTP_PORT),
        )
    }

    fun isTorAvailable(): Boolean {
        return try {
            val socket = java.net.Socket()
            socket.connect(InetSocketAddress("127.0.0.1", ORBOT_SOCKS_PORT), 1000)
            socket.close()
            true
        } catch (e: Exception) {
            false
        }
    }

    fun openOrbotPlayStore(context: Context) {
        try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = android.net.Uri.parse("market://details?id=$ORBOT_PACKAGE")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = android.net.Uri.parse("https://play.google.com/store/apps/details?id=$ORBOT_PACKAGE")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        }
    }
}
