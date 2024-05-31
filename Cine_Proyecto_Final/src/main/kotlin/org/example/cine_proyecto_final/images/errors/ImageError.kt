package org.example.cine_proyecto_final.images.errors

sealed class ImagesError (message : String){
    class SaveError(message: String) : ImagesError(message)
    class LoadError(message: String) : ImagesError(message)
    class DeleteError(message: String) : ImagesError(message)
}