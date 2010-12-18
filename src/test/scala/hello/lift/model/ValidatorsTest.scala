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
    object someField extends MappedString(this, 60)
  }
  object SomeMapper extends SomeMapper with LongKeyedMetaMapper[SomeMapper]

  "Mapperのキャラクタライズ" should {
    "Validationがない場合、someFieldのvalidation結果はNil" in {
      val target = SomeMapper.create
      target.someField.validate must equalTo(Nil)
    }
  }
}

}
}
