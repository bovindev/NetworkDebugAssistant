package com.dlong.networkdebugassistant.constant

import android.content.Context
import com.dlong.networkdebugassistant.bean.UdpBroadConfiguration
import com.dlong.networkdebugassistant.utils.SpfUtils
import org.json.JSONObject

/**
 * 配置参数
 *
 * @author D10NG
 * @date on 2019-12-05 14:49
 */
class DBConstant constructor(context: Context) {

    private val mSpf = SpfUtils.getInstance(context).getSpf()

    companion object{
        @Volatile
        private var INSTANCE: DBConstant? = null

        @JvmStatic
        fun getInstance(context: Context): DBConstant =
            INSTANCE?: synchronized(this) {
                INSTANCE?: DBConstant(context).also {
                    INSTANCE = it
                }
            }

        /** udp 广播 */
        private const val SPF_UDP_BROAD = "udp_broad_constant"
        /** udp 组播 */
        private const val SPF_UDP_MULTI = "udp_multi_constant"
        /** tcp 客户端 */
        private const val SPF_TCP_CLIENT = "tcp_client_constant"
        /** tcp 服务器 */
        private const val SPF_TCP_SERVER = "tcp_server_constant"
    }

    fun getUdpBroadConfiguration() : UdpBroadConfiguration {
        val constant = mSpf.getString(SPF_UDP_BROAD, "")?: ""
        if (constant.isEmpty()) return UdpBroadConfiguration()
        val configuration = UdpBroadConfiguration()
        configuration.setFromJson(JSONObject(constant))
        return configuration
    }

    fun setUdpBroadConfiguration(configuration: UdpBroadConfiguration) {
        val constant = configuration.toJson().toString()
        mSpf.edit().putString(SPF_UDP_BROAD, constant).apply()
    }
}