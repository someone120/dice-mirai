package com.Hacone

import net.mamoe.mirai.contact.Member

object charFormat {
    fun format(string: String,sender: Member,format:String?):String{
        var s=string.replace("{nick}",sender.nick).replace("{id}",sender.id.toString())
        if (format!=null){
            s=s.replace("{format}",format)
        }
        return s
    }
}