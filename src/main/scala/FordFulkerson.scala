import scala.collection.mutable

object FordFulkerson {


  def main(args: Array[String]): Unit = {
    //val s = "7 11,0 1 7 0 3 3,1 3 4 1 4 6,2 0 9 2 5 9,3 4 9 3 6 2,5 3 3 5 6 6,6 4 8"
    val s = "0 6 11,0 1 5,1 3 3 1 4 9,2 0 3 2 1 -4,3 4 3 3 5 2,4 2 -1 4 5 -5,5 0 9 5 2 8"

    val graph = Parser.parseInput(s)
    println(findPathRec(bfs(graph,0,5),0,5).deep.mkString(", "))
  }



  def bfs(graph:Array[Node], s:Int , e:Int): Array[Int] = {
    var visited = Array.fill(6){false}
    var queue = mutable.Queue[Node]()
    var parent = Array.fill(6){-1}
    queue.+=(graph.find(n => n.i == s).get)
    parent(s) = s
    while(queue.nonEmpty) {
      val node = queue.dequeue()
      if(node.i==e) return parent
      if (!visited(node.i)) {
        node.nodes.foreach {
          case (id: Int, distance: Int) => {
            queue += graph.find(n => n.i == id).get
            parent(id) = node.i
          }
        }
        visited(node.i)
      }
    }
    return null
  }





  def findPathRec(parent:Array[Int], s:Int,nodeId:Int):Array[Int]={
    if(nodeId == s) return Array[Int](nodeId)
    else findPathRec(parent,s,parent(nodeId)) :+ nodeId
  }

}
