package com.Hacone

import net.mamoe.mirai.console.plugins.Config
import net.mamoe.mirai.console.plugins.ConfigSection
import net.mamoe.mirai.console.plugins.PluginBase
import net.mamoe.mirai.console.plugins.withDefaultWriteSave
import net.mamoe.mirai.console.utils.getBotManagers
import net.mamoe.mirai.event.events.BotInvitedJoinGroupRequestEvent
import net.mamoe.mirai.event.events.MessageRecallEvent
import net.mamoe.mirai.event.subscribeAlways
import net.mamoe.mirai.event.subscribeGroupMessages
import net.mamoe.mirai.utils.info

object DicePluginMain : PluginBase() {
    override fun onLoad() {
        super.onLoad()
    }

    override fun onDisable() {
        super.onDisable()
    }

    /*
    * 打开时调用
    */
    @ExperimentalStdlibApi
    override fun onEnable() {
        super.onEnable()
        logger.info("Plugin loaded!")
        subscribeGroupMessages {
            //var status = true
            val rand = rand()
            val manager = manager()
            ".bot on" reply {
                if (getBotManagers(bot).any { it == sender.id }) {
                    if (manager.getGlobalStat()) {
                        "已经启动了。"
                    } else {
                        manager.setGlobalStat(older = true)
                        "启动成功。"
                    }
                } else {
                    "你是nm的管理员。"
                }
            }
            ".bot off" reply {
                if (getBotManagers(bot).any { it == sender.id }) {
                    if (!manager.getGlobalStat()) {
                        "已经关闭了。"
                    } else {
                        manager.setGlobalStat(older = false)
                        "已关闭。"
                    }
                } else {
                    "你是nm的管理员。"
                }
            }
            if (manager.getGlobalStat()) {
                startsWith(".ra ", removePrefix = false) {
                    if (manager.getGroupStat(sender.group.id)) {
                        if (manager.getCommonStat(".ra")) {
                            rand.ra(it, sender.nick)
                        } else {
                            ""
                        }
                    } else {
                        ""
                    }
                }
                ".r" reply {
                    if (manager.getGroupStat(sender.group.id))
                        if (manager.getCommonStat(".r")) {
                            "${sender.nick}掷出了一颗骰子：D100=${(1..100).random()}/100"
                        } else {
                            ""
                        }
                    else
                        ""
                }
                Regex(pattern = ".r ([0-9]+d[0-9]+\\+?)+") matchingReply {
                    if (manager.getGroupStat(sender.group.id))
                        if (manager.getCommonStat(".r")) {
                            rand.r(it, sender.nick)
                        } else {
                            ""
                        }
                    else
                        ""
                }
            }
        }
    }
}

