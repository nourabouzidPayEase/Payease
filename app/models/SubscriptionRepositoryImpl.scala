package models

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.PostgresProfile
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.language.postfixOps

@Singleton
class SubscriptionRepositoryImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                                          (implicit executionContext: ExecutionContext) extends HasDatabaseConfigProvider[PostgresProfile] {
  val subscriptionTable = TableQuery[SubscriptionTable]
  val basic = subscriptionTable

  def findBySub(id: String): Future[Seq[Subscription]] = {
    val query = basic.filter(sub => sub.id === id).result
    println(db.run(query))
    db.run(query)
  }

  def findByLouerid(id: Int): Future[Seq[Subscription]] = {
    val query = basic.filter(sub => sub.loueur_id === id).result
    println(db.run(query))
    db.run(query)
  }

  def insertRent(sub: Subscription): Future[Int] = {
    println(sub.toString)
    val insertAction = subscriptionTable += sub
    db.run(insertAction)
  }

  def updateRent(sub: Subscription): Future[Int] = {
    println("sub to update " +sub)
    val updateAction = subscriptionTable
      .filter(_.id === sub.id)
      .update(sub)

    db.run(updateAction).recover {
      case ex: Throwable =>
        println(s"Error updating subscription with id ${sub.id}: ${ex.getMessage}")
        0 // Return a default value or handle the error accordingly
    }

  }

}
