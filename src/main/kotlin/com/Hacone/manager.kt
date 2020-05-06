package com.Hacone

import net.mamoe.mirai.console.plugins.Config
import net.mamoe.mirai.utils.info
import net.mamoe.mirai.console.plugins.PluginBase
import net.mamoe.mirai.utils.MiraiLogger

class manager constructor(status: Config) {
    val status = status
    fun setGroupStat(groupId: Long, older: Boolean) {
            val GroupStat = status.getLongList("GroupsStat").distinct().toMutableList()
            if (older) {
                GroupStat.remove(groupId)
            } else {
                GroupStat.add(groupId)
            }
            status.set("GroupsStat", GroupStat)
            save()

    }

    fun getGroupStat(groupId: Long): Boolean {
        if (status.get("GroupsStat") == null) {
            status["GroupsStat"] = listOf<Long>(0)
            status.save()
            return true
        } else return !status.getLongList("GroupsStat").any { it == groupId }
    }

    fun setGlobalStat(older: Boolean) {
        //status.setIfAbsent("GlobalStat", true)
        status["GlobalStat"] = older
        save()
    }

    fun getGlobalStat(): Boolean {
        if (status.get("CommonsStat") == null) {
            status["GlobalStat"] = true
            status.save()
            return true
        } else return status.getBoolean("GlobalStat")
    }

    fun cat(): String {
        return status.toString()
    }

    fun setCommonStat(common: String, older: Boolean) {
        val GroupStat = status.getStringList("CommonsStat").distinct().toMutableList()
        MiraiLogger.info(GroupStat.toString())
        if (older) {
            GroupStat.remove(common)
            MiraiLogger.info(GroupStat.toString())
        } else {
            GroupStat.add(common)
        }
        status.set("CommonsStat", GroupStat)
        save()
    }

    fun getCommonStat(common: String): Boolean {
        if (status.get("CommonsStat") == null) {
            status["CommonsStat"] = listOf<String>("")
            status.save()
            return true
        } else return !status.getStringList("CommonsStat").any { it == common }
    }

    fun addManager(uin: Long) {
        val list = getManager().distinct().toMutableList()
        list.add(uin)
        status["manager"] = list
        save()
    }

    fun removeMananger(uin: Long) {
        val list = getManager().distinct().toMutableList()
        list.remove(uin)
        status["manager"] = list
        save()
    }

    fun getManager(): List<Long> {
        //status.setIfAbsent("manager", setOf<Long>())
        if (status.get("manager") == null) {
            status["manager"] = listOf<Long>(0)
            status.save()
            return listOf<Long>()
        } else return status.getLongList("manager")
    }

    fun save() {
        status.save()
    }
}