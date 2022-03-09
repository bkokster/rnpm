package peachmobilemodule

import android.view.View
import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ReactShadowNode
import com.facebook.react.uimanager.ViewManager
import java.util.*
import kotlin.collections.ArrayList

class PeachPackage : ReactPackage {

    override fun createViewManagers(reactContext: ReactApplicationContext)
            : MutableList<ViewManager<*, *>>
    {
        return Collections.emptyList()
    }

    override fun createNativeModules(reactContext: ReactApplicationContext): MutableList<NativeModule> {
        val nativeModules = ArrayList<NativeModule>()
        nativeModules.add(PeachMobile(reactContext))
        nativeModules.add(PeachMobileSimple(reactContext))

        return nativeModules
    }
}