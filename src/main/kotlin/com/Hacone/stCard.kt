package com.Hacone

class stCard {
    val cards = mutableMapOf<Long, MutableList<String>>()
    val attributes = mutableMapOf<String, MutableMap<String, Int>>()
    fun makeNewCard(uin: Long, card: String) {
        val list = cards.get(uin) ?: mutableListOf<String>()
        list.add(card)
        val att = attributes.get(card) ?: mutableMapOf<String, Int>()
        cards.set(uin, list)
        attributes.set(card, att)
    }

    fun anyCard(uin: Long, card: String): Boolean {
        val list = cards.get(uin) ?: mutableListOf<String>()
        return list.any { it == card }
    }

    fun setAttributes(card: String, Key: String, value: Int) {
        val attribute = attributes.get(card) ?: mutableMapOf<String, Int>()
        attribute.set(Key, value)
        attributes.set(card, attribute)
    }

    fun changerCard(uin: Long, card: String, message: String): String {
        if (!anyCard(uin, card)) makeNewCard(uin, card)
        val attribute = mutableMapOf<String, Int>()
        val data = message.drop(4).split(" ")
        data.forEach {
            if (it.indexOf("+") != -1) {
                var result = 0
                setAttributes(
                    card,
                    it.substring(0, it.indexOf("+")),
                    if (it.substring(it.indexOf("+") + 1).indexOf("d") != -1) {
                        val expression = it.substring(it.indexOf("+") + 1).split("d")
                        rand().moreRand(expression[0].toInt(), expression[1].toInt()).forEach { result += it }
                        result
                    } else {
                        result = it.substring(it.indexOf("+") + 1).toInt()
                        result
                    }
                )
                attribute.set(it.substring(0, it.indexOf("+")), result)
            }
        }
        data.forEach {
            if (it.indexOf("-") != -1) {
                var result = 0
                setAttributes(
                    card,
                    it.substring(0, it.indexOf("-")),
                    if (it.substring(it.indexOf("-") + 1).indexOf("d") != -1) {
                        val expression = it.substring(it.indexOf("-") + 1).split("d")
                        rand().moreRand(expression[0].toInt(), expression[1].toInt()).forEach { result += it }
                        result
                    } else {
                        result = it.substring(it.indexOf("-") + 1).toInt()
                        result
                    }
                )

                attribute.set(it.substring(0, it.indexOf("-")), result)
            }
        }
        var result = StringBuffer("已为${card}更新属性,新属性:\n")
        attribute.forEach { key, value -> result.append("${key}->${value}\n") }
        result.dropLast(1)
        return result.toString()
    }

    fun setCard(uin: Long, card: String, message: String): String {
        if (!anyCard(uin, card)) makeNewCard(uin, card)
        TODO()
    }
}