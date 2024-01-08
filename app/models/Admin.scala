package models

import java.time.OffsetDateTime

case class Admin(id: Int, username: String, password: String, email: String, deleted_at: Option[OffsetDateTime])