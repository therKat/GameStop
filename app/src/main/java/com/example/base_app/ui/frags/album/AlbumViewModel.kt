package com.example.base_app.ui.frags.album

import com.example.base_app.ui.base.BaseViewModel
import javax.inject.Inject

class AlbumViewModel @Inject constructor(): BaseViewModel(){

    /** Function to scan albums */
//    fun getAlbums(context: Context): ArrayList<Album> {
//        val albums = ArrayList<Album>()
//        val mutableMapOfAlbum = mutableMapOf<String, MutableList<Album>>()
//        /** Album for image */
//        val sortOrder = "${MediaStore.Images.Media.BUCKET_DISPLAY_NAME} ASC"
//        val cursor = context.contentResolver.query(
//            uriImage, albumProjection, null, null, sortOrder
//        )
//        cursor?.use { c ->
//            while (c.moveToNext()){
//                val colAlbumName = c.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
//                val colAlbumData = c.getColumnIndex(MediaStore.Images.Media.DATA)
//                val albumName = c.getString(colAlbumName)
//                val file = File(c.getString(colAlbumData))
//                if (!mutableMapOfAlbum.containsKey(albumName)){
//                    mutableMapOfAlbum[albumName] = mutableListOf()
//                }
//                if (mutableMapOfAlbum[albumName]?.isEmpty() == true){
//                    val albumPath = file.path.substring(0, file.path.lastIndexOf("/"))
//                    if (File(albumPath).listFiles()?.isNotEmpty() == true){
//                        val mediaModels = getMediaByAlbum(context, albumName, uriImage, Constants.IMAGE_TYPE, imageProjection)
//                        mediaModels.addAll(getMediaByAlbum(context, albumName, uriVideo, Constants.VIDEO_TYPE, videoProjection))
//                        mutableMapOfAlbum[albumName]?.add(Album(albumName, albumPath, Constants.NORMAL, mediaModels))
//                    }
//                }
//            }
//        }
//        /** Album for video */
//        val cursorVideo = context.contentResolver.query(
//            uriVideo, albumProjection, null, null, sortOrder
//        )
//        cursorVideo?.use { c ->
//            while (c.moveToNext()){
//                val colAlbumName = c.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
//                val colAlbumData = c.getColumnIndex(MediaStore.Images.Media.DATA)
//                val albumName = c.getString(colAlbumName)
//                val file = File(c.getString(colAlbumData))
//                if (!mutableMapOfAlbum.containsKey(albumName)){
//                    mutableMapOfAlbum[albumName] = mutableListOf()
//                }
//                if (mutableMapOfAlbum[albumName]?.isEmpty() == true){
//                    val albumPath = file.path.substring(0, file.path.lastIndexOf("/"))
//                    if (File(albumPath).listFiles()?.isNotEmpty() == true){
//                        val mediaModels = getMediaByAlbum(context, albumName, uriVideo, Constants.VIDEO_TYPE, videoProjection)
//                        mediaModels.addAll(getMediaByAlbum(context, albumName, uriImage, Constants.IMAGE_TYPE, imageProjection))
//                        mutableMapOfAlbum[albumName]?.add(Album(albumName, albumPath, Constants.NORMAL, mediaModels))
//                    }
//                }
//            }
//        }
//        /** Pass data from map to list */
//        mutableMapOfAlbum.map { (key, value) ->
//            if (value.isNotEmpty()){
//                albums.add(value[0])
//            }
//        }
//        return albums
//    }

    /**
     * Function scan media
     * */
//    private fun getMediaByAlbum(context: Context, albumName: String, uri: Uri, type: Int, projection: Array<String>): ArrayList<MediaModel>{
//        val mediaModels = ArrayList<MediaModel>()
//        try {
//            val selection = "${MediaStore.Images.Media.BUCKET_DISPLAY_NAME}=?"
//            val selectionArgs = arrayOf(albumName)
//            val sortOrder = MediaStore.Images.Media.DATE_TAKEN + " DESC"
//
//            val cursor: Cursor? = context.contentResolver.query(
//                uri, projection, selection, selectionArgs, sortOrder
//            )
//            cursor?.use { c ->
//                while (c.moveToNext()){
//                    val colId = c.getColumnIndexOrThrow(projection[0])
//                    val colName = c.getColumnIndexOrThrow(projection[1])
//                    val colSize = c.getColumnIndexOrThrow(projection[2])
//                    val colData = c.getColumnIndexOrThrow(projection[3])
//                    val colHeight = c.getColumnIndexOrThrow(projection[4])
//                    val colWidth = c.getColumnIndexOrThrow(projection[5])
//                    var mDuration: Long? = null
//                    try {
//                        val colDuration = c.getColumnIndexOrThrow(projection[6])
//                        mDuration = c.getLong(colDuration)
//                    }catch (ex: Exception){
//                        mDuration = null
//                        ex.printStackTrace()
//                    }
//                    val data = c.getString(colData)
//                    val file = File(data)
//                    if (file.exists()){
//                        mediaModels.add(
//                            MediaModel(c.getLong(colId), c.getString(colName), data, Pair(c.getInt(colWidth), c.getInt(colHeight)), file.lastModified(), c.getLong(colSize), type, mDuration)
//                        )
//                    }
//                }
//            }
//        }catch (ex: Exception){
//            ex.printStackTrace()
//        }
//        return mediaModels
//    }

}