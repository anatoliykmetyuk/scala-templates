package templates

object Main {

  val tml =
"""def @2 = Shorthand(@1, () => @2)"""

  val target =
"""
      "|"    OrPar1
      "||"   OrPar2
      "&"    AndPar1
      "&&"   AndPar2
      "=="   Equality
      "+"    Alternative
      "%/%/" Interrupt0OrMore
      "%/"   Interrupt
      "%%"   Shuffle1OrMore
      "%"    Shuffle
      "/"    Disrupt
"""

  def main(args: Array[String]) {
    val result = Template(tml, equalIndents = true) ~> target
    println(result)
  }

}