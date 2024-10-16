package com.example.samojlov_av_homework_module_15_number_4_koala.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts_table")
data class Contact(
    @ColumnInfo(name = "surname") var surname: String,
    @ColumnInfo(name = "phone") var phone: String
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}