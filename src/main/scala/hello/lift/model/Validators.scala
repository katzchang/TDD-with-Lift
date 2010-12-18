package hello.lift {
package model {

import net.liftweb._
import net.liftweb.util._
import net.liftweb.mapper._
import Helpers._

object Validators {
  trait NotNull extends MixableMappedField {
    selftype: MappedField[_, _] =>
      
    def validateNotNull(s: ValueType): List[FieldError] = 
      if (s == null) List(FieldError(this, "this should be not null"))
      else List[FieldError]()
    
    abstract override def validations = validateNotNull _ :: super.validations
  }
}

}
}
