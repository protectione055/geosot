import scala.io._
import os._
import geny.Generator

object Main {
  def main(args: Array[String]): Unit = {
    val proj_dir = os.pwd
    val input_files: Seq[os.Path] = os.list(proj_dir / "dataset").tail
    for(file <- input_files) {
      val lines: Seq[String] = os.read.lines(file)
      for(line <- lines) {
        var cells: Seq[String] = line.split(", ")
        val lat: String = cells(0)
        val lon: String = cells(1)
        val sot_code: String = cells(2)
        println(lat, lon, sot_code)
      }
    }
  }
}
