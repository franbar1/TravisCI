package com.hexadec.megalexa.model

import com.hexadec.megalexa.model.blocks.Block
import com.hexadec.megalexa.adapter.GatewayController
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class Workflow constructor(private var workflowName:String, private val creationDate:Date, private val lastModifyDate:Date, private val interaction:Int):parserJson {
    private var blockList: ArrayList<Block> = ArrayList()

    fun addBlockToWorkflow(block: Block){
        blockList.add(block)
    }

    fun getLastModifyDate():Date{
        return lastModifyDate
    }

    fun getCreationDate():Date{
        return creationDate
    }

    fun getInteraction(): Int {
        return interaction
    }

    fun getWorkflowSize():Int {
        return blockList.size
    }

    fun removeBlockAtPosition(position:Int) {
        blockList.removeAt(position)
    }

    fun removeBlockFromWorkflow(block:Block) {
        blockList.remove(block)
    }

    fun getBlocks() : ArrayList<Block>{
        return blockList
    }

    fun getBlocks(user: HexadecUser) : ArrayList<Block>{
        blockList = GatewayController.readBlocks(user, this)!!
        return blockList
    }

    fun setBlocks(blockList : ArrayList<Block>){
        this.blockList = blockList
    }

    override fun toString(): String {
        return getWorkflowName()
    }

    fun getWorkflowName() : String{
        return workflowName
    }

    override fun toJSON() : JSONObject{
        var workflowName = JSONObject()
        var workflow = JSONArray()
        for(block in blockList){
            workflow.put(block.toJSON())
        }
        // need Update
        workflowName.put(this.getWorkflowName(), workflow)
        return workflowName
    }

}