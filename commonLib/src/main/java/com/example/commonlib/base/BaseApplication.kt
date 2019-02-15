package com.example.commonlib.base

import android.app.Application
import android.content.Context
import android.content.Intent
import java.util.*
import kotlin.properties.Delegates

/**
 *
 * Class: com.example.commonlib.base.BaseApplication
 *
 * Description:
 * <pre>
 *
</pre> *
 *
 * @author lujunjie
 * @date 2019/2/12/14:19
 */

abstract class BaseApplication : Application() {

    companion object {

        var context: Context by Delegates.notNull()
            private set

        lateinit var instance: Application

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        context = applicationContext
    }


}

