package com.example.samojlov_av_homework_module_15_number_4_koala.utils

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.samojlov_av_homework_module_15_number_4_koala.models.Contact

@Dao
interface ContactDao {
    @Insert
    suspend fun insert(contact: Contact)

    @Delete
    suspend fun delete(contact: Contact)

    @Query("SELECT * FROM contacts_table ORDER BY id ASC")
    fun getAllContacts(): List<Contact>

    @Query("DELETE FROM contacts_table")
    fun deleteAll()
}