package models

case class BankAccountFormData(id_agence: String, id_account: String)

object BankAccountFormData {
  import play.api.data.Form
  import play.api.data.Forms._

  // A form mapping to handle incoming form data
  val form: Form[BankAccountFormData] = Form(
    mapping(
      "id_agence" -> nonEmptyText,
      "id_account" -> nonEmptyText)(BankAccountFormData.apply)(BankAccountFormData.unapply)
  )
}
