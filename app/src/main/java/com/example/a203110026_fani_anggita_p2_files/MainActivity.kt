package com.example.a203110026_fani_anggita_p2_files

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader


class MainActivity : AppCompatActivity() {
    private var editTextFileName: EditText? = null
    private var editTextFolderName: EditText? = null
    private var editTextContent: EditText? = null
    private var buttonDeleteFile: Button? = null
    private var buttonWriteFile: Button? = null
    private var buttonReadFile: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        // TODO 1: Mengambil data input text dan membaca aktivitas tombol dari layout activity_main.xml
        editTextFileName = findViewById(R.id.editTextFileName)
        editTextFolderName = findViewById(R.id.editTextFolderName)
        editTextContent = findViewById(R.id.editTextContent)
        buttonReadFile = findViewById(R.id.buttonReadFile)

        // TODO 2: Tombol 'buttonReadFile'
        // diklik, maka akan membaca file yang telah disimpan (tergantung file yang dicari berdasarkan nama file dan folder
        with(buttonReadFile) { this?.setOnClickListener(View.OnClickListener { view -> buttonReadFile_onClick(view) }) }
        buttonWriteFile = findViewById(R.id.buttonWriteFile)
        with(buttonWriteFile) {
            this?.setOnClickListener(View.OnClickListener { view ->
                buttonWriteFile_onClick(
                    view
                )
            })
        }

        // Todo 3: Tombol ' buttonDeleteFile'
        // Jika diklik akan menghapus file tergantung file yang dicari berdasarkan nama file dan folder
        buttonDeleteFile = findViewById(R.id.buttonDeleteFile)
        with(buttonDeleteFile) {
            this?.setOnClickListener(View.OnClickListener { view ->
                buttonDeleteFile_onClick(
                    view
                )
            })
        }
    }

    // Mengambil data yang telah disimpan
    private fun buttonReadFile_onClick(view: View) {
        try {
            val result = StringBuilder()
            var line: String?
            val folder =
                application.filesDir.absolutePath + File.separator + editTextFolderName!!.text.toString()
            val subFolder = File(folder)
            val bufferedReader =
                BufferedReader(FileReader(File(folder, editTextFileName!!.text.toString())))
            while (bufferedReader.readLine().also { line = it } != null) {
                result.append(line)
            }
            editTextContent!!.setText(result.toString())
            // Jika file yang dicari tidak, maka akan menampilkan pesan kesalahan
        } catch (e: Exception) {
            Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
        }
    }

    // Menyimpan data baru
    private fun buttonWriteFile_onClick(view: View) {
        try {
            val folder =
                application.filesDir.absolutePath + File.separator + editTextFolderName!!.text.toString()
            val subFolder = File(folder)
            if (!subFolder.exists()) {
                subFolder.mkdirs()
            }
            val outputStream = FileOutputStream(File(subFolder, editTextFileName!!.text.toString()))
            outputStream.write(editTextContent!!.text.toString().toByteArray())
            outputStream.close()
            //   Toast akan muncul jika file berhasil disimpan
            Toast.makeText(applicationContext, getString(R.string.done), Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
        }
    }


    //    Mengahapus data
    private fun buttonDeleteFile_onClick(view: View) {
        try {
            val folder =
                application.filesDir.absolutePath + File.separator + editTextFolderName!!.text.toString()
            val subFolder = File(folder)
            val file = File(folder, editTextFileName!!.text.toString())
            if (file.exists()) {
                file.delete()
            }
            Toast.makeText(applicationContext, getString(R.string.done), Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
        }
    }
}