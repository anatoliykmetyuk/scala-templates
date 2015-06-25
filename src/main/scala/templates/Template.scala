package templates

import scala.language.implicitConversions

case class Template(
  template      : String
, prefix        : String = "@"
, ignoreNewlines: Boolean = false
, equalIndents  : Boolean = false
, sanitize      : Boolean = true
) {

  def ~>(args: String): String = {
    val argsSeq = args
      .transform {args => if (sanitize) args.stripRubish else args}
      .split("\n").toSeq
      .map(_.split(" ").toSeq)
      .map(_.filter(!_.isEmpty))

      .condition(ignoreNewlines) {_.filter(!_.isEmpty)}
      .condition(equalIndents  ) {as =>
        val maxArgsLengths = (0 until as.head.size).map {id => as.map(_(id).length).max}
        as.map {_
          .zip(maxArgsLengths)
          .map {case (arg, targetLength) => arg + " " * (targetLength - arg.length)}
        }
      }

    argsSeq
      .map {
        case arg if !arg.isEmpty => arg.foldLeft((template, 1)) {case ((t, idx), nextArg) => t.replace(s"$prefix$idx", nextArg) -> (idx + 1)}._1
        case arg if  arg.isEmpty => ""
      }
      .mkString("\n")
  }

}