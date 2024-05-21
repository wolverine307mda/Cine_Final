package org.example.cine_proyecto_final.images.service

import com.github.michaelbull.result.Result
import org.example.cine_proyecto_final.images.errors.ImagesError
import java.io.File

interface ImagesService {
    fun saveImage(file: File): Result<File, ImagesError>
    fun loadImage(fileName: String): Result<File, ImagesError>
    fun deleteImage(file: File): Result<Unit, ImagesError>
    fun deleteAllImages(): Result<Long, ImagesError>
    fun updateImage(imagenName: String, newImage: File): Result<File, ImagesError>
}