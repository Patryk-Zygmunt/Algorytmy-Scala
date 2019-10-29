import scala.collection.mutable

object FordFulkerson {


  def main(args: Array[String]): Unit = {
    val s = "7 11,0 1 7 0 3 3,1 3 4 1 4 6,2 0 9 2 5 9,3 4 9 3 6 2,5 3 3 5 6 6,6 4 8"
    //val s = "0 6 11,0 1 5,1 3 3 1 4 9,2 0 3 2 1 4,3 4 3 3 5 2,4 2 1 4 5 -5,5 0 9 5 2 8"

    var graph = Parser.parseInput(s)
    graph+:=Node(4,mutable.Map())
  //  println(graph.mkString("\n"))
 fordFulkerson(graph,2,4,7).mkString(", ")
  //  println(bfs(graph,0,5,6).mkString(", "))
 //   println(nodesToArr(graph,6).map(_.mkString(", ")).mkString("\n"))
  //  println(arrToNode(nodesToArr(graph,6)).mkString("\n"))
  }



  def bfs(graph:Array[Node], s:Int , e:Int, size: Int): Array[Int] = {
    var visited = Array.fill(size){false}
    var queue = mutable.Queue[Node]()
    var parent = Array.fill(size){s}
    queue.+=(graph.find(n => n.i == s).get)
    parent(s) = s
    while(queue.nonEmpty) {
      val node = queue.dequeue()
      if(node.i==e) return findPathRec(parent,s,e)
      if (!visited(node.i)) {
        node.nodes.foreach {
          case (id: Int, distance: Int) => {
            queue += graph.find(n => n.i == id).get
            parent(id) = node.i
          }
        }
        visited(node.i)=true
      }
    }
    return  Array()
  }


  def fordFulkerson(graph:Array[Node], s:Int , e:Int, size: Int): Array[Node] = {
        var r  = nodesToArr(graph,size)
        var path:Array[Int] = bfs(graph,s,e,size)
        var flows = Array.fill(size){Int.MaxValue}
        var maxFlow = 0
        while(path.nonEmpty){
          for (v <-1 until path.length){
              flows(path(v-1)) = r(path(v-1))(path(v))
          }
          var min = flows.min
          maxFlow += min
          for (v <-1 until path.length){
            r(path(v-1))(path(v)) -= min
          //  r(path(v))(path(v-1)) += min
          }
          path = bfs(arrToNode(r),s,e,size)
     //     println(arrToNode(r).mkString(", "))

        }
    println(arrToNode(r).mkString("\n"))
    println(maxFlow)
      graph
  }


  def nodesToArr(graph:Array[Node], size: Int): Array[Array[Int]] = {
    var arr:Array[Array[Int]] = Array.ofDim(size,size)
    graph.foreach(g=>{
      g.nodes.foreach {
        case (id: Int, distance: Int) => {
          arr(g.i)(id) = distance
          arr(id)(g.i) = 0
        }
      }
    })
    arr
  }

  def arrToNode(arr:Array[Array[Int]]): Array[Node] = {
    var graph = Array[Node]()
    for(i<-0 until arr.length) {
      var n = Node(i, mutable.Map())
      for (j <- 1 until arr.length) {
        if (arr(i)(j) != null && arr(i)(j) > 0) {
          n.nodes+=(j->arr(i)(j))

        }
      }
      graph+:=n
    }
    graph
  }




  def findPathRec(parent:Array[Int], s:Int,nodeId:Int):Array[Int]={
    if(nodeId == s) return Array[Int](nodeId)
    else findPathRec(parent,s,parent(nodeId)) :+ nodeId
  }

}
