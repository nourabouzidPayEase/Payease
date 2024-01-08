package models

import play.api.data.Forms._
import play.api.data._

case class ProductFormData(
                            id: Int,
                            productName: String,
                            description: String,
                            price: Int,
                            deletedAt: String,
                            imagestring: String,
                            imagebytea: Array[Byte],
                            loueurid: String
                          )

object ProductFormData {
  import play.api.data.Form
  import play.api.data.Forms._

  val nonArrayByte: Mapping[Array[Byte]] = list(byteNumber)
    .transform(
      byteList => byteList.toArray, // Convert List[Byte] to Array[Byte]
      byteArray => byteArray.toList   // Convert Array[Byte] to List[Byte]
    )
  // A form mapping to handle incoming form data
  val form: Form[ProductFormData] = Form(
    mapping(
      "id" -> number,
      "productName" -> nonEmptyText,
      "description" -> nonEmptyText,
      "price" -> number,
      "deletedAt" -> nonEmptyText,
      "imagestring" -> nonEmptyText,
      "imagebytea" -> nonArrayByte, // Use default for Array[Byte]
      "loueurid" -> nonEmptyText
    )(ProductFormData.apply)(ProductFormData.unapply)
  )
}