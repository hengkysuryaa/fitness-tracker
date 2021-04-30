package com.example.workout.ui.schedule

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
        SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val query = (
                "CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_START_TIME TEXT, $COLUMN_END_TIME TEXT, $COLUMN_DATE TEXT, $COLUMN_TYPE_SCHEDULE TEXT, $COLUMN_TYPE_SPORT TEXT, $COLUMN_REPEATED_DAY TEXT, $COLUMN_AUTO_START TEXT, $COLUMN_TARGET_SPORT TEXT);"
                )
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, old: Int, new: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addSchedule(schedule: Schedule) {
        val values = ContentValues()
        values.put("typeSport", schedule.typeSport)
        values.put("typeSchedule", schedule.typeSchedule)
        values.put("scheduleDate", schedule.scheduleDate)
        values.put("startTime", schedule.startTime)
        values.put("endTime", schedule.endTime)
        values.put("repeatedDay", schedule.repeatedDay)
        values.put("autoStart", schedule.autoStart)
        values.put("targetSport", schedule.targetSport)
        val db = this.writableDatabase
//        db?.execSQL("INSERT INTO schedule " +
//                "VALUES('Cycling','Once','30/4/2021','19:30'," +
//                "'19:45','null','false','100');")
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllSchedule(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "scheduleSQLite.db"
        val TABLE_NAME = "sch"
        val COLUMN_ID = "_id"
        val COLUMN_TYPE_SPORT = "typeSport"
        val COLUMN_TYPE_SCHEDULE = "typeSchedule"
        val COLUMN_DATE = "scheduleDate"
        val COLUMN_START_TIME = "startTime"
        val COLUMN_END_TIME = "endTime"
        val COLUMN_REPEATED_DAY = "repeatedDay"
        val COLUMN_AUTO_START = "autoStart"
        val COLUMN_TARGET_SPORT = "targetSport"



    }
}