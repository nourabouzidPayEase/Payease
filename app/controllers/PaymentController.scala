package controllers

import com.stripe.Stripe
import com.stripe.exception.StripeException
import com.stripe.model.PaymentIntent
import com.stripe.net.RequestOptions
import com.stripe.param.PaymentIntentCreateParams
import models.SubscriptionRepositoryImpl
import play.api.libs.json._
import play.api.mvc._

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class PaymentController @Inject()
(cc: ControllerComponents,
  subscriptionRepository:SubscriptionRepositoryImpl
)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  // Initialize Stripe with your Secret Key

  /**
   * nourabouzid@MacBook-Pro-de-Noura ~ % curl https://connect.stripe.com/oauth/token \
   * -u sk_test_SUdSPInh20L4EOlEExO0lWKs00JD9VKtzO: \
   * -d "code"="ac_PF0f5HOug25ldlrtd0Zx3PNXJ4AtyAEu" \
   * -d "grant_type"="authorization_code"
   *
   * {
   * "access_token": "sk_test_51OQTVsDEGDDJ5J5n8eRT5GF0wq9zCYQxQXwBrH40G81kUXkstKioItQKVLBjieUTlj3ukUn381ZD8wMA7tH9dtQb00pdBkVHp7",
   * "livemode": false,
   * "refresh_token": "rt_PF0fjbkMsLrHPkFyb5rMUPqvbLdLODsi3pJKqCxw8Zl86TLo",
   * "token_type": "bearer",
   * "stripe_publishable_key": "pk_test_51OQTVsDEGDDJ5J5nvXCaaa7USZNW5Ys6e32SYzXgodGMmNrYsBRSRYXsOdaq3vtfExmM2gBElfpqJ58tyDYBHoEm00tqGUW5m3",
   * "stripe_user_id": "acct_1OQTVsDEGDDJ5J5n",
   * "scope": "read_write"
   * }%
   * */

  Stripe.apiKey = "sk_test_SUdSPInh20L4EOlEExO0lWKs00JD9VKtzO"

  def createPaymentTest: Action[JsValue] = Action.async(parse.json) { implicit request =>
    println("request.body " + request.body)
    val stripeAccountId = "acct_1OQTVsDEGDDJ5J5n"

    val paymentAmount = (request.body \ "amount").as[Int]
    val pmCardVisa = (request.body \ "pm_card_visa").as[String]
    val rentId = (request.body \ "rent").as[String]
    val loueurId = (request.body \ "loueurId").as[String]


    val paymentIntentParams = PaymentIntentCreateParams
      .builder()
      .setAmount(paymentAmount)
      .setCurrency("usd")
      .setPaymentMethod(pmCardVisa)
      .setConfirm(true)
      .build()

    val requestOptions = RequestOptions.builder().setStripeAccount(stripeAccountId).build()

    Stripe.apiKey = "sk_test_SUdSPInh20L4EOlEExO0lWKs00JD9VKtzO"

    val paymentIntent = PaymentIntent.create(paymentIntentParams, requestOptions)
    try {
      for {
        sub <- subscriptionRepository.findBySub(rentId)
        subupdate = sub.head.copy(status =Some("PAID"))
        a <- subscriptionRepository.updateRent(subupdate)
      } yield {
        println("Sub updated")
      }

      println(s"PaymentIntent ID: ${paymentIntent.getId}")

      Future.successful(
        Ok(Json.obj("clientSecret" -> paymentIntent.getId))
      )

    } catch {
      case e: StripeException =>
        Future.successful(
          BadRequest(Json.obj("error" -> e.getMessage))
        )
    }

  }

  def createPaymentIntent: Action[JsValue] = Action.async(parse.json) { implicit request =>
    println("request.body " + request.body)
    val paymentAmount = (request.body \ "amount").as[Int]
    // Collect card details from the request JSON
    val cardNumber = (request.body \ "cardNumber").as[String]
    val cardExpMonth = (request.body \ "cardExpMonth").as[Int]
    val cardExpYear = (request.body \ "cardExpYear").as[Int]
    val cardCvc = (request.body \ "cardCvc").as[String]

    // Create a PaymentIntent using the Stripe Secret Key and collected card details
    val paymentIntentParams: java.util.Map[String, Object] = new java.util.HashMap()
    paymentIntentParams.put("amount", paymentAmount.toString)
    paymentIntentParams.put("currency", "eur")
    paymentIntentParams.put("payment_method_types", java.util.Arrays.asList("card"))

    val cardParams: java.util.Map[String, Object] = new java.util.HashMap()
    cardParams.put("number", cardNumber)
    cardParams.put("exp_month", cardExpMonth.asInstanceOf[Object])
    cardParams.put("exp_year", cardExpYear.asInstanceOf[Object])
    cardParams.put("cvc", cardCvc)


    val paymentMethodData: java.util.Map[String, Object] = new java.util.HashMap()
    paymentMethodData.put("type", "card")
    paymentMethodData.put("card", cardParams)

    paymentIntentParams.put("payment_method_data", paymentMethodData)
    //https://stripe.com/docs/testing#cards
    try {
      val paymentIntent = PaymentIntent.create(paymentIntentParams)
      val clientSecret = paymentIntent.getClientSecret

      Future.successful(
        Ok(Json.obj("clientSecret" -> clientSecret))
      )
    } catch {
      case e: StripeException =>
        Future.successful(
          BadRequest(Json.obj("error" -> e.getMessage))
        )
    }
  }
}