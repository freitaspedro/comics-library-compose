package com.example.comicslibrary.model.conn

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import com.example.comicslibrary.SingletonHolder
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class ConnMonitor private constructor(context: Context): ConnObservable {

    companion object: SingletonHolder<ConnMonitor, Context>(:: ConnMonitor)

    private val connManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun observe(): Flow<ConnObservable.Status> = callbackFlow {
        val callback = object: ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                launch { send(ConnObservable.Status.Available) }
            }
            override fun onLost(network: Network) {
                super.onLost(network)
                launch { send(ConnObservable.Status.Unavailable) }
            }
        }
        connManager.registerDefaultNetworkCallback(callback)
        if (connManager.activeNetwork == null) {
            launch { send(ConnObservable.Status.Unavailable) }
        }
        awaitClose {
            connManager.unregisterNetworkCallback(callback)
        }
    }.distinctUntilChanged()

}