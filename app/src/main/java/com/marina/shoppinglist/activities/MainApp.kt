package com.marina.shoppinglist.activities

import android.app.Application
import com.marina.shoppinglist.database.MainDatabase

class MainApp : Application() {
    val database by lazy { MainDatabase.getDatabase(this) }
}