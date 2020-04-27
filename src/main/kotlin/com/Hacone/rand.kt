package com.Hacone


class rand {
    fun ra(it: String, nick: String): String {
        val data = it.split(" ")
        val rand = (0..100).random()
        val tryNum = data[1].toInt()
        var result = "失败"
        if (rand <= tryNum) {
            result = "成功"
        }
        return "${nick}进行${data[0]}检测:D100=$rand/$tryNum\n结果:$result"
    }
    fun moreRand(num: Int, range: Int): MutableList<Int> {
        val numList = mutableListOf<Int>()
        for (i in 1..num) {
            numList.add((1..range).random())
        }
        return numList
    }
    @ExperimentalStdlibApi
    fun r(v: MatchResult, nick: String) :String{
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
}