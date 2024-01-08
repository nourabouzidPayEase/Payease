package models

import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape

class BankAccountTable(tag: Tag) extends Table[BankAccount](tag, "account") {
  def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def id_agence: Rep[String] = column[String]("id_agence")
  def id_account: Rep[String] = column[String]("id_account")

  def * : ProvenShape[BankAccount] = (id, id_agence, id_account).<>(BankAccount.tupled, BankAccount.unapply)
}

