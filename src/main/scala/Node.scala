
case class Node(i: Int, nodes: Map[Int, Int])  {
  override def toString = i + ":" + nodes.map(pair => pair._1+"->"+pair._2).mkString(" ",",","")

}
