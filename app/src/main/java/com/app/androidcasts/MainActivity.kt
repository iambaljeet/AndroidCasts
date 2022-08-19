package com.app.androidcasts

import android.os.Bundle
import android.os.Environment
import android.os.Environment.getExternalStoragePublicDirectory
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.app.androidcasts.databinding.ActivityMainBinding
import java.io.File

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    private val imageActivityResultContract = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) {
        Log.d(TAG, "isSuccess: $it")
    }

    private val videoActivityResultContract = registerForActivityResult(
        ActivityResultContracts.CaptureVideo()
    ) {
        Log.d(TAG, "isSuccess: $it")
    }

    private lateinit var binding: ActivityMainBinding

    private val externalStoragePublicDirectory =
        getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)

    private val file = File(externalStoragePublicDirectory, "default_image.jpg")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUri = FileProvider.getUriForFile(
            this,
            "$packageName.fileprovider",
            file
        )

        with(binding) {
            takePictureButton.setOnClickListener {
                videoActivityResultContract.launch(imageUri)
            }
        }
    }
}