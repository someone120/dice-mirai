package com.Hacone

import net.mamoe.mirai.console.plugins.Config
import net.mamoe.mirai.console.plugins.ConfigSection
import net.mamoe.mirai.console.plugins.withDefaultWriteSave
import java.security.acl.Group

class manager {
    private val status = Config.load("status.yml")
    fun setGroupStat(groupId: Long, older: Boolean) {
        status.setIfAbsent("GroupsStat", listOf<Long>())
        if (getGlobalStat() != older) {
            val GroupStat = status.getLongList("GroupsStat").toMutableList()
            if (GroupStat.any{it==groupId}){
                GroupStat.remove(groupId)
            }
            else
            {
                GroupStat.add(groupId)
            }
            status.set("GroupsStat", listOf(groupId))
        }
    }

    fun getGroupStat(groupId: Long): Boolean {
        status.setIfAbsent("GroupsStat", listOf<Long>())
        return status.getLongList("GroupsStat").any { it == groupId }
    }

    fun setGlobalStat(older: Boolean) {
        status["GlobalStat"]=older
    }

    fun getGlobalStat(): Boolean {
        return status.getBoolean("GlobalStat")
    }

    fun setCommonStat(common: String, older: Boolean) {
        status.setIfAbsent("CommonsStat", listOf<Long>())
        if (getGlobalStat() != older) {
            val GroupStat = status.getStringList("CommonsStat").toMutableList()
            if (GroupStat.any{it==common}){
                GroupStat.remove(common)
            }
            else
            {
                GroupStat.add(common)
            }
            status.set("GroupsStat", listOf(common))
        }
    }

    fun getCommonStat(common: String): Boolean {
        status.setIfAbsent("CommonsStat", listOf<String>())
        return status.getStringList("CommonsStat").any { it == common }
    }
}