package com.hnimrod.itemtouchhelpersample.helper

interface ItemTouchHelperAdapter {

    fun onItemMove(fromPos: Int, toPos: Int): Boolean

    fun onItemDismiss(pos: Int)
}