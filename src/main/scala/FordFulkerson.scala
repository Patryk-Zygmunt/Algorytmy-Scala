import scala.collection.mutable

object FordFulkerson {


  def main(args: Array[String]): Unit = {
    println(findPathRec(Array(0,0,1,0,2),0,2).deep.mkString(", "))
  }



  def bfs(graph:Array[Node], s:Int , e:Int): Array[Int] = {
    var visited = Array[Boolean]()
    var queue = mutable.Queue[Node]()
    var parent = Array[Int]()
    queue.+=(graph(0))
    visited(0) = true
    while(queue.nonEmpty) {
      val node = queue.dequeue()
      if(node.i==e) return parent;
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
