package com.example.wpmod.model

import android.os.Parcel
import android.os.Parcelable

data class RenderParams(
    val speed: Float,
    val scale: Float,
    val intensity: Float,
    val distortion: Float,
    val xOffset: Float,
    val yOffset: Float,
    val dotSize: Int,
    val xMax: Int,
    val yMax: Int,
    val step: Int,
    val xDivisor: Float,
    val xSubtractor: Float,
    val yDivisor: Float,
    val ySubtractor: Float,
    val oBase: Float,
    val oDivisor: Float,
    val sinDivisor: Float,
    val cosMultiplier: Float,
    val xKMultiplier: Float,
    val xScale: Float,
    val koMultiplier: Float,
    val yDivFactor: Float,
    val yScale: Float,
    val eoMultiplier: Float
) : Parcelable {

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeFloat(speed)
        dest.writeFloat(scale)
        dest.writeFloat(intensity)
        dest.writeFloat(distortion)
        dest.writeFloat(xOffset)
        dest.writeFloat(yOffset)
        dest.writeInt(dotSize)
        dest.writeInt(xMax)
        dest.writeInt(yMax)
        dest.writeInt(step)
        dest.writeFloat(xDivisor)
        dest.writeFloat(xSubtractor)
        dest.writeFloat(yDivisor)
        dest.writeFloat(ySubtractor)
        dest.writeFloat(oBase)
        dest.writeFloat(oDivisor)
        dest.writeFloat(sinDivisor)
        dest.writeFloat(cosMultiplier)
        dest.writeFloat(xKMultiplier)
        dest.writeFloat(xScale)
        dest.writeFloat(koMultiplier)
        dest.writeFloat(yDivFactor)
        dest.writeFloat(yScale)
        dest.writeFloat(eoMultiplier)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<RenderParams> = object : Parcelable.Creator<RenderParams> {
            override fun createFromParcel(source: Parcel): RenderParams = RenderParams(
                speed = source.readFloat(),
                scale = source.readFloat(),
                intensity = source.readFloat(),
                distortion = source.readFloat(),
                xOffset = source.readFloat(),
                yOffset = source.readFloat(),
                dotSize = source.readInt(),
                xMax = source.readInt(),
                yMax = source.readInt(),
                step = source.readInt(),
                xDivisor = source.readFloat(),
                xSubtractor = source.readFloat(),
                yDivisor = source.readFloat(),
                ySubtractor = source.readFloat(),
                oBase = source.readFloat(),
                oDivisor = source.readFloat(),
                sinDivisor = source.readFloat(),
                cosMultiplier = source.readFloat(),
                xKMultiplier = source.readFloat(),
                xScale = source.readFloat(),
                koMultiplier = source.readFloat(),
                yDivFactor = source.readFloat(),
                yScale = source.readFloat(),
                eoMultiplier = source.readFloat()
            )

            override fun newArray(size: Int): Array<RenderParams?> = arrayOfNulls(size)
        }

        fun defaultVortexParams() = RenderParams(
            speed = 1f,
            scale = 1f,
            intensity = 1f,
            distortion = 5f,
            xOffset = 130f,
            yOffset = 70f,
            dotSize = 1,
            xMax = 200,
            yMax = 200,
            step = 2,
            xDivisor = 10f,
            xSubtractor = 10f,
            yDivisor = 5.7f,
            ySubtractor = 20f,
            oBase = 5f,
            oDivisor = 3f,
            sinDivisor = 10f,
            cosMultiplier = 5f,
            xKMultiplier = 0f,
            xScale = 0.1f,
            koMultiplier = 5f,
            yDivFactor = 0.4f,
            yScale = 0.7f,
            eoMultiplier = 1f
        )

        fun defaultSpiralParams() = RenderParams(
            speed = 2.5f,
            scale = 1f,
            intensity = 1f,
            distortion = 5f,
            xOffset = 200f,
            yOffset = 200f,
            dotSize = 1,
            xMax = 90,
            yMax = 90,
            step = 1,
            xDivisor = 3f,
            xSubtractor = 11.5f,
            yDivisor = 9f,
            ySubtractor = 5f,
            oBase = 2f,
            oDivisor = 5.5f,
            sinDivisor = 2f,
            cosMultiplier = 6.6f,
            xKMultiplier = 4f,
            xScale = 0.7f,
            koMultiplier = 4f,
            yDivFactor = 16f,
            yScale = 0.7f,
            eoMultiplier = 4f
        )
    }
}