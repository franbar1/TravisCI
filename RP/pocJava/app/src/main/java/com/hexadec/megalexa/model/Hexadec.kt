package com.hexadec.megalexa.model

import com.hexadec.megalexa.model.blocks.Block
import com.hexadec.megalexa.adapter.GatewayController
import java.util.*
import kotlin.collections.ArrayList

class HexaDec {
    private var workflowList  = ArrayList<Workflow>()
    private  lateinit var user : HexadecUser

    fun saveUser(user: HexadecUser){
        this.user = user
        GatewayController.saveUser(user)
    }

    fun addWorkflow(wl: Workflow){
        workflowList.add(wl)
    }

    fun getWorkflowList(): ArrayList<Workflow> {
        return workflowList
    }

    fun getUser() : HexadecUser{
        return user
    }

    fun setUser(user: HexadecUser){
        this.user = user
    }

    fun loadWorkflow() : ArrayList<Workflow>{
        workflowList = GatewayController.readWorkflow(user)
        return workflowList
    }

    fun saveWorkflow(workflowName: String, blockList: ArrayList<Block>,create:Date,modify:Date,counter:Int){
        var workflow = Workflow(workflowName,create,modify,counter)
        workflow.setBlocks(blockList)
        workflowList.add(workflow)
        GatewayController.saveWorkflow(user, workflow)
    }

    fun getBlock(user: HexadecUser, w: String) : ArrayList<Block>? {
        for(item in workflowList){
            if(item.getWorkflowName() == w){
                return item.getBlocks(user)
            }
        }
        return null
    }

    fun isWorkflowIn(wl: String) : Boolean{
        var isIn  = false
        for(workflow in workflowList){
            if(workflow.getWorkflowName() == wl){
                isIn = true
            }
        }
        return isIn
    }

}