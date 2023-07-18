package geosot.common

// 点的维度分量，可能是坐标或时间
trait Dimension {
    var value: Option[Int] = None
    def getValue(precision: Int = 32) : Int
}
