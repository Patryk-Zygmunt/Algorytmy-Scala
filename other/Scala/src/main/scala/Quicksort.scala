import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Quicksort {

  def quciksortFun(arr:mutable.ArrayBuffer[Int]):mutable.ArrayBuffer[Int] = {
    if(arr.length <= 1) return arr

    var pivot = arr(arr.length-1)
    var i=0
    for (j<- arr.indices){
      if(arr(j)<=pivot && i!=j ){
        i+=1
        swap(arr,i,j)
      }
    }
    (quciksortFun(arr.slice(0,i)) :+pivot) ++  quciksortFun(arr.slice(i+1,arr.length))
  }


  def swap(arr:mutable.ArrayBuffer[Int] ,i:Int, j:Int):Unit ={
    var tmp = arr(i)
    arr(i) = arr(j)
    arr(j) = tmp
  }


  def quciksort(arr:mutable.ArrayBuffer[Int], l :Int,h:Int):Unit = {
    if (l < h)  {
      var pivot = arr(h)
      var act = l
      for (i <- l until h) {
        if (arr(i) < pivot) {
          swap(arr, act, i)
          act += 1
        }
      }
      swap(arr, act, h)
      quciksort(arr, l, act - 1)
      quciksort(arr, act + 1, h)
    }
  }

  def main(args: Array[String]): Unit = {
    var arr = mutable.ArrayBuffer(1,8,3,9,4,5,7,344,22,11)
   quciksort(arr,0,arr.length-1)
    print(arr.mkString(","))
  }

}


//               j i
//1 8 3 9 4 5 7; 0 0
//1 8 3 9 4 5 7; 1 0
//1 8 3 9 4 5 7; 2 1
//1 3 8 9 4 5 7; 3 1
//1 3 4 9 8 5 7; 4 2
