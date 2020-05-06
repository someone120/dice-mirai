package com.Hacone

import java.util.*


class rand {

    /**
     * 表达式：.ra 事件 概率\n
     * 投出一个100面骰，当点数>概率时检测失败。
     */
    var rp = mutableMapOf<Long, Int>()
    var date = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    fun ra(it: String, nick: String): String {
        val data = it.split(" ")
        val rand = (0..100).random()
        val tryNum = data[1].toInt()
        var result = "失败"
        if (rand <= tryNum) {
            result = "成功"
        }
        return "${nick}进行${data[0]}判定:D100=$rand/$tryNum\n结果:$result"
    }

    /**
     * 表达式：.r XdY\n
     * 投出X个Y面骰
     */

    fun moreRand(num: Int, range: Int): MutableList<Int> {
        val numList = mutableListOf<Int>()
        for (i in 1..num) {
            numList.add((1..range).random())
        }
        return numList
    }

    /**
     * 解析表达式
     * 表达式：.r XdY
     * 投出X个Y面骰
     */
    @ExperimentalStdlibApi
    fun r(v: MatchResult, nick: String): String {
        val data = v.value.split(" ")[1].split("+")
        var size = 0
        for (i in data) {
            val parameter = i.split("d")
            size += parameter[0].toInt()
        }
        if (size >= 1000) {
            return "太多了……都溢出来了"
        } else {
            var numList = mutableListOf<Int>()
            for (s in data) {
                val parameter = s.split("d")
                numList = (moreRand(parameter[0].toInt(), parameter[1].toInt()) + numList).toMutableList()
            }
            val result = StringBuilder("${nick}掷出了${numList.size}个骰子:D")
            if (numList.size >= 100) {
                var num = 0
                for (i in numList) {
                    //   result.append("$i+")
                    num += i
                }
                result.append("=$num")
            } else {
                //val result = StringBuilder("${sender.nick}掷出了${numList.size}个骰子：")
                //numList= numList.sorted().toMutableList()
                numList.sort()
                var num = 0
                for (i in numList) {
                    result.append("$i+")
                    num += i
                }
                result.deleteAt(result.length - 1)
                result.append("=$num")
            }
            return result.toString()
        }
    }

    fun jrrp(ID: Long): String {
        if (rp.any { it.key == ID }) {
            if (date == Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) {
                return rp.get(ID).toString()
            }
        }
        val rd = (0..100).random()
        rp.set(ID, rd)
        return rd.toString()
    }
}