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


  def test():Array[Node] = {
    val s = "7 11,0 1 7 0 3 3,1 3 4 1 4 6,2 0 9 2 5 9,3 4 9 3 6 2,5 3 3 5 6 6,6 4 8"
    //val s = "0 6 11,0 1 5,1 3 3 1 4 9,2 0 3 2 1 4,3 4 3 3 5 2,4 2 1 4 5 -5,5 0 9 5 2 8"

    var graph = Parser.parseInput(s)
    graph+:=Node(4,mutable.Map())
    return graph
  }

}
