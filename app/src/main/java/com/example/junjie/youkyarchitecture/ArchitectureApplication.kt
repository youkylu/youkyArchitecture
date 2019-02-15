package com.example.junjie.youkyarchitecture

import com.alibaba.android.arouter.launcher.ARouter
import com.example.commonlib.base.BaseApplication

/**
 *
 * Class: com.example.junjie.youkyarchitecture.ArchitectureApplication
 *
 * Description:
 * <pre>
 *
</pre> *
 *
 * @author lujunjie
 * @date 2019/2/15/15:45
 */

class ArchitectureApplication : BaseApplication(){

    private val debug = true

    override fun onCreate() {
        super.onCreate()
        initARouter()
    }

    private fun initARouter() {
        if (isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this) // 尽可能早，推荐在Application中初始化
    }

    private fun isDebug(): Boolean {
        return debug
    }
}
