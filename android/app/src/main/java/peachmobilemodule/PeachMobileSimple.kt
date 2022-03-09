package peachmobilemodule

import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.oppwa.mobile.connect.payment.PaymentParams
import com.oppwa.mobile.connect.payment.card.CardPaymentParams

class PeachMobileSimple(context: ReactApplicationContext) : ReactContextBaseJavaModule() {

    @ReactMethod
    fun createPaymentParams(
        checkoutId: String,
        cardHolder: String,
        cardNumber: String,
        cardExpiryMonth: String,
        cardExpiryYear: String,
        cardCVV: String,
        promise: Promise

    ): PaymentParams {


        // promise.resolve(checkoutId)

        return CardPaymentParams(
            checkoutId,
            Constants.Config.CARD_BRAND,
            cardNumber,
            cardHolder,
            cardExpiryMonth,
            "20$cardExpiryYear",
            cardCVV
        );

    }



    override fun getName(): String {
        return "PeachMobileSimple"
    }

}