package com.example.carspareparts

import android.app.Application
import com.parse.Parse
import com.parse.ParseObject

/**
 * Created by rawan on 31/05/19 .
 */
class MyApplication:Application(){
    override fun onCreate() {
        super.onCreate()
        //ParseObject.registerSubclass(PinnedItemModel::class.java)
        Parse.initialize( Parse.Configuration.Builder(this)
            .applicationId(getString(R.string.back4app_app_id))
            .clientKey(getString(R.string.back4app_client_key))
            .server(getString(R.string.back4app_server_url))
            .enableLocalDataStore()
            .build()
        )
    }
}