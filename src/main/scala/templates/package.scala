package object templates {

  implicit class RefinedString(str: String) {
    def stripRubish = str.stripMargin.drop(1).dropRight(1)
  }

  implicit class TransformAny[T](x: T) {
    def transform(f: T => T   ): T          = f(x)
    def side     (f: T => Unit): T          = transform(f.andThen {_ => x})
    def condition(c: Boolean)(f: T => T): T = if (c) transform(f) else x
  }

}