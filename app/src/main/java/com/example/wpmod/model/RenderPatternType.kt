package com.example.wpmod.model

enum class RenderPatternType {
    VORTEX,
    SPIRAL;

    companion object {
        fun fromString(value: String): RenderPatternType? {
            return try {
                valueOf(value.uppercase())
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    }

}