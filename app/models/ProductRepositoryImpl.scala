package models

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.PostgresProfile
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.language.postfixOps

@Singleton
class ProductRepositoryImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                                     (implicit executionContext: ExecutionContext) extends HasDatabaseConfigProvider[PostgresProfile] {
  val produitsTable = TableQuery[ProductTable]
  val basic = produitsTable.filter(_.deletedAt isEmpty)

  def findByProduct(id: String): Future[Seq[Product]] = {
    val query = basic.filter(produit => produit.loueurid === id).result
     println(db.run(query))
    db.run(query)
  }

  def findByProductById(id: Int): Future[Seq[Product]] = {
    val query = basic.filter(produit => produit.id === id).result
    println(db.run(query))
    db.run(query)
  }

  def insertProduct(product: Product): Future[Int] = {
    println(product.toString)
    val insertAction = produitsTable += product
    db.run(insertAction)
  }
}
