package geosot

import geosot.common._


class MortonCodeTest extends munit.FunSuite {

    private val coordinates = List[(Int, Int)](
        (0xFFFFFFFF, 0xFFFFFFFF),
        (0xC0007A, 0x954DDD70),
       (0x80D8C814, 0x15467C51)
    )
    private val quadCode = List[String](
        "33333333333333333333333333333333",
        "20020202130022022202220203331010",
        "10020202130112201322320002030102"
    )

    test("Encode") {
        for(i <- Range(0, coordinates.size)) {
            val code = MortonCode(Longitude(coordinates(i)._1), Latitude(coordinates(i)._2))
            var obtained = code.toString
            var expected = quadCode(i)
            assert(clue(obtained) == clue(expected))
        }
    }

    test("Decode") {
        for (i <- Range(0, quadCode.size)) {
            val mortonCode = quaternaryStringToBinaryCode(quadCode(i))
            val code = MortonCode(mortonCode)
            val res = code.decode()
            var obtained = (res(0), res(1))
            var expected = coordinates(i)
            assert(clue(obtained) == clue(expected))
        }
    }

    def quaternaryStringToBinaryCode(code: String): Array[Int] = {
        val pattern = "[0123]+".r
//        if (!pattern.matches(code) || code.size != 32) throw new IllegalArgumentException("Illegal code")

        val mortonCode = code.substring(0)
        val reversedCode = mortonCode.reverse + "0" * (32 - mortonCode.size)
        val res = Array.ofDim[Int](reversedCode.size * 2 / 32)
        for (i <- Range(0, reversedCode.size)) {
            val int_idx = i * 2 / 32
            val num = (reversedCode(i) - '0') & 0x3
            res(int_idx) = res(int_idx) | (num << (i * 2 % 32))
        }
        res
    }
}