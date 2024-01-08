package models

import slick.jdbc.PostgresProfile.api._
import slick.lifted.{ProvenShape, Tag}

class SubscriptionTable(tag: Tag) extends Table[Subscription](tag, "subscription") {
  def id: Rep[String] = column[String]("id", O.PrimaryKey)
  def subscriber_name: Rep[String] = column[String]("subscriber_name")
  def email: Rep[String] = column[String]("email")
  def kilometrage: Rep[String] = column[String]("kilometrage")
  def assurance: Rep[String] = column[String]("assurance")
  def options: Rep[String] = column[String]("options") // Use Array[Byte] for image column
  def status: Rep[Option[String]] = column[Option[String]]("status")
  def start_date: Rep[Option[String]] = column[Option[String]]("start_date") // Use Array[Byte] for image column
  def deleted_at: Rep[Option[String]] = column[Option[String]]("deleted_at") // Use Array[Byte] for image column
  def active: Rep[Boolean] = column[Boolean]("active") // Use Array[Byte] for image column
  def loueur_id: Rep[Int] = column[Int]("loueur") // Use Array[Byte] for image column
  def return_date: Rep[Option[String]] = column[Option[String]]("return_date") // Use Array[Byte] for image column
  def return_hour: Rep[Option[String]] = column[Option[String]]("return_hour") // Use Array[Byte] for image column
  def return_place: Rep[Option[String]] = column[Option[String]]("return_place") // Use Array[Byte] for image column
  def pickup_date: Rep[Option[String]] = column[Option[String]]("pickup_date") // Use Array[Byte] for image column
  def pickup_time: Rep[Option[String]] = column[Option[String]]("pickup_time") // Use Array[Byte] for image column
  def pickup_place: Rep[Option[String]] = column[Option[String]]("pickup_place") // Use Array[Byte] for image column


  def * : ProvenShape[Subscription] = (id, subscriber_name, email, kilometrage, assurance, options, start_date, deleted_at, active, loueur_id, status,
    return_date, return_hour, return_place, pickup_date, pickup_time, pickup_place).<>(Subscription.tupled, Subscription.unapply)
}