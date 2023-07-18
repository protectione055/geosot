package geosot

import geosot.common._

/*
 * @author: Ziming Zhang
 */
class MortonCode {
    private final val COMP_LEN = 32
    private var data_ : Array[Int] = Array()

    /*
     * 将字符串编码解码为各个维度上的值
     */
    def decode(): Array[Int] = {
        simpleSeparation(data_)
    }

    def encode(): Array[Int] = data_

    /*
        按照ZYX的顺序将比特位交叠起来
     */
    private def simpleInterleave(dims: Array[Dimension]): Array[Int] = {
        val dimension = dims.size
		var res: Array[Int] = Array.ofDim[Int](dimension)
        for(i: Int <- 0 to COMP_LEN - 1){
            for(j: Int <- 0 to dimension - 1) {
                val index: Int = (i * dimension + j) / COMP_LEN
                res(index) = (res(index) | (((dims(j).getValue() >>> i) & 1) << (i * dimension + j) % COMP_LEN))
            }
        }
        return res
    }

    /*
     * 从交叠的编码中还原出的各个坐标分量
     */
    private def simpleSeparation(code: Array[Int]): Array[Int] = {
        val dimension: Int = code.size
        var res: Array[Int] = Array.ofDim[Int](dimension)
        for (i: Int <- 0 to COMP_LEN - 1) {
            for (j: Int <- 0 to dimension - 1) {
                val offset: Int = i * dimension + j
                res(j) = (res(j) | (((code(offset / COMP_LEN) >>> (offset  % COMP_LEN)) & 1) << i))
            }
        }
        return res
    }

    override def toString: String = {
        toQuaternaryString
    }

    def toQuaternaryString: String = {
        val resultBuilder = new StringBuilder()
        val quaternaryDigits = "0123"

        for (i: Int <- data_.size-1 to 0 by -1) {
            var tmpInt = data_(i)
            val byteStringBuilder = new StringBuilder()
            for (_ <- 1 to COMP_LEN / 2) {
                val digit = quaternaryDigits.charAt(tmpInt & 0x03)
                byteStringBuilder.insert(0, digit)
                tmpInt = (tmpInt >> 2)
            }
            resultBuilder.append(byteStringBuilder.toString())
        }
        resultBuilder.toString()
    }
}

object MortonCode {
    def apply(dims: Array[Dimension]): MortonCode = {
        val obj = new MortonCode
        obj.data_ = obj.simpleInterleave(dims)
        obj
    }

    def apply(longitude: Longitude, latitude: Latitude): MortonCode = {
        val obj = MortonCode(Array[Dimension](longitude, latitude))
        obj
    }

    def apply(longitude: Longitude, latitude: Latitude, elevation: Elevation): MortonCode = {
        val obj = MortonCode(Array[Dimension](longitude, latitude, elevation))
        obj
    }

    def apply(code: Array[Int]): MortonCode = {
        val obj = new MortonCode
        obj.data_ = code
        obj
    }
}