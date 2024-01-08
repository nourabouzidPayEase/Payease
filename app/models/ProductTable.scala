package models

import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape

class ProductTable(tag: Tag) extends Table[Product](tag, "produit") {
  def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def product_name: Rep[String] = column[String]("product_name")
  def desciption: Rep[String] = column[String]("description")
  def price: Rep[Int] = column[Int]("price")
  def deletedAt: Rep[Option[String]] = column[Option[String]]("deleted_at")
  def imagestring: Rep[String] = column[String]("imagestring") // Use Array[Byte] for image column
  def imagebytea :  Rep[Array[Byte]] = column[Array[Byte]]("image")

  def loueurid: Rep[String] = column[String]("loueurid") // Use Array[Byte] for image column

  def * : ProvenShape[Product] = (id, product_name, desciption, price, deletedAt, imagestring, imagebytea, loueurid).<>(Product.tupled, Product.unapply)
}