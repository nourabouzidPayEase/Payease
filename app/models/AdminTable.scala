package models

import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape

import java.time.OffsetDateTime

class AdminTable(tag: Tag) extends Table[Admin](tag, "admin") {
  def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def username: Rep[String] = column[String]("username")
  def password: Rep[String] = column[String]("password")
  def email: Rep[String] = column[String]("email")
  def deleted_at: Rep[Option[OffsetDateTime]] = column[Option[OffsetDateTime]]("deleted_at")

  def * : ProvenShape[Admin] = (id, username, password, email, deleted_at).<>(Admin.tupled, Admin.unapply)
}

