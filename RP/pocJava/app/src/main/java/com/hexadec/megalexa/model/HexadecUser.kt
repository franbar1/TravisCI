package com.hexadec.megalexa.model
import org.json.JSONObject
import java.io.Serializable

class HexadecUser constructor(private val id: String ,private val name: String,private val email:String) : parserJson,Serializable{

    fun getName():String{
        return name
    }
    fun getId():String{
        return id
    }
    fun getEmail():String{
        return email
    }

    override fun toJSON() : JSONObject{
        val userJSON : JSONObject = JSONObject()
        userJSON.put("Id User", id)
        userJSON.put("name User", name)
        return userJSON
    }
}