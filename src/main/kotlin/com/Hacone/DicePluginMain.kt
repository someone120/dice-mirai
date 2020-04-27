package com.Hacone

import net.mamoe.mirai.console.plugins.Config
import net.mamoe.mirai.console.plugins.PluginBase
import net.mamoe.mirai.console.utils.getBotManagers
import net.mamoe.mirai.event.events.MessageRecallEvent
import net.mamoe.mirai.event.subscribeAlways
import net.mamoe.mirai.event.subscribeGroupMessages
import net.mamoe.mirai.utils.info

object DicePluginMain : PluginBase() {

    private var setting = loadConfig("setting.yml")
    override fun onLoad() {
        super.onLoad()
    }

    override fun onDisable() {
        super.onDisable()
        setting.save()
    }

    @ExperimentalStdlibApi
    override fun onEnable() {
        super.onEnable()
        logger.info("Plugin loaded!")

        subscribeGroupMessages {
            val rand = rand()
            ".bot on" reply {
                val status = setting.getConfigSection("${group.id}")
                if (getBotManagers(bot).any { it == sender.id }) {
                    if (status.getBoolean("Operating_Status")) {
                        "机器人已经启动了"
                    } else {
                        status["Operating_Status"] = true
                        "机器人已打开"
                    }
                } else {
                    "你是你妈的管理员"
                }
            }
            ".bot off" reply {
                val status = setting.getConfigSection("${group.id}")
                if (getBotManagers(bot).any { it == sender.id }) {
                    if (!status.getBoolean("Operating_Status")) {
                        "机器人已经关闭了"
                    } else {
                        status["Operating_Status"] = false
                        "机器人已关闭"
                    }
                } else {
                    "你是你妈的管理员"
                }
            }
            startsWith(".ra ", removePrefix = false) {
                if (setting.getConfigSection("${group.id}").getBoolean("Operating_Status"))
                    rand.ra(it, sender.nick)
            }
            ".r" reply {
                if (setting.getConfigSection("${group.id}").getBoolean("Operating_Status"))
                    "${sender.nick}掷出了一颗骰子：D100=${(1..100).random()}/100"
            }
            Regex(pattern = ".r ([0-9]+d[0-9]+\\+?)+") matchingReply {
                if (setting.getConfigSection("${group.id}").getBoolean("Operating_Status")) {
                    rand.r(it, sender.nick)
                }
            }
        }
    }
}