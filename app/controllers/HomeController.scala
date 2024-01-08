package controllers

import models._
import play.api._
import play.api.libs.json._
import play.api.mvc._

import java.util.UUID
import javax.inject.Inject
import scala.collection.mutable
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.

 */


class HomeController @Inject()(adminRepository: AdminRepositoryImpl,
                               produitRepository: ProductRepositoryImpl,
                               subscriptionRepository:SubscriptionRepositoryImpl ,
                               bankAccountRepository:BankAccountRepositoryImpl ,
                               val controllerComponents: ControllerComponents)
  extends BaseController {

  implicit val todoListJson = Json.format[TodoListItem]

  private val todoList = new mutable.ListBuffer[TodoListItem]()
  todoList += TodoListItem(1, "test", true)
  todoList += TodoListItem(2, "some other value", false)


  def getAll(itemId: Long) = Action {
    val foundItem = todoList.find(_.id == itemId)
    foundItem match {
      case Some(item) => Ok(Json.toJson(item))
      case None => NotFound
    }
  }

  def auth(username: String, password: String) = Action.async { implicit request =>
    adminRepository.findByUsernameAndPassword(username, password).map {
      case Some(admin) => Ok(Json.toJson(admin))
      case None => NotFound
    }
  }

  def uuidToLong(uuid: UUID): Long = {
    val mostSigBits = uuid.getMostSignificantBits
    mostSigBits
  }

  def insertProduct: Action[ProductFormData] = Action.async(parse.form(ProductFormData.form)) { implicit request =>
    println("product.toString")

    val formData = request.body
    val newProduct = Product(id = uuidToLong(UUID.randomUUID()).toInt,
      productName = formData.productName,
      description = formData.description,
      imagestring = formData.imagestring,
      price=formData.price.toInt,
      loueurid = formData.loueurid,
      deletedAt = None,
      imagebytea = formData.imagebytea)

    produitRepository.insertProduct(newProduct).map { _ =>
      Ok("Product inserted successfully!")
    }.recover {
      case ex: Exception =>
        InternalServerError(s"Failed to insert product: ${ex.getMessage}")
    }
  }

  def logout = Action.async { implicit request =>
    Future.successful(Ok("Logged out").withNewSession)
  }

  implicit val adminWrite: Writes[Admin] = new Writes[Admin] {
    def writes(admin: Admin): JsValue = Json.obj(
      "id" -> admin.id,
      "username" -> admin.username,
      "email" -> admin.email
    )
  }


  implicit val subWrites: Writes[Subscription] = new Writes[Subscription] {
    def writes(subscription: Subscription): JsValue = Json.obj(
      "id" -> subscription.id,
      "name" -> subscription.subscriber_name,
      "kilometrage" -> subscription.kilometrage,
      "assurance" -> subscription.Assurance,
      "options" -> subscription.Options,
      "status" -> subscription.status,
      "pickup_place" -> subscription.pickup_place,
      "return_place" -> subscription.return_place,
    )
  }

  def insertRent: Action[SubscriptionFormData] = Action.async(parse.form(SubscriptionFormData.form)) { implicit request =>
    println("rent.toString")
    val iod = (UUID.randomUUID()).toString
    println(iod)
    val formData = request.body
    val newRent = Subscription(id = iod,
      email = formData.email,
      subscriber_name = formData.subscriber_name,
      kilometrage = formData.kilometrage,
      Assurance = formData.Assurance,
      Options = formData.Options,
      active = formData.active,
      start_date = Some(formData.start_date),
      deleted_at = Some(formData.start_date),
      loueur_id = formData.loueur_id,
      status = Some("UNPAID"),
      return_date = None,
      return_hour = None,
      return_place = None,
      pickup_date = None,
      pickup_time = None,
      pickup_place = None)

    subscriptionRepository.insertRent(newRent).map { _ =>
      Ok(iod)
    }.recover {
      case ex: Exception =>
        InternalServerError(s"Failed to insert rent: ${ex.getMessage}")
    }
  }

  def rentUpdate: Action[SubscriptionFormData] = Action.async(parse.form(SubscriptionFormData.form)) { implicit request =>
    println("rentUpdate.toString")

    val formData = request.body
    val newRent = Subscription(id = formData.id,
      email = formData.email,
      subscriber_name = formData.subscriber_name,
      kilometrage = formData.kilometrage,
      Assurance = formData.Assurance,
      Options = formData.Options,
      active = true,
      start_date = Some(formData.start_date),
      deleted_at = Some(formData.start_date),
      loueur_id = formData.loueur_id,
      status = None,
      return_date = Some(formData.return_date),
      return_hour = Some(formData.return_hour),
      return_place = Some(formData.return_place),
      pickup_date = Some(formData.pickup_date),
      pickup_time = Some(formData.pickup_time),
      pickup_place = Some(formData.pickup_place))

    subscriptionRepository.updateRent(newRent).map { _ =>
      Ok("updated")
    }.recover {
      case ex: Exception =>
        InternalServerError(s"Failed to updated rent: ${ex.getMessage}")
    }
  }


  implicit val produitWrites: Writes[Product] = new Writes[Product] {
    def writes(produit: Product): JsValue = Json.obj(
      "id" -> produit.id,
      "productName" -> produit.productName,
      "description" -> produit.description,
      "price" -> produit.price,
      "deletedAt" -> produit.deletedAt,
      "image" -> produit.imagestring,
      "loueurid" -> produit.loueurid
    )
  }

  def insertBankAccount: Action[BankAccountFormData] = Action.async(parse.form(BankAccountFormData.form)) { implicit request =>

    val formData = request.body
    println("--" +formData.id_agence)
    println("--" +formData.id_account)

    val newBankAccount = BankAccount(id= uuidToLong(UUID.randomUUID()).toInt,
      id_account = formData.id_account,
      id_agence = formData.id_agence
    )

    bankAccountRepository.insertAccount(newBankAccount).map { _ =>
      Ok("bank account inserted successfully!")
    }.recover {
      case ex: Exception =>
        InternalServerError(s"Failed to insert bank account: ${ex.getMessage}")
    }
  }


  implicit val accounttWrites: Writes[BankAccount] = new Writes[BankAccount] {
    def writes(bankAccount: BankAccount): JsValue = Json.obj(
      "idAgence" -> bankAccount.id_agence,
      "idAccount" -> bankAccount.id_account
    )
  }

  def product(id: String) = Action.async { implicit request =>
    produitRepository.findByProduct(id).map {
      case listProduct => Ok(Json.toJson(listProduct))
      case _ => NotFound
    }
  }

  def getAllRent(id: String) = Action.async { implicit request =>
    subscriptionRepository.findByLouerid(id.toInt).map {
      case listRent => {println("size + "+listRent.size )
        Ok(Json.toJson(listRent))}
      case _ => NotFound
    }
  }

  def productById(id: String) = Action.async { implicit request =>
    produitRepository.findByProductById(id.toInt).map {
      case listProduct => Ok(Json.toJson(listProduct))
      case _ => NotFound
    }
  }
}