package hello.lift {
package snippet {

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


class ValidatorsTestSpecsAsTest extends JUnit3(ValidatorsTestSpecs)
object ValidatorsTestSpecsRunner extends ConsoleRunner(ValidatorsTestSpecs)

object ValidatorsTestSpecs extends Specification {
  class  SomeMapper extends LongKeyedMapper[SomeMapper] with IdPK {
    def getSingleton = SomeMapper

    def alwaysError(field: FieldIdentifier)(s: String) = 
      List(FieldError(field, "always error..."))

    def noNull(field: FieldIdentifier)(s: String): List[FieldError] = 
      if (s == null) List(FieldError(field, "should be not null"))
      else List[FieldError]()

    object validField extends MappedString(this, 60)
    object someField extends MappedString(this, 60) {
      override def validations = noNull(this) _ :: Nil
    }
  }
  object SomeMapper extends SomeMapper with LongKeyedMetaMapper[SomeMapper]

  "Validationのキャラクタライズ" should {
    val target = SomeMapper.create
    
    "Validationがない場合、validate結果はNil" in {
      target.validField.validate must equalTo(Nil)
    }
    "Validationにかかる場合、validate結果はNilではない" in {
      target.someField.setFromAny(null)
      target.someField.validate.size must equalTo(1)
      target.someField("hoge")
      target.someField.validate must equalTo(Nil)
    }
  }
}

}
}
