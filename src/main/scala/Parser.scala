import scala.collection.mutable

object Parser {

  def parseInput(input: String): Array[Node] = {
    var lines = input.split(",")
    var g = Array[Node]()
    for (i <- 1 until lines.length) {
      var n = lines(i).split(" ")
      var m = mutable.Map[Int, Int]()
      for (j <- n.indices by 3) {
        m+=(n(j + 1).toInt -> n(j + 2).toInt)
      }
      g :+= Node(n(0).toInt, m)
    }
    g
  }


}
