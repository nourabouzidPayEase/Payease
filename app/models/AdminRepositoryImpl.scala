package models
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.PostgresProfile
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.language.postfixOps

@Singleton
class AdminRepositoryImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                                   (implicit executionContext: ExecutionContext) extends HasDatabaseConfigProvider[PostgresProfile] {
  val adminsTable = TableQuery[AdminTable]
  val basic = adminsTable.filter(_.deleted_at isEmpty)

   def findByUsernameAndPassword(username: String, password: String): Future[Option[Admin]] = {
    val query = basic.filter(admin => admin.username === username && admin.password === password).result.headOption
     println(db.run(query))
    db.run(query)
  }

}
