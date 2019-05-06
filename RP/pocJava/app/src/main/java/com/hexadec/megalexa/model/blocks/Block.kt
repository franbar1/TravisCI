package com.hexadec.megalexa.model.blocks

import com.hexadec.megalexa.model.parserJson
import java.io.Serializable

interface Block :Serializable,parserJson{
    fun getConfiguration() : String
    fun getBLockInformation(): String
}
