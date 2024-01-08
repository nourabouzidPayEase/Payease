package models

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.PostgresProfile
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.language.postfixOps

@Singleton
class BankAccountRepositoryImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                                         (implicit executionContext: ExecutionContext) extends HasDatabaseConfigProvider[PostgresProfile] {
  val accountsTable = TableQuery[BankAccountTable]
  val basic = accountsTable

   def findByIdAgence(id: String): Future[Option[BankAccount]] = {
    val query = basic.filter(admin => admin.id_agence === id).result.headOption
     println(db.run(query))
    db.run(query)
  }

  def insertAccount(account: BankAccount): Future[Int] = {
    println(account.toString)
    val insertAction = accountsTable += account
    db.run(insertAction)
  }
}
