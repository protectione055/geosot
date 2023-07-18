package geosot

import geosot.common._


/**
 * GeoSOT地球网格参考系统中的单个网格
 *
 * @author Ziming Zhang
 * @date 2023/7/17 21:54
 */
class Grid {
    private var latitude_ : Latitude = Latitude("0°0'0\" N")
    private var longitude_ : Longitude = Longitude("0°0'0\" E")
    private var elevation_ : Elevation = Elevation("0")
    private var level_ : Int = 32
    private var code_ : MortonCode = MortonCode(longitude_, latitude_, elevation_)

    override def toString: String = {
        "G" + code_.toString
    }

}

object Grid {
    def apply(lat: String, lon: String, level: Int): Grid = {
        val obj = new Grid
        obj.latitude_ = Latitude(lat)
        obj.longitude_ = Longitude(lon)
        obj.level_ = level
        obj.code_ = MortonCode(obj.longitude_, obj.latitude_)
        obj
    }

    def apply(lat: String, lon: String, elev: String, level: Int): Grid = {
        val obj = new Grid
        obj.latitude_ = Latitude(lat)
        obj.longitude_ = Longitude(lon)
        obj.elevation_ = Elevation(elev)
        obj.level_ = level
        obj.code_ = MortonCode(obj.longitude_, obj.latitude_, obj.elevation_)
        obj
    }
}

