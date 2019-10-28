object BellmanFord {
//http://algorytmika.wikidot.com/bellman-ford
  //https://github.com/mission-peace/interview/blob/master/C%2B%2B/Graph%20Algorithms/Single%20Shortest%20Path%20Bellman%20Ford%20Algorithm.cpp
  //https://github.com/thinkphp/bellman-ford/blob/master/bellmanford.vector.cpp
 // https://eduinf.waw.pl/inf/alg/001_search/0138a.php
  def main(args: Array[String]): Unit = {

    //    val graph = Array(Node(0,Map(1->5,2->20)),Node(1,Map(2->6)),Node(2,Map()))
    val s = "0 6 11,0 1 5,1 3 3 1 4 9,2 0 3 2 1 -4,3 4 3 3 5 2,4 2 -1 4 5 -5,5 0 9 5 2 8"
    val graph = Parser.parseInput(s)
    println(graph.deep.mkString(", "))
    bellmanFord(graph,6)

  }


  def bellmanFord(graph: Array[Node], n: Int): Unit = {
    val inf = Int.MaxValue
    var d = Array.fill(n) {inf}
    var p = Array.fill(n) {-1}
    d(0) = 0
    for(_ <- 1 until n)
    for (g <- graph) {
      g.nodes.foreach {
        case (id: Int, distance: Int) =>
          if (d(g.i)!=inf && d(id) > d(g.i) + distance) {
            var t =d(g.i) + distance
            d(id) = d(g.i) + distance
            p(id) = g.i
          }
      }
    }
    println(d.deep.mkString(", "))
    println(p.deep.mkString(", "))
  }




}



