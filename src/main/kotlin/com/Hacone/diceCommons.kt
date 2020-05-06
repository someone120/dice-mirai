package com.Hacone

import net.mamoe.mirai.console.command.Command
import net.mamoe.mirai.console.command.CommandBuilder
import net.mamoe.mirai.console.command.ContactCommandSender
import net.mamoe.mirai.console.command.registerCommand
import net.mamoe.mirai.console.plugins.Config
import net.mamoe.mirai.console.plugins.PluginBase
import net.mamoe.mirai.contact.Group

object command {
    internal fun registerCommands(manager: manager) {
        DicePluginMain.registerCommand {
            name = "dice"
            alias = listOf("dice")
            description = "dice[骰娘]插件总指令"
            usage = "[/dice manager add qq] 添加管理员\n" +
                    "[/dice manager remove qq] 删除管理员\n" +
                    "更多设置请在插件配置文件中更改"
            onCommand {
                if (it.isEmpty()) {
                    return@onCommand false
                }
                when (it[0].toLowerCase()) {
                    "manager" -> {
                        when (it[1].toLowerCase()) {
                            "add" -> {
                                try {
                                    val qq = it[2].toLong()
                                    manager.addManager(qq)
                                    this.sendMessage("已添加${it[2].toLong()}为管理员")
                                } catch (e: Exception) {
                                    this.sendMessage("你输入的字符有误，请重新输入。")
                                    e.printStackTrace()
                                    return@onCommand false
                                }
                            }
                            "remove" -> {
                                try {
                                    manager.removeMananger(it[2].toLong())
                                    this.sendMessage("已删除${it[2].toLong()}的管理员")
                                } catch (e: Exception) {
                                    this.sendMessage("你输入的字符有误，请重新输入。")
                                    return@onCommand false
                                }
                            }
                        }
                    }
                }
                return@onCommand true
            }
        }
    }
}