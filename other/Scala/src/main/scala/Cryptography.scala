import scala.collection.mutable



object Cryptography {



  def countLetters(s:String): Map[String,Int] =
        s.toCharArray.map(c=>(c.toString,s.toCharArray.count(vc=>vc==c))).toMap

  def findTwoTheBiggest(cm:Map[String,Int]): ((String,Int),(String,Int)) ={
    val cl = cm.toArray.sortBy((c)=>  c._2)
    (cl(0),cl(1))
  }

//    def fillTree(cm: Array[String],i1:Int,i2:Int,step:Int):(N,N)= {
//      var nl = new N(cm(i1))
//      var nr = new N(cm(i2))
//      var inxs = if(cm(i1).length > 1 && cm(i2).length > 1)  4 else 3;
//
//      (nl.left,nl.right) =   fillTree(cm, i1 + 2, i1 + inxs)
//      (nr.left,nr.right) =   fillTree(cm, i2 + 2, i2 + inxs)
//
//      (nl,nr)
//      }



def fillTree(cm: Array[String]):N={
  var q = mutable.Queue[(N,Array[String])]()
  var root = new N(cm(0))
  q.+=((root,cm.filter(!_.equals(cm(0)))))
  while(q.nonEmpty) {
    var (n,arr) = q.dequeue()
    if(arr.nonEmpty) {
      var nl = new N(arr(0))
      n.left = nl
      if (arr(0).length > 1) q.+=((nl, arr.filter(v => arr(0).contains(v) && !v.equals(arr(0)))))
      if(arr.length>1) {
        var nr = new N(arr(1))
        n.right = nr
        if (arr(1).length > 1) q.+=((nr, arr.filter(v => arr(1).contains(v) && !v.equals(arr(1)))))
      }
    }
  }
 return root
}

  def codeTree(n:N, prefix:String):Map[String,String]={
    val lprefix = "0"
    val rprefix = "1"

      if (n.i.length == 1) return Map(n.i -> (prefix))

    return codeTree(n.left,prefix + lprefix)  ++ codeTree(n.right,prefix + rprefix)
    }

def code(text:String):(Map[String,String],N)={
  var cm,cmT = countLetters(text)
  var size = cm.size -1
  for (_ <- 0 until size) {
    val (f, s) = findTwoTheBiggest(cmT)
    val newCode = ((f._1 + s._1) -> (s._2 + f._2))
    cmT = cmT.removedAll(Array(f._1, s._1))
    cmT += newCode
    cm += newCode
  }

  val ii = cm.toIndexedSeq.sortBy(-_._2)
  val tree = fillTree(ii.map(_._1).toArray)
  val cd =  codeTree(tree,"")
  (cd,tree)
}

  def decodeSign(tree:N,text:String): (Boolean,String)={
    var n = tree
    for(c<-text.toCharArray){
      if(c == '0')
        n = n.left
      else if(c == '1')
        n = n.right
    }
    if (n.i.length==1) return (true,n.i)
    return (false,"")


  }

  def decode(tree:N,text:String):String={
    var result=""
    var act=""
    for(c<-text.toCharArray){
      act += c
      val (found,sign) = decodeSign(tree,act)
      if(found){
        result+=sign
        act=""
      }
    }
  result
  }


  def main(args: Array[String]): Unit = {
    val text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi posuere, est molestie tincidunt suscipit, felis nibh condimentum libero, sed consequat tortor ex vitae metus. Pellentesque gravida dolor eros, a feugiat lorem hendrerit sit amet. Sed porttitor posuere sem id rutrum. Etiam non nisi non arcu elementum commodo a at est. Sed laoreet urna sit amet augue ultrices, id scelerisque elit cursus. Pellentesque a blandit mi. Cras dictum ligula quis neque consectetur, ut convallis ligula maximus. Proin tempus porttitor sem, laoreet faucibus ligula.\n\nProin convallis arcu diam, venenatis congue nisi semper vitae. Morbi eget lacinia ante, eu ultrices metus. Curabitur cursus justo vitae gravida tempor. Nam dictum ex et odio vestibulum varius. Sed luctus eros magna, et sodales tortor tristique sit amet. Mauris mollis velit at luctus cursus. Maecenas dignissim lorem eget risus sollicitudin, a pellentesque ligula sodales. Aliquam nisi elit, mattis vitae facilisis vel, commodo a sem. In ac augue suscipit, scelerisque sapien ut, lobortis ex.\n\nVestibulum ullamcorper pulvinar risus non luctus. Nunc nec iaculis ante. Integer accumsan fringilla nisi, ac fermentum nisi dignissim sit amet. Etiam vel dictum mauris. Sed imperdiet enim elit, ut luctus mauris pretium vel. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Integer venenatis magna at efficitur rutrum. Duis eu tortor eget augue molestie aliquet interdum eget sapien. Nulla sit amet sapien vel quam finibus ultricies et  Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi posuere, est molestie tincidunt suscipit, felis nibh condimentum libero, sed consequat tortor ex vitae metus. Pellentesque gravida dolor eros, a feugiat lorem hendrerit sit amet. Sed porttitor posuere sem id rutrum. Etiam non nisi non arcu elementum commodo a at est. Sed laoreet urna sit amet augue ultrices, id scelerisque elit cursus. Pellentesque a blandit mi. Cras dictum ligula quis neque consectetur, ut convallis ligula maximus. Proin tempus porttitor sem, laoreet faucibus ligula.\n\nProin convallis arcu diam, venenatis congue nisi semper vitae. Morbi eget lacinia ante, eu ultrices metus. Curabitur cursus justo vitae gravida tempor. Nam dictum ex et odio vestibulum varius. Sed luctus eros magna, et sodales tortor tristique sit amet. Mauris mollis velit at luctus cursus. Maecenas dignissim lorem eget risus sollicitudin, a pellentesque ligula sodales. Aliquam nisi elit, mattis vitae facilisis vel, commodo a sem. In ac augue suscipit, scelerisque sapien ut, lobortis ex.\n\nVestibulum ullamcorper pulvinar risus non luctus. Nunc nec iaculis ante. Integer accumsan fringilla nisi, ac fermentum nisi dignissim sit amet. Etiam vel dictum mauris. Sed imperdiet enim elit, ut luctus mauris pretium vel. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Integer venenatis magna at efficitur rutrum. Duis eu tortor eget augue molestie aliquet interdum eget sapien. Nulla sit amet sapien vel quam finibus ultricies et quis sem. Proin dignissim turpis eu metus molestie auctor.\n\nSuspendisse at rhoncus massa. Vestibulum tincidunt sodales volutpat. Donec tincidunt vulputate quam, quis tincidunt justo tincidunt vitae. Aenean malesuada dui non velit suscipit eleifend. Vestibulum luctus placerat ultricies. Interdum et malesuada fames ac ante ipsum primis in faucibus. Vestibulum pretium, erat commodo laoreet tincidunt, est nulla euismod augue, in bibendum ante enim id risus. Ut eu nisl ut felis molestie tempor ac id urna. Morbi ut ex eu nunc lacinia faucibus eget ac velit. Donec iaculis justo non semper faucibus. Suspendisse ullamcorper, sapien eget viverra pharetra, mauris quam pretium nisl, vitae auctor augue ipsum posuere magna. Pellentesque at ipsum consequat, auctor augue in, porttitor odio. In dictum eleifend velit. Maecenas id ex lobortis, auctor massa vel, hendrerit nunc. Maecenas a scelerisque felis. Nam nec dui a eros lacinia tristique.\n\nNulla facilisi. In convallis dapibus dolor ac congue. Aliquam blandit elit quis elit efficitur, nec placerat magna mattis. Quisque ultricies dolor nec erat egestas egestas. Vestibulum vel tellus tincidunt, placerat quam eu, bibendum mauris. Proin scelerisque dictum tortor, laoreet rutrum leo. Integer eget nulla in erat efficitur interdum ac at eros. Vivamus malesuada lobortis metus, sit amet lobortis dolor. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nulla facilisi. Ut ultrices vitae massa eu tempus. Sed ac vulputate lectus, at finibus nisi. Sed varius varius ligula nec convallis. Sed imperdiet odio eget odio viverra euismod. Etiam mattis leo ut odio scelerisque egestas.\n\nSed sed dapibus lorem. Cras consequat diam ut euismod mollis. Aliquam vitae auctor risus. Sed suscipit ipsum quis elit porttitor pharetra. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Maecenas at lacus eleifend, rhoncus lectus eget, molestie mauris. Suspendisse eget mauris pharetra, fermentum tellus ut, laoreet orci. Suspendisse potenti. Cras nec malesuada sapien. Pellentesque ultrices nisi eleifend, malesuada metus egestas, vehicula ipsum. Quisque mollis nulla ac purus commodo, non pulvinar diam lobortis. Nullam ac semper sapien. Proin varius finibus sem, bibendum mattis lorem hendrerit vel. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.\n\nMauris a dictum mi. Fusce interdum, enim nec ornare efficitur, lacus velit dignissim urna, nec mattis sem tellus vel leo. Praesent at massa erat. Duis lacinia nec dui id egestas. Sed dictum gravida fringilla. Nunc odio ipsum, lobortis id tincidunt nec, rutrum non libero. Suspendisse id orci mattis, vestibulum nunc eget, ullamcorper eros. Proin sed euismod lorem. Nullam euismod augue eget orci luctus, ut viverra tortor eleifend. Sed eu erat leo. Praesent ipsum sapien, ultricies vel urna vitae, tincidunt maximus risus. Praesent mattis bibendum egestas. Cras tristique cursus nibh. Nunc malesuada pretium dolor, a rhoncus neque sagittis eget.\n\nProin eget mauris quis nisi tempor lacinia. Aliquam gravida est metus, sed fringilla odio suscipit viverra. Fusce sed fringilla orci. Integer a mi magna. Etiam ac eros et felis suscipit rutrum eu ac libero. In eget quam vel urna laoreet finibus quis sit amet dui. Aliquam felis ex, consequat eget egestas sed, dapibus nec erat. Sed tincidunt feugiat ex. Phasellus lobortis felis vitae nisl laoreet luctus. Quisque ac erat ut massa elementum volutpat eu non ante. Nunc nec turpis sit amet erat sodales tincidunt. Nam porttitor nunc ipsum, sed auctor velit ultricies eget. Curabitur vulputate sapien ac nulla accumsan, at venenatis erat posuere. Vivamus facilisis massa massa, in condimentum turpis feugiat eu. Cras est metus, varius a iaculis sit amet, auctor ac massa. Aenean eget posuere turpis, sit amet finibus sem.\n\nVivamus tincidunt erat vel ligula semper, eget ornare quam congue. Curabitur vitae arcu ligula. Quisque hendrerit justo ac neque malesuada finibus. Ut convallis posuere sagittis. Donec dapibus efficitur tortor a aliquet. Morbi malesuada rutrum efficitur. Cras quis tincidunt quam, non consequat nisl. Etiam ut erat neque. Cras eu sem metus. Maecenas faucibus placerat quam et euismod. Nullam mattis mi ac nulla tincidunt efficitur.\n\nMorbi tristique cursus mauris feugiat dignissim. Curabitur sit amet venenatis dolor. Nam finibus ante at massa ultrices, sit amet malesuada tortor ultrices. Sed vitae tincidunt nulla. Fusce neque erat, auctor a sollicitudin sed, euismod vitae purus. Vestibulum maximus, neque ut porta rutrum, diam justo iaculis ante, ac malesuada quam ligula at felis. Phasellus id cursus sem. In mauris ante, eleifend eget egestas quis, pretium vel augue. In dapibus, lorem ut auctor porta, arcu mauris dignissim mi, a venenatis nunc arcu id nisi. Pellentesque dictum turpis sit amet convallis blandit. Morbi sed congue lorem, ut ultricies dolor. In hac habitasse platea dictumst. Phasellus elementum, sem placerat molestie facilisis, risus sapien posuere nisi, vel fermentum enim massa eget est. Etiam vitae sem molestie, semper ex hendrerit, ornare nisi.\n\nUt sollicitudin vitae ipsum non congue. Sed vehicula mollis libero, eu sollicitudin tortor dictum a. Duis lacus dui, eleifend a felis quis, vulputate dapibus quam. Mauris nunc massa, consequat sit amet dolor vitae, tincidunt hendrerit libero. Aliquam ut sem eu turpis porta ornare. Quisque et efficitur elit. Vestibulum at ipsum commodo, tristique magna et, rutrum ipsum. Morbi non aliquet elit. Duis sed orci erat. In euismod ut purus quis tempor.\n\nNam aliquam dui id purus vulputate, in vulputate ipsum pretium. Proin viverra mauris quis gravida aliquam. Nullam rhoncus eros odio. Nulla finibus luctus urna ac rhoncus. Suspendisse blandit posuere nisi, sed molestie nisi malesuada sit amet. Vestibulum dui neque, iaculis non nulla et, lacinia egestas ante. Sed dapibus varius porttitor. Etiam sem ex, mollis volutpat semper ac, tempor eget justo. Cras sit amet dui et nisl blandit euismod. Fusce quis purus quis elit vehicula fringilla. Quisque est massa, dictum sed scelerisque ac, aliquet in ligula. Duis pretium est vel felis tristique viverra. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer in leo massa. Donec lacinia ultricies aliquam.\n\nCras lacus lorem, tincidunt eget lorem ut, scelerisque viverra diam. Integer in lorem in ligula consequat pulvinar. Interdum et malesuada fames ac ante ipsum primis in faucibus. Quisque augue enim, euismod vel sapien quis, finibus convallis enim. Nulla vitae turpis eu dui pretium pharetra. Aliquam quis pharetra orci. Etiam pretium sem enim. Integer sit amet sagittis elit, in tempus ipsum. Duis nec diam volutpat, molestie erat vel, pharetra felis. Suspendisse ac varius diam. Nam sit amet hendrerit massa, ac pulvinar arcu. Aenean suscipit eleifend semper. Vivamus mattis mattis mi ut blandit. Curabitur sit amet justo tortor. Pellentesque vestibulum purus dui, dictum malesuada leo maximus at. Morbi rhoncus purus justo, in hendrerit turpis ornare quis.\n\nNulla facilisi. Sed auctor orci lorem, a aliquet erat dictum et. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Etiam velit ex, ultricies et sapien cursus, ullamcorper ultrices purus. Pellentesque laoreet sit amet magna a ullamcorper. Maecenas luctus molestie vulputate. Cras ac felis non massa dictum aliquet. Curabitur tempus congue est, ut sagittis metus ultricies et. Integer et massa dolor. Nulla ullamcorper placerat justo, non fermentum sapien sagittis ut. Aenean hendrerit mi ipsum, ac elementum justo malesuada imperdiet. Pellentesque ipsum elit, luctus nec placerat ut, porta et mi. Donec sit amet ullamcorper ex, eget fringilla lectus. Mauris convallis quis turpis ut tempus. Nulla sed hendrerit sem. Vivamus gravida cursus nunc vitae iaculis.\n\nMaecenas risus nulla, blandit vitae volutpat vel, accumsan a nibh. Mauris dapibus ac nibh vel tincidunt. Maecenas mauris lorem, sollicitudin a felis vitae, semper interdum purus. Phasellus nec hendrerit nunc. Etiam quis interdum tellus, non ullamcorper odio. Integer sed sodales metus, vitae vestibulum est. Quisque dictum sit amet ante sed pellentesque.\n\nAenean id augue eu nunc sagittis pharetra vel a orci. Phasellus quis massa sed tortor auctor blandit. Nulla facilisi. Cras dapibus aliquam molestie. Vestibulum ornare ipsum interdum, commodo est sed, ornare mi. In gravida turpis sem, nec mollis tellus laoreet eu. Integer malesuada tincidunt maximus. Duis ac nibh et ex lacinia pharetra. Aliquam rutrum suscipit nulla id vulputate.\n\nEtiam maximus risus at erat fringilla aliquet. Ut iaculis, risus vitae interdum tempor, ligula velit egestas felis, et venenatis ex justo ac velit. Ut dapibus justo at velit mollis vehicula a ac purus. Mauris in consequat risus, sit amet ullamcorper quam. Maecenas eleifend lorem at magna gravida, sit amet dignissim nisl finibus. Ut rhoncus pulvinar est et facilisis. In hac habitasse platea dictumst. Mauris dignissim sem sed consequat aliquet. Aliquam quis ullamcorper eros, vel feugiat eros. Suspendisse sodales felis in nulla interdum mollis. Proin ante velit, lacinia sed elit vel, venenatis pulvinar lacus. Nulla sit amet pulvinar lacus.\n\nNunc accumsan diam tellus, non hendrerit tortor imperdiet congue. Nunc vulputate felis eu leo congue tincidunt nec non lorem. Quisque enim eros, placerat quis laoreet vitae, consequat vel erat. In quis euismod lacus. Integer aliquam placerat nulla sed feugiat. Donec et orci lectus. Morbi venenatis nibh convallis ipsum pulvinar, eget dictum ligula ultricies.\n\nInteger convallis eros eu tortor mollis, non eleifend velit porttitor. Praesent tempus dolor in ipsum bibendum, a accumsan ipsum vehicula. Integer nec turpis quis ante fermentum tincidunt. Sed mi nunc, tempus vel ipsum quis, porttitor elementum lectus. Phasellus sit amet libero vel velit rhoncus convallis eu facilisis elit. Suspendisse placerat massa a mi rutrum, sed viverra metus tristique. Fusce dolor sapien, blandit eu molestie non, pulvinar at tortor. Fusce dignissim neque ac dictum consequat.quis sem. Proin dignissim turpis eu metus molestie auctor."
    val (codeTable,tree) = code(text)
    print(codeTable)
    val coded :String  = text.toCharArray.map(c=>codeTable(c.toString)).mkString("")
    print(coded)
    print(decode(tree,coded))

  }


}


case class N(i: String, var left: N, var right:N)  {

  def this(s:String) =
    this(s,null,null)

  override def toString = i + ": (" + left + "," + right + ")"

}

