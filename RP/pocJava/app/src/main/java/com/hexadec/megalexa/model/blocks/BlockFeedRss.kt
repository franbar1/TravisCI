package com.hexadec.megalexa.model.blocks

import android.util.Log
import com.hexadec.megalexa.adapter.connectors.Connector
import com.hexadec.megalexa.adapter.connectors.ConnectorFeedRss
import com.hexadec.megalexa.model.ApplyFilter
import org.json.JSONObject


class BlockFeedRss(private val url: String): Block,ApplyFilter {
    init{
        val connector = generateConnector(url)
    }

    private fun generateConnector(url: String): Connector {
        val toReturn=ConnectorFeedRss(url= url)
        Log.d("asdfasdfasdfasdf", "Hello World")
        if(!toReturn.valid()){
            //TODO("custom error handling required ")
        }
        return toReturn
    }

    override fun getConfiguration(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun getBLockInformation():String {
        return "Feed RSS block "
    }

    override fun toJSON() : JSONObject {
        val allBlock = JSONObject()
        allBlock.put("blockType", "FeedRSS" )
        val config = JSONObject()
        config.put("url" , url)
        allBlock.put("config", config)
        return allBlock
    }
}