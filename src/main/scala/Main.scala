import scala.io._
import os._
import geny.Generator
import geosot.MortonCode

object Main {
  def main(args: Array[String]): Unit = {
    val proj_dir = os.pwd
    val input_files: Seq[os.Path] = os.list(proj_dir / "dataset").tail
//    readTestDataFromFile(input_files)

    val morton_coder = MortonCode
    val coordinate: Array[Int] = Array[Int](-1, -1, -1)
    val obtained = morton_coder.enCode(coordinate)
    val expected = Array[Byte](-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1)
  }

  private def readTestDataFromFile(input_files: Seq[os.Path]): Unit = {
    for (file <- input_files) {
      val lines: Seq[String] = os.read.lines(file)
      for (line <- lines) {
        var cells: Seq[String] = line.split(", ")
        val lat: String = cells(0)
        val lon: String = cells(1)
        val sot_code: String = cells(2)
        println(lat, lon, sot_code)
      }
    }
  }

}
