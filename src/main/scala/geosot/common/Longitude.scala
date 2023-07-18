package geosot.common

import scala.util.matching.Regex

class Longitude extends Coordinate {
    private val _regex_dms : Regex = """(\d+)°(\d+)'(\d+(\.\d+)?)"\s([EW])""".r
}

object Longitude {
    def apply(dms: String) = {
        val lon = new Longitude
        lon.parseFromString(dms, lon._regex_dms)
        lon.parseFromString(dms, lon._regex_dms)
        var res: Int = lon.concatDegMinSec()
        if (lon.direction == "W") {
            res = res | (1 << 31)
        }
        lon.value_ = res
        lon
    }

    def apply(value: Int) = {
        val obj = new Longitude
        obj.direction_ = if (value>>>31 == 1) "W" else "E"
        obj.splitDegMinSec(value)
        obj.value_ = value
        obj
    }
}