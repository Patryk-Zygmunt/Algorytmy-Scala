import scala.collection.mutable

object FordFulkerson {


  def main(args: Array[String]): Unit = {

  }



  def bfs(graph:Array[Node], s:Int , e:Int): Array[Node] = {
    var visited = Array[Boolean]()
    var queue = mutable.Queue[Node]()
    var parent = Array[Int]()
    queue.+=(graph(0))
    visited(0) = true
    while(queue.nonEmpty) {
      val node = queue.dequeue()
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
  }



}
