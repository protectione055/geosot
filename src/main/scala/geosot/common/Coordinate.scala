package geosot.common

import scala.util.matching.Regex

/**
 * 这是一个示例类，用于演示 Scaladoc 注释的规范。
 *
 * @throws IllegalArgumentException 当参数不合法时抛出异常
 * @see OtherClass 参考其他相关类
 * @example 示例代码
 *         {{{
 *         val example = new ExampleClass("John", 30, true)
 *         example.printInfo()
 *         }}}
 */
abstract class Coordinate extends Dimension{
    protected var degrees_ : Int = 0
    protected var minutes_ : Int = 0
    protected var seconds_ : Double = 0.0
    protected var direction_ : String = ""
    protected var value_ : Int = 0

    def degree = degrees_
    def minutes = minutes_
    def seconds = seconds_
    def direction = direction_

    override def getValue(level: Int = 32): Int = (value_ >>> (32 - level)) << (32 - level)

    // 接受 "1°32'0.06\" E" 格式的输入
    def parseFromString(dms: String, regex_dms: Regex) = {
        dms match {
            case regex_dms(degrees, minutes, seconds, _, direction) =>
                degrees_ = degrees.toInt
                minutes_ = minutes.toInt
                seconds_ = seconds.toDouble
                direction_ = direction
            case _ =>
                throw new IllegalArgumentException(s"${dms}")
        }
    }


    /**
     *
     * @param value 编码为整型的坐标
     *              将编码为整型的坐标分解为“度-分-秒”
     */
    protected def concatDegMinSec(): Int = {
        var res: Int = 0
        res = res | ((degrees_ & 0xFF) << 23)
        res = res | ((minutes_ & 0x3F) << 17)
        res = res | ((seconds_.toInt & 0x3F) << 11)
        val frac_part: Int = ((seconds_ - math.floor(seconds_)) * 2048).toInt
        res = (res | frac_part)
        res
    }

    /**
     *
     * @param value      编码为整型的坐标
     * 将编码为整型的坐标分解为“度-分-秒”
     */
    protected def splitDegMinSec(value: Int) = {
        val frac_part: Int = value & 0x7FF
        val seconds = (value >>> 11) & 0x3F
        seconds_ = seconds.toDouble + frac_part.toDouble/2048
        minutes_ = (value >>> 17) & 0x3F
        degrees_ = (value >>> 23) & 0xFF
    }

    /**
     * 返回"{度}°{分}'{秒}\" {方位}"格式的字符串"
     */
    override def toString = {
        val sb = new StringBuilder()
        sb.append(degrees_.toString + "°")
        sb.append(minutes_.toString + "\'")
        sb.append(seconds_.toString + "\"")
        sb.append(" " + direction_)
        sb.toString
    }
}