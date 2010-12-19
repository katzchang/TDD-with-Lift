package hello.lift.model

import net.liftweb.mapper._

object Busho extends Busho with LongKeyedMetaMapper[Busho] with LongCRUDify[Busho] {
}

class Busho extends LongKeyedMapper[Busho] with IdPK {
  def getSingleton = Busho

  object name extends MappedString(this, 60)
  object hoge extends MappedString(this, 10) with ValidateLength
}
