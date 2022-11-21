package com.example.vinilosgrupo3

import android.app.Application
import com.example.vinilosgrupo3.database.VinylRoomDatabase

class VinylsApplication: Application()  {
    val database by lazy { VinylRoomDatabase.getDatabase(this) }
}