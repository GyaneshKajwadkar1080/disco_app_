package `in`.disco_app

import `in`.disco_app.MainActivity.Companion.cameraId
import `in`.disco_app.MainActivity.Companion.cameraManager
import android.hardware.camera2.CameraAccessException
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat


class MainFragment : Fragment() {

    var oneSecDelay_ll: LinearLayout? = null
    var mediumBlink_ll: LinearLayout? = null
    var light = false

    var simpleTourch_iv: ImageView?=null

    var activeFirstBlink=false
    var activeSecondBlink=false
    var activeThirdBlink= false

    var views : View? = null

    var  contextMain :MainActivity?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        views= inflater.inflate(R.layout.fragment_main2, container, false)

        oneSecDelay_ll=   views?.findViewById(R.id.oneSecDelay_ll) as LinearLayout
        mediumBlink_ll=   views?.findViewById(R.id.mediumBlink_ll) as LinearLayout
        simpleTourch_iv=   views?.findViewById(R.id.simpleTourch_iv) as ImageView

        contextMain = activity as MainActivity

        //Simple tourch
        simpleTourch_iv?.setOnClickListener {

            contextMain?.showInterstitial()

            activeFirstBlink=false
            activeSecondBlink=false
            activeThirdBlink= false
            light = if (light==false) {
                try {
                    cameraManager?.setTorchMode(cameraId!!, true)
                } catch (e: CameraAccessException) {
                    e.printStackTrace()
                }
                simpleTourch_iv?.setColorFilter(ContextCompat.getColor(activity as MainActivity, R.color.orange));

                true
            }
            else {
                try {
                    cameraManager?.setTorchMode(cameraId!!, false)
                } catch (e: CameraAccessException) {
                    e.printStackTrace()
                }
                simpleTourch_iv?.setColorFilter(ContextCompat.getColor(activity as MainActivity, R.color.white));

                false
            }
        }

        val context = activity as MainActivity

        oneSecDelay_ll?.setOnClickListener {

          if(light)
            {
                try {
                    cameraManager?.setTorchMode(cameraId!!, false)
                } catch (e: CameraAccessException) {
                    e.printStackTrace()
                }
            }

            simpleTourch_iv?.setColorFilter(ContextCompat.getColor(activity as MainActivity, R.color.white));


            context.removeAllview()

            context.setFragment(2)
        }

        mediumBlink_ll?.setOnClickListener {
            if(light)
            {
                try {
                    cameraManager?.setTorchMode(cameraId!!, false)
                } catch (e: CameraAccessException) {
                    e.printStackTrace()
                }
            }
            simpleTourch_iv?.setColorFilter(ContextCompat.getColor(activity as MainActivity, R.color.white));
            context.removeAllview()
            context.setFragment(3)
        }

        return views
    }


}