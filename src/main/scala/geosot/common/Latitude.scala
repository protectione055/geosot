package geosot.common

import scala.util.matching.Regex

class Latitude extends Coordinate {
    private val _regex_dms : Regex = """(\d+)°(\d+)'(\d+(\.\d+)?)"\s([NS])""".r
}

object Latitude {
    def apply(dms: String) = {
        var lat = new Latitude
        lat.parseFromString(dms, lat._regex_dms)
        var res: Int = lat.concatDegMinSec()
        if (lat.direction == "S") {
            res = res | (1 << 31)
        }
        lat.value_ = res
        lat
    }

    def apply(value: Int) = {
        val obj = new Latitude
        obj.direction_ = if (value >>> 31 == 1) "S" else "N"
        obj.splitDegMinSec(value)
        obj.value_ = value
        obj
    }
}