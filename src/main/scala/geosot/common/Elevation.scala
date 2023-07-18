package geosot.common

class Elevation extends Dimension {
// TODO: 实现这个类
    override def getValue(precision: Int): Int = 0
}

object Elevation {
    def apply(elevation: String) = {
        val obj = new Elevation
        obj
    }
}
