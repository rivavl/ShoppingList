package com.marina.shoppinglist.utils

import android.content.Intent
import com.marina.shoppinglist.entities.ShopListItem
import java.lang.StringBuilder

object ShareHelper {

    fun shareShopList(shopList: List<ShopListItem>, listName: String): Intent {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plane"
        intent.apply {
            putExtra(Intent.EXTRA_TEXT, makeShareText(shopList, listName))
        }
        return intent
    }

    private fun makeShareText(shopList: List<ShopListItem>, listName: String): String {
        val sBuilder = StringBuilder()
        sBuilder.append("<<$listName>>\n")
        var counter = 0
        shopList.forEach {
            sBuilder.append("${++counter} - ${it.name} (${it.itemInfo})\n")
        }
        return sBuilder.toString()
    }
}