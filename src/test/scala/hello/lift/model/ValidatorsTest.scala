package hello.lift {
package model {

import org.specs._
import org.specs.runner.JUnit3
import org.specs.runner.ConsoleRunner
import net.liftweb._
import http._
import net.liftweb.util._
import net.liftweb.common._
import net.liftweb.mapper._
import org.specs.matcher._
import org.specs.specification._
import Helpers._
import lib._

import Validators._

class ValidatorsTestSpecsAsTest extends JUnit3(ValidatorsTestSpecs)
object ValidatorsTestSpecsRunner extends ConsoleRunner(ValidatorsTestSpecs)

object ValidatorsTestSpecs extends Specification {
  class  SomeMapper extends LongKeyedMapper[SomeMapper] with IdPK {
    def getSingleton = SomeMapper

    trait MyValidateLength extends ValidateLength {
      self: MappedString[_] =>
      override def defaultErrorMessage = "too long"
    }

    object notNullDateField extends MappedDate(this) with NotNull

    object notNullStringField extends MappedString(this, 60) with NotNull

    object notNullLimitedStringField extends MappedString(this, 10) with MyValidateLength with NotNull

  }
  object SomeMapper extends SomeMapper with LongKeyedMetaMapper[SomeMapper]

  "NotNull validator" should {
    val target = SomeMapper.create
    
    "値が設定されている場合、validate結果はNil" in {
      target.notNullDateField(new java.util.Date)
      target.notNullStringField("hogehoge")
      target.validate must equalTo(Nil)
    }
    "Validationにかかる場合、validate結果はNilではない" in {
      target.notNullDateField.setFromAny(null)
      target.notNullStringField.setFromAny(null)
      target.validate.size must equalTo(2)
    }
    "他のValidationと同時にうごく" in {
      target.notNullLimitedStringField.setFromAny("hogehogehoge")
      target.validate.size must equalTo(2)
    }
  }
}

}
}
