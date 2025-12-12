package com.example.portfolio.exemplos.features.media

import android.content.ContentUris
import android.content.Context
import android.os.Build
import android.provider.MediaStore

class MediaReader(
    private val context: Context
) {
    fun getAllMediaFiles(): List<MediaFile> {
        val mediaFiles = mutableListOf<MediaFile>()

        val queryUri = if (Build.VERSION.SDK_INT >= 29) {
            MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            MediaStore.Files.getContentUri("external")
        }

        val projection = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.MIME_TYPE,
            MediaStore.Files.FileColumns.SIZE,
            MediaStore.Files.FileColumns.DATE_ADDED
        )

        context.contentResolver.query(
            queryUri,
            projection,
            null,
            null,
            null
        ).use { cursor ->
            val idColumn = cursor?.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
            val nameColumn =
                cursor?.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)
            val mimeTypeColumn =
                cursor?.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MIME_TYPE)
            val sizeColumn = cursor?.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE)
            val dateAddedColumn =
                cursor?.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_ADDED)

            while (cursor?.moveToNext() ?: false) {
                val id = cursor.getLong(idColumn!!)
                val name = cursor.getString(nameColumn!!)
                val mineType = cursor.getString(mimeTypeColumn!!)
                val size = cursor.getLong(sizeColumn!!)
                val dateAdded = cursor.getLong(dateAddedColumn!!)

                val contentUri = ContentUris.withAppendedId(
                    queryUri,
                    id
                )
                val mediaType = when {
                    mineType.startsWith("audio/") -> MediaType.AUDIO
                    mineType.startsWith("video/") -> MediaType.VIDEO
                    else -> MediaType.IMAGE
                }

                mediaFiles.add(
                    MediaFile(
                        uri = contentUri,
                        name = name,
                        type = mediaType,
                        size = size,
                        dateAdded = dateAdded
                    )
                )
            }
        }
        return mediaFiles
    }
}