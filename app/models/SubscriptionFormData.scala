package models

case class SubscriptionFormData(
                            id: String,
                            subscriber_name: String,
                            email: String,
                            kilometrage: String,
                            Assurance: String,
                            Options: String,
                            start_date: String,
                            deleted_at: String,
                            active: Boolean,
                            loueur_id: Int,
                            return_date: String,
                            return_hour: String,
                            return_place: String,
                            pickup_date: String,
                            pickup_time: String,
                            pickup_place: String
                          )

object SubscriptionFormData {
  import play.api.data.Form
  import play.api.data.Forms._

  // A form mapping to handle incoming form data
  val form: Form[SubscriptionFormData] = Form(
    mapping(
      "id" -> nonEmptyText,
      "subscriber_name" -> nonEmptyText,
      "email" -> nonEmptyText,
      "kilometrage" -> nonEmptyText,
      "Assurance" -> nonEmptyText,
      "Options" -> nonEmptyText,
      "start_date" -> nonEmptyText,
      "deleted_at" -> nonEmptyText,
      "active" -> boolean,
      "loueur"-> number,
      "return_date" -> nonEmptyText,
      "return_hour" -> nonEmptyText,
      "return_place" -> nonEmptyText,
      "pickup_date" -> nonEmptyText,
      "pickup_time" -> nonEmptyText,
      "pickup_place" -> nonEmptyText
    )(SubscriptionFormData.apply)(SubscriptionFormData.unapply)
  )
}
