package com.ygtcomp.harcamatakipygt.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ygtcomp.harcamatakipygt.model.HarcamaModel


@Database(entities = [HarcamaModel::class], version = 2)
abstract class HarcamaDataBase: RoomDatabase() {

    abstract fun harcamaDao(): HarcamaDao

    companion object{
        private var INSTANCE: HarcamaDataBase? = null

        fun getDatabase(context: Context): HarcamaDataBase {
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    HarcamaDataBase::class.java,
                    "harcama_tablo"
                ).allowMainThreadQueries().build()
            }
            return INSTANCE as HarcamaDataBase
        }
    }
}