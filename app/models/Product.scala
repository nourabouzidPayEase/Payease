package models

case class Product(id: Int,
                   productName: String,
                   description: String,
                   price: Int,
                   deletedAt: Option[String],
                   imagestring: String,
                   imagebytea: Array[Byte],
                   loueurid: String)