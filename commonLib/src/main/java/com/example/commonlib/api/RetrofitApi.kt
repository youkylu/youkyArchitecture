package com.example.commonlib.api

import com.example.commonlib.helper.RetrofitServiceManager

/**
 * <p>Class: com.example.junjie.youkyarchitecture.api.RetrofitApi</p>
 * <p>Description: </p>
 * <pre>
 *
 * </pre>
 *
 * @author lujunjie
 * @date 2019/2/1/15:38
 */

object RetrofitApi {

    private  var api: Api? = null

    val instence: Api
        get() {
            if (api == null) {
                synchronized(RetrofitApi::class.java) {
                    if (api == null) {
                        api = RetrofitServiceManager.createApi(
                            Api::class.java,
                            BASE_URL
                        )
                    }
                }
            }
            return api!!
        }
}