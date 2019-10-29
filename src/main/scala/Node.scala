import scala.collection.mutable
import scala.collection.mutable.Map

case class Node(i: Int, nodes: mutable.Map[Int, Int])  {
  override def toString = i + ":" + nodes.iterator.map(pair => pair._1+"->"+pair._2).mkString(" ",",","")

}
