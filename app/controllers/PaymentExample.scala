import com.stripe.Stripe
import com.stripe.model.PaymentIntent
import com.stripe.net.RequestOptions
import com.stripe.param.PaymentIntentCreateParams

class PaymentExample  {
  // Set your Stripe secret key
  val secretKey = "sk_test_SUdSPInh20L4EOlEExO0lWKs00JD9VKtzO"

  // Set the Stripe account ID
  val stripeAccountId = "acct_1OQTVsDEGDDJ5J5n"

  // Set the payment method ID or card details
  val paymentMethodId = "pm_card_visa" // Replace with the actual payment method ID or card details

  // Set the amount and currency
  val amount = 2000
  val currency = "usd"

  // Create a PaymentIntent
  val paymentIntentParams = PaymentIntentCreateParams
    .builder()
    .setAmount(amount)
    .setCurrency(currency)
    .setPaymentMethod(paymentMethodId)
    .setConfirm(true)
    .build()

  // Set the Stripe-Account header
  val requestOptions = RequestOptions.builder().setStripeAccount(stripeAccountId).build()

  // Make the API request to create the PaymentIntent
  Stripe.apiKey = secretKey
  val paymentIntent = PaymentIntent.create(paymentIntentParams, requestOptions)

  // Print the PaymentIntent ID
  println(s"PaymentIntent ID: ${paymentIntent.getId}")
}
