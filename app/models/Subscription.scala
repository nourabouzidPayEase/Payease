package models

case class Subscription(id: String,
                        subscriber_name: String,
                        email: String,
                        kilometrage: String,
                        Assurance: String,
                        Options: String,
                        start_date: Option[String],
                        deleted_at: Option[String],
                        active: Boolean,
                        loueur_id: Int,
                        status: Option[String],
                        return_date: Option[String],
                        return_hour: Option[String],
                        return_place: Option[String],
                        pickup_date: Option[String],
                        pickup_time: Option[String],
                        pickup_place: Option[String]
                       )
