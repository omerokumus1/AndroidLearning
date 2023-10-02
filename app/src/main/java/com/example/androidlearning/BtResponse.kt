package com.example.androidlearning

import com.google.gson.Gson

data class BtResponse(
    val result_type: String,
    val charge_point_id: String,
    val group_number: String,
    val hera_id: String,
    val ocpp_port: String,
    val ssl_enable: String,
    val ocpp_path: String,
    val authorization_key: String,
    val certificate_import: String,
    val network_priority_selection: String,
    val card_type: String,
    val local_startup: String,
    val qr_code_process: String,
    val transfer_private_data: String,
    val type1: String,
    val max_voltage: String,
    val min_voltage: String,
    val max_current: String,
    val max_temperature: String,
    val current: String,
    val four_g_enable: String,
    val link_status: String,
    val four_g_signal: String,
    val network_card: String,
    val ocpp_state: String,
    val apn: String,
    val four_g_user: String,
    val password: String,
    val pin: String,
    val dhcp_enable: String,
    val ip: String,
    val netmask: String,
    val gateway: String,
    val wifi_enable: String,
    val mode_selection: String,
    val ssid: String,
    val wifi_password: String,
    val encryption: String,
    val dhcpd_enable: String,
    val start_address: String,
    val terminate_address: String,
    val wifi_netmask: String,
    val wifi_gateway: String,
    val wifi_dns1: String,
    val wifi_dns2: String,
    val server_enable: String,
    val server_name: String,
    val server_pin: String,
    val screen_enable: String,
    val id_cover: String,
    val qr_code_enable: String,
    val modbus_ip: String,
    val modbus_port: String,
    val modbus_mode: String,
    val time_zone: String,
    val get_version: String
) {
    companion object {
        fun fromJson(json: String): BtResponse {
            return Gson().fromJson(json, BtResponse::class.java)
        }
    }
}
