package com.hexadec.megalexa.model.blocks

import org.json.JSONObject

class BlockTextBox constructor(private var valueTextBox: String):Block {
    init{
        require(valueTextBox.length <= 256){
            println("Overflow Error, max lenght is 256")
        }
    }

    override fun getConfiguration(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getBLockInformation(): String {
        return "Text block"
    }

    fun setValueTextBox(text: String){
        valueTextBox = text
    }

    fun getValueTextBox(): String {
        return valueTextBox
    }


    override fun toJSON() : JSONObject{
        val allBlock : JSONObject = JSONObject()
        allBlock.put("blockType", "textToSpeech")
        val config : JSONObject = JSONObject()
        config.put("textToSpeech", valueTextBox )
        allBlock.put("config", config)
        return allBlock
    }
}