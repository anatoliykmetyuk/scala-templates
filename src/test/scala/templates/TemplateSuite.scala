package templates

import scala.language.implicitConversions

import org.scalatest._

class TemplateSuite extends FlatSpec with Matchers with TemplateSuiteHelpers {

  "A template" should "process simplest tempaltes correctly" in {
    basicTemplate.copy(sanitize = false) ~> "World" shouldBe "Hello World"
  }

  it should "process multiline templates" in {
    basicTemplate ~> """
      |World
      |Someone
    |""" shouldBe """
      |Hello World
      |Hello Someone
    |""".stripRubish
  }

  it should "process multiarg templates" in {
    Template("Hello, @1 @2. You're @3") ~> """
      |John Smith Smart
      |Little Bee Stupid
    |""" shouldBe """
      |Hello, John Smith. You're Smart
      |Hello, Little Bee. You're Stupid
    |""".stripRubish
  }

  it should "interpret new lines depending on the newline mode" in {
    basicTemplate.copy(ignoreNewlines = true) ~> """
      |A
      |
      |B
    |""" shouldBe """
      |Hello A
      |Hello B
    |""".stripRubish

    basicTemplate.copy(ignoreNewlines = false) ~> """
      |A
      |
      |B
    |""" shouldBe """
      |Hello A
      |
      |Hello B
    |""".stripRubish
  }

  it should "have an option to use equal identation for all arguments in same position" in {
    val args = """
      |Star bright
      |Cat yellow
      |N variable
    |"""

    twoArgTml.copy(equalIndents = true) ~> args shouldBe """
      |Star is bright  .
      |Cat  is yellow  .
      |N    is variable.
    |""".stripRubish

    twoArgTml ~> args shouldBe """
      |Star is bright.
      |Cat is yellow.
      |N is variable.
    |""".stripRubish
  }

  it should "not care about extra whitespaces in the args" in {
    twoArgTml.copy(sanitize = false) ~> "   A     B    " shouldBe "A is B."
  }
}

trait TemplateSuiteHelpers {

  val basicTemplate = Template("Hello @1")

  val twoArgTml     = Template("@1 is @2.")

}