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
    
    object validField extends MappedString(this, 60)
    object someField extends MappedString(this, 60) {
      override def validations = alwaysError(someField) _ :: Nil
    }
  }
  object SomeMapper extends SomeMapper with LongKeyedMetaMapper[SomeMapper]
  "Validationのキャラクタライズ" should {
    "Validationがない場合、validate結果はNil" in {
      val target = SomeMapper.create
      target.validField.validate must equalTo(Nil)
    }
    "Validationにかかる場合、validate結果はNilではない" in {
      val target = SomeMapper.create
      target.someField.validate.size must equalTo(1)
    }
  }
}

}
}
