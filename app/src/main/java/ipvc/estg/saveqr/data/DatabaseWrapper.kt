package ipvc.estg.saveqr.ui.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ipvc.estg.saveqr.ui.data.ORM.HistoryORM


class DatabaseWrapper(context: Context): SQLiteOpenHelper(context, "qr-code.db", null, 1) {

    private val TAG = javaClass.name

    private val DB_NAME = "qr-code.db"
    private val DB_VERSION = 2


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(HistoryORM().SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(HistoryORM().SQL_DROP_TABLE)

        onCreate(db)
    }

}