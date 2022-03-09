package peachmobilemodule

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import com.facebook.react.bridge.*
import com.oppwa.mobile.connect.exception.PaymentError
import com.oppwa.mobile.connect.exception.PaymentException
import com.oppwa.mobile.connect.payment.CheckoutInfo
import com.oppwa.mobile.connect.payment.PaymentParams
import com.oppwa.mobile.connect.payment.card.CardPaymentParams
import com.oppwa.mobile.connect.provider.Connect
import com.oppwa.mobile.connect.provider.Connect.ProviderMode
import com.oppwa.mobile.connect.provider.ITransactionListener
import com.oppwa.mobile.connect.provider.Transaction
import com.oppwa.mobile.connect.provider.TransactionType
import com.oppwa.mobile.connect.service.ConnectService
import com.oppwa.mobile.connect.service.IProviderBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


class PeachMobile(context: ReactApplicationContext) : ReactContextBaseJavaModule(),ServiceConnection,ITransactionListener {

    private lateinit var checkoutId: String
    private var providerBinder: IProviderBinder? = null
    protected var resourcePath: String? = null

    override fun getName(): String {
        return "PeachMobile"
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            /* we have a connection to the service */
            providerBinder = service as IProviderBinder
            providerBinder!!.addTransactionListener(this@PeachMobile)

            try {
                providerBinder!!.initializeProvider(ProviderMode.TEST)
            } catch (ee: PaymentException) {
                Log.e("PaymentException", ee.message!!)
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            providerBinder = null
        }
    }

//    @ExperimentalCoroutinesApi
//    fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        setContentView(R.layout.activity_custom_ui)
//
//        initViews()
//    }

    @ReactMethod
    fun initPaymentProvider(mode: String) {
        if (providerBinder != null) {
            try {
                if (mode == "live") {
                    providerBinder!!.initializeProvider(ProviderMode.LIVE)
                    Log.i("Provider Initialized", "Initialized provider in Live mode.")
                } else {
                    providerBinder!!.initializeProvider(ProviderMode.TEST)
                    Log.i("Provider Initialized", "Initialized provider in Test mode.")
                }
            } catch (error: PaymentException) {
                Log.e("PaymentException", "Failed to initialize payment provider")
            }
        } else {
            Log.e("PaymentException", "Failed to initialize payment provider. Binder is undefined")
        }
    }

    @ReactMethod
    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        /* we have a connection to the service */
        providerBinder = service as IProviderBinder
        providerBinder!!.addTransactionListener(this@PeachMobile)
        Log.i("Service Connected", "Service is Connected")

        try {
            providerBinder!!.initializeProvider(ProviderMode.TEST)
        } catch (ee: PaymentException) {
            Log.e("PaymentException", ee.message!!)
        }
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        providerBinder = null
    }


//    override fun onServiceConnected(componentName: ComponentName?, service: IBinder) {
//        binder = service as IProviderBinder
//    }
//
//    override fun onServiceDisconnected(componentName: ComponentName?) {
//        binder = null
//    }
//

  init {

//      context.addActivityEventListener(this@PeachMobile)
      val intent = Intent(context, ConnectService::class.java)
      context.startService(intent)
      context.bindService(intent, serviceConnection, 0)

  }


//
//   override fun onStop() {
//       super.onStop()
//
//       unbindService(serviceConnection)
//       stopService(Intent(this, ConnectService::class.java))
//   }

//    override fun onCheckoutIdReceived(checkoutId: String?) {
//        super.onCheckoutIdReceived(checkoutId)
//
//        checkoutId?.let {
//            this.checkoutId = checkoutId
//            requestCheckoutInfo(checkoutId)
//        }
//    }

//    region ITransactionListener methods
    override fun paymentConfigRequestSucceeded(checkoutInfo: CheckoutInfo) {
        /* Get the resource path from checkout info to request the payment status later */
        resourcePath = checkoutInfo.resourcePath

//        runOnUiThread {
//            showConfirmationDialog(checkoutInfo.amount.toString(),
//                    checkoutInfo.currencyCode!!
//            )
//        }
    }

    override fun paymentConfigRequestFailed(paymentError: PaymentError) {
        Log.e("PaymentException", paymentError.toString())

    }

//    @ExperimentalCoroutinesApi
    override fun transactionCompleted(transaction: Transaction) {
        if (transaction.transactionType == TransactionType.SYNC) {
            /* check the status of synchronous transaction */
            CoroutineScope(Dispatchers.Main).launch {
                val paymentStatus = RequestManager().requestPaymentStatus(resourcePath!!)
                Log.e("AsyncPaymentRedirect", "Payment Status Placeholder to be updated")
            }
        } else {
            /* wait fot the callback in the onNewIntent() */
            Log.e("AsyncPaymentRedirect", transaction.redirectUrl.toString())
        }
    }

    override fun transactionFailed(transaction: Transaction, paymentError: PaymentError) {

        Log.d("TransactionFailure",paymentError.toString())
    }
    //endregion

//    @ExperimentalCoroutinesApi
//    private fun initViews() {
//        holder_edit_text.setText(Constants.Config.CARD_HOLDER_NAME)
//        number_edit_text.setText(Constants.Config.CARD_NUMBER)
//        expiry_month_edit_text.setText(Constants.Config.CARD_EXPIRY_MONTH)
//        expiry_year_edit_text.setText(Constants.Config.CARD_EXPIRY_YEAR)
//        cvv_edit_text.setText(Constants.Config.CARD_CVV)
//        progressBar = progress_bar_custom_ui
//
//        button_pay_now.setOnClickListener {
//            if (providerBinder != null && checkFields()) {
//                requestCheckoutId()
//            }
//        }
//    }

//    private fun checkFields(): Boolean {
//        if (holder_edit_text.text.isEmpty() ||
//                number_edit_text.text.isEmpty() ||
//                expiry_month_edit_text.text.isEmpty() ||
//                expiry_year_edit_text.text.isEmpty() ||
//                cvv_edit_text.text.isEmpty()) {
//            showAlertDialog(R.string.error_empty_fields)
//
//            return false
//        }
//
//        return true
//    }

//    private fun requestCheckoutInfo(checkoutId: String) {
//        try {
//            providerBinder!!.requestCheckoutInfo(checkoutId)
//        } catch (e: PaymentException) {
//            e.message?.let { showAlertDialog(it) }
//        }
//    }

    @ReactMethod
    fun pay(checkoutId: String) {
        try {
            val paymentParams = createPaymentParams(checkoutId)
//            paymentParams.shopperResultUrl = getString(R.string.custom_ui_callback_scheme) + "://callback"
            val transaction = Transaction(paymentParams)

            providerBinder!!.submitTransaction(transaction)
        } catch (e: PaymentException) {
            Log.e("PaymentException", e.error.toString())

        }
    }

    @ReactMethod
    fun payFull(checkoutId: String,
                cardHolder: String = "",
                cardNumber: String = "",
                cardExpiryMonth: String = "",
                cardExpiryYear: String = "",
                cardCVV: String = "",
                deviceId: String = "",
                did: String = "",
                shopperResultUrl: String = ""

                ) {
        try {
            val paymentParams = CardPaymentParams(
                checkoutId,
                Constants.Config.CARD_BRAND,
                cardNumber,
                cardHolder,
                cardExpiryMonth,
                "20$cardExpiryYear",
                cardCVV)
            paymentParams.shopperResultUrl = shopperResultUrl
            paymentParams.addParam("customer.app.deviceId",deviceId)
            paymentParams.addParam("customer.middleName",did)

//            paymentParams.addParam("customParameters","SHOPPER_customerId")
//            paymentParams.addParam("customParameters.SHOPPER_customerId","SHOPPER_customerId")

//            paymentParams.addParam("customer.identificationDocType","IDCARD")
//            paymentParams.addParam("customer.identificationDocId","did:ethr:0xf3beac30c498d9e26865f34fcaa57dbb935b0d74")


            val transaction = Transaction(paymentParams)

            providerBinder!!.submitTransaction(transaction)
        } catch (e: PaymentException) {
            Log.e("PaymentException", e.error.toString())

        }
    }

    @ReactMethod
    fun createPaymentParams(checkoutId: String,
    cardHolder: String = "",
    cardNumber: String = "",
    cardExpiryMonth: String = "",
    cardExpiryYear: String = "",
    cardCVV: String = ""

    ) : PaymentParams {

        return CardPaymentParams(
                checkoutId,
                Constants.Config.CARD_BRAND,
                cardNumber,
                cardHolder,
                cardExpiryMonth,
                "20$cardExpiryYear",
                cardCVV)

    }

    @ReactMethod
    fun firstcheck(a: String, promise: Promise
    
    ) {
       promise.resolve(a)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @ReactMethod
    fun getCurrentTime(promise: Promise) {
        val date = ZonedDateTime.now( ZoneOffset.UTC ).format( DateTimeFormatter.ISO_INSTANT )
        promise.resolve(date)
    }

//    private fun showConfirmationDialog(amount: String, currency: String) {
//        AlertDialog.Builder(this)
//                .setMessage(String.format(getString(R.string.message_payment_confirmation), amount, currency))
//                .setPositiveButton(R.string.button_ok) { _, _ -> pay(checkoutId) }
//                .setNegativeButton(R.string.button_cancel) { _, _ -> hideProgressBar()}
//                .setCancelable(false)
//                .show()
//    }

//    private fun showErrorDialog(message: String) {
//        runOnUiThread { showAlertDialog(message) }
//    }

//    private fun showErrorDialog(paymentError: PaymentError) {
//        runOnUiThread { showErrorDialog(paymentError.errorMessage) }
//    }

//    protected var progressBar: ProgressBar? = null
//
//    protected fun showProgressBar() {
//        progressBar?.visibility = View.VISIBLE
//    }
//
//    protected fun hideProgressBar() {
//        progressBar?.visibility = View.INVISIBLE
//    }

//   protected fun showAlertDialog(message: String) {
//       AlertDialog.Builder(this)
//               .setMessage(message)
//               .setPositiveButton(R.string.button_ok, null)
//               .setCancelable(false)
//               .show()
//   }

//   protected fun showAlertDialog(messageId: Int) {
//       showAlertDialog(getString(messageId))
//   }

}