package com.example.samojlov_av_homework_module_15_number_4_koala

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.samojlov_av_homework_module_15_number_4_koala.databinding.ActivityMainBinding
import com.example.samojlov_av_homework_module_15_number_4_koala.models.Contact
import com.example.samojlov_av_homework_module_15_number_4_koala.utils.ContactDatabase
import kotlinx.coroutines.*

@Suppress("DeferredResultUnused")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var surnameEditTextET: EditText
    private lateinit var phoneEditTextET: EditText
    private lateinit var saveButtonBT: Button
    private lateinit var contactsTextViewTV: TextView

    private var db: ContactDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init()
    }

    override fun onResume() {
        super.onResume()
        saveButtonBT.setOnClickListener {
            if (surnameEditTextET.text.isEmpty() || phoneEditTextET.text.isEmpty()) return@setOnClickListener
            val contact =
                Contact(surnameEditTextET.text.toString(), phoneEditTextET.text.toString())
            addContact(db!!, contact)
            readDatabase(db!!)
            surnameEditTextET.text.clear()
            phoneEditTextET.text.clear()
        }
    }

    private fun init() {
        toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        title = getString(R.string.toolbar_title)
        toolbar.subtitle = getString(R.string.toolbar_subtitle)

        surnameEditTextET = binding.surnameEditTextET
        phoneEditTextET = binding.phoneEditTextET
        saveButtonBT = binding.saveButtonBT
        contactsTextViewTV = binding.contactsTextViewTV

        db = ContactDatabase.getDatabase(this)
        readDatabase(db!!)

    }


    @OptIn(DelicateCoroutinesApi::class)
    private fun addContact(db: ContactDatabase, contact: Contact) = GlobalScope.async {
        db.getContactDao().insert(contact)
    }



    @OptIn(DelicateCoroutinesApi::class)
    private fun readDatabase(db: ContactDatabase) = GlobalScope.async  {
        contactsTextViewTV.text = ""
        val list = db.getContactDao().getAllContacts()
        list.forEach { i -> contactsTextViewTV.append(i.surname + "\n" + i.phone + "\n\n") }
        Toast.makeText(this@MainActivity,"${list.size}",Toast.LENGTH_LONG).show()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun deleteAllContacts() = GlobalScope.async {
        db?.getContactDao()?.deleteAll()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @SuppressLint("SetTextI18n", "ShowToast")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exitMenu -> {
                Toast.makeText(
                    this,
                    getString(R.string.toast_exit),
                    Toast.LENGTH_LONG
                ).show()
                finishAffinity()
            }

            R.id.removeDBMenu -> {
                Toast.makeText(this, getString(R.string.removeDB_Toast), Toast.LENGTH_LONG).show()
                deleteAllContacts()
                contactsTextViewTV.text = ""
            }

        }
        return super.onOptionsItemSelected(item)
    }
}