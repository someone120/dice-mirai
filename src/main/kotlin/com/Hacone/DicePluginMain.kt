package com.Hacone

import net.mamoe.mirai.console.plugins.PluginBase
import net.mamoe.mirai.event.subscribeGroupMessages

object DicePluginMain : PluginBase() {
    val manager = manager(loadConfig("setting.yml"))
    override fun onLoad() {
        super.onLoad()
    }

    override fun onDisable() {
        super.onDisable()
        manager.save()
    }

    /*
    * 打开时调用
    */
    @ExperimentalStdlibApi
    override fun onEnable() {
        super.onEnable()
        logger.info("Plugin loaded!")
        command.registerCommands(manager)

        subscribeGroupMessages {
            //var status = true
            val rand = rand()
            val stCard = stCard()
            ".help" reply {
                "欢迎使用骰娘！\n" +
                        ".bot on/off 骰娘的qq号 - 开/关骰娘\n" +
                        ".help 协议 - 获取骰娘的使用协议\n" +
                        ".help 指令 - 获取骰娘的指令\n" +
                        ".help 设定 - 获取骰娘的设定\n" +
                        ".help 文档 - 获取源码链接"
            }
            startsWith(".bot on ", removePrefix = true) {
                if (manager.getManager().any { it == sender.id } && (it.toLong() == bot.id)) {
                    if (manager.getGroupStat(sender.group.id)) {
                        reply("已经启动了。")
                    } else {
                        manager.setGroupStat(sender.group.id, older = true)
                        reply("启动成功。")
                    }
                } else {
                    reply("你是nm的管理员。")
                }
                return@startsWith
            }

            startsWith(".bot off ", removePrefix = true) {
                if (manager.getManager().any { it == sender.id } && (it.toLong() == bot.id)) {
                    if (!manager.getGroupStat(sender.group.id)) {
                        reply("已经关闭了。")
                    } else {
                        manager.setGroupStat(sender.group.id, older = false)
                        reply("已关闭。")
                    }
                } else {
                    reply("你是nm的管理员。")
                }
                return@startsWith
            }
            ".bot on" reply {
                if (sender.id == 525965357L) {
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
                if (sender.id == 525965357L) {
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
            startsWith(".bot enable ", removePrefix = true) {
                if (manager.getManager().any { it == sender.id }) {
                    manager.setCommonStat(it, true)
                    reply("已开启。")
                } else {
                    reply("你是nm的管理员。")
                }
            }
            startsWith(".bot disable ", removePrefix = true) {
                if (manager.getManager().any() { it == sender.id }) {
                    manager.setCommonStat(it, false)
                    reply("已关闭。")
                } else {
                    reply("你是nm的管理员。")
                }
            }
            startsWith(".bot managet add ", removePrefix = true) {
                if (manager.getManager().any() { it == sender.id }) {
                    manager.addManager(sender.id)
                    reply("已添加")
                } else {
                    reply("你是nm的管理员。")
                }
            }
            startsWith(".bot managet remove ", removePrefix = true) {
                if (manager.getManager().any() { it == sender.id }) {
                    manager.addManager(sender.id)
                    reply("已删除")
                } else {
                    reply("你是nm的管理员。")
                }
            }
            //骰娘开机与否
            startsWith(".ra ", removePrefix = true) {
                if (manager.getGlobalStat())
                    if (manager.getGroupStat(sender.group.id))
                        if (manager.getCommonStat(".ra")) {
                            reply(rand.ra(it, sender.nick))
                        }
            }
            ".r" reply {
                if (manager.getGlobalStat()) {
                    if (manager.getGroupStat(sender.group.id)) {
                        if (manager.getCommonStat(".r")) {
                            reply("${sender.nick}掷出了一颗骰子：D100=${(1..100).random()}/100")
                        }
                    }
                }
            }
            Regex(pattern = "\\.r ([0-9]+d[0-9]+\\+?)+") matchingReply {
                if (manager.getGlobalStat())
                    if (manager.getGroupStat(sender.group.id))
                        if (manager.getCommonStat(".r")) {
                            reply(rand.r(it, sender.nick))
                        }
            }
            ".jrrp" reply {
                var rp = rand.jrrp(sender.id)
                charFormat.format("Meteonic Shower!{nick}今天会收到我的{format}颗星星！", sender, rp)
            }
            Regex(pattern = "\\.st (.*--)?(\\S+(\\+|-)((\\d+)|(\\d+d\\d+)))+ ?") matchingReply {
                var value = it.value
                var card: String = sender.nick
                if (value.indexOf("--") != -1) {
                    card = value.drop(4)
                        .substring(value.indexOf("--"), value.length)
                }
                reply(stCard.changerCard(sender.id, card, it.value))
            }
        }
    }
}

