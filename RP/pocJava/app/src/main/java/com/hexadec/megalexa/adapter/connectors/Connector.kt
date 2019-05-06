package com.hexadec.megalexa.adapter.connectors

interface Connector {
    fun connect(uri:String):String
    fun valid():Boolean
}