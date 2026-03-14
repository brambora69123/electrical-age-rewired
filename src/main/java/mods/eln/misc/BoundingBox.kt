package mods.eln.misc

import com.google.common.base.Objects
import net.minecraft.util.Vec3
import java.lang.Float.NEGATIVE_INFINITY
import java.lang.Float.POSITIVE_INFINITY

class BoundingBox(xMin: Float, xMax: Float, yMin: Float, yMax: Float, zMin: Float, zMax: Float) {
    val min: Vec3 = Vec3.createVectorHelper(xMin.toDouble(), yMin.toDouble(), zMin.toDouble())
    val max: Vec3 = Vec3.createVectorHelper(xMax.toDouble(), yMax.toDouble(), zMax.toDouble())

    fun merge(other: BoundingBox): BoundingBox {
        return BoundingBox(
            min.pos.x.coerceAtMost(other.min.pos.x).toFloat(),
            max.pos.x.coerceAtLeast(other.max.pos.x).toFloat(),
            min.pos.y.coerceAtMost(other.min.pos.y).toFloat(),
            max.pos.y.coerceAtLeast(other.max.pos.y).toFloat(),
            min.pos.z.coerceAtMost(other.min.pos.z).toFloat(),
            max.pos.z.coerceAtLeast(other.max.pos.z).toFloat()
        )
    }

    fun centre(): Vec3 {
        return Vec3.createVectorHelper(
            min.pos.x + (max.pos.x - min.pos.x) / 2,
            min.pos.y + (max.pos.y - min.pos.y) / 2,
            min.pos.z + (max.pos.z - min.pos.z) / 2
        )
    }

    override fun toString(): String {
        return Objects.toStringHelper(this)
            .add("min", min)
            .add("max", max)
            .toString()
    }

    companion object {
        @JvmStatic
        fun mergeIdentity(): BoundingBox {
            return BoundingBox(POSITIVE_INFINITY, NEGATIVE_INFINITY, POSITIVE_INFINITY, NEGATIVE_INFINITY, POSITIVE_INFINITY, NEGATIVE_INFINITY)
        }
    }

}
