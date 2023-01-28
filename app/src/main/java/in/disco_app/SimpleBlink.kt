package `in`.disco_app

import `in`.disco_app.MainActivity.Companion.cameraId
import `in`.disco_app.MainActivity.Companion.cameraManager
import android.hardware.camera2.CameraAccessException
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout

class SimpleBlink : Fragment() {

    var simpleTourch_ll: LinearLayout? = null
    var oneSecDelay_ll: LinearLayout? = null
    var mediumBlink_ll: LinearLayout? = null
    var fastBlink_ll: LinearLayout? = null
    var veryFastBlink_ll: LinearLayout? = null
    var ultraFastBlink_ll: LinearLayout? = null

    var light = true
    var activeBlink=false
    var loopingLight=true
    var threadObj: Thread?= null
    var threadSleep=  Thread.sleep(1000)

    var tourchOff_st: ImageView?=null
    var tourchOn_st: ImageView?=null
    var tourchOff_smt: ImageView?=null
    var tourchOn_smt: ImageView?=null
    var tourchOff_mmt: ImageView?=null
    var tourchOn_mmt: ImageView?=null
    var tourchOff_fmt: ImageView?=null
    var tourchOn_fmt: ImageView?=null
    var tourchOff_vfmt: ImageView?=null
    var tourchOn_vfmt: ImageView?=null
    var tourchOff_ufmt: ImageView?=null
    var tourchOn_ufmt: ImageView?=null

    var activeFirstBlink=false
    var activeSecondBlink=false
    var activeThirdBlink= false
    var activeForthBlink= false
    var activeFifthBlink= false

    var views : View? = null
    var  contextMain :MainActivity?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       views=inflater.inflate(R.layout.fragment_simple_blink, container, false)

        simpleTourch_ll=  views?.findViewById(R.id.simpleTourch_ll) as LinearLayout
        oneSecDelay_ll=   views?.findViewById(R.id.oneSecDelay_ll) as LinearLayout
        mediumBlink_ll=   views?.findViewById(R.id.mediumBlink_ll) as LinearLayout
        fastBlink_ll=     views?.findViewById(R.id.fastBlink_ll) as LinearLayout
        veryFastBlink_ll= views?.findViewById(R.id.veryFastBlink_ll) as LinearLayout
        ultraFastBlink_ll=views?.findViewById(R.id.ultraFastBlink_ll) as LinearLayout

        tourchOff_st=  views?.findViewById(R.id.tourchOff_st) as ImageView
        tourchOn_st=   views?.findViewById(R.id.tourchOn_st) as ImageView
        tourchOff_smt= views?.findViewById(R.id.tourchOff_smt) as ImageView
        tourchOn_smt=  views?.findViewById(R.id.tourchOn_smt) as ImageView
        tourchOff_mmt= views?.findViewById(R.id.tourchOff_mmt) as ImageView
        tourchOn_mmt=  views?.findViewById(R.id.tourchOn_mmt) as ImageView
        tourchOff_fmt= views?.findViewById(R.id.tourchOff_fmt) as ImageView
        tourchOn_fmt=  views?.findViewById(R.id.tourchOn_fmt) as ImageView
        tourchOff_vfmt=views?.findViewById(R.id.tourchOff_vfmt) as ImageView
        tourchOn_vfmt= views?.findViewById(R.id.tourchOn_vfmt) as ImageView
        tourchOff_ufmt=views?.findViewById(R.id.tourchOff_ufmt) as ImageView
        tourchOn_ufmt= views?.findViewById(R.id.tourchOn_ufmt) as ImageView

        contextMain = activity as MainActivity

        oneSecDelay_ll?.setOnClickListener {
            contextMain?.showInterstitial()
            setToDefault()
            activeSecondBlink=false
            activeThirdBlink= false
            activeForthBlink= false
            activeFifthBlink=false
            if(!activeFirstBlink){
                activeFirstBlink=true
                blinkFirst()
                tourchOff_smt?.visibility=View.GONE
                tourchOn_smt?.visibility=View.VISIBLE
            }
            else {
                activeFirstBlink = false
            }
        }

        mediumBlink_ll?.setOnClickListener {
            contextMain?.showInterstitial()
            activeFirstBlink=false
            activeThirdBlink= false
            activeForthBlink= false
            activeFifthBlink=false
            setToDefault()
            if(!activeSecondBlink){activeSecondBlink=true
                blinkSecond()
                tourchOff_mmt?.visibility=View.GONE
                tourchOn_mmt?.visibility=View.VISIBLE
            }
            else activeSecondBlink=false
        }

        fastBlink_ll?.setOnClickListener {
            contextMain?.showInterstitial()
            activeFirstBlink=false
            activeSecondBlink=false
            activeForthBlink= false
            activeFifthBlink=false
            setToDefault()
            if(!activeThirdBlink){activeThirdBlink=true
                blinkThird()
                tourchOff_fmt?.visibility=View.GONE
                tourchOn_fmt?.visibility=View.VISIBLE
            }
            else activeThirdBlink=false
        }

        veryFastBlink_ll?.setOnClickListener {
            contextMain?.showInterstitial()
            activeFirstBlink=false
            activeThirdBlink= false
            activeSecondBlink=false
            activeFifthBlink=false
            setToDefault()
            if(!activeForthBlink){activeForthBlink=true
                 blinkTForth()
                tourchOff_vfmt?.visibility=View.GONE
                tourchOn_vfmt?.visibility=View.VISIBLE
            }
            else activeForthBlink=false
        }

        ultraFastBlink_ll?.setOnClickListener {
            contextMain?.showInterstitial()
            activeFirstBlink=false
            activeThirdBlink= false
            activeSecondBlink=false
            activeForthBlink=false
            setToDefault()
            if(!activeFifthBlink){activeFifthBlink=true
                blinkTFifth()
                tourchOff_ufmt?.visibility=View.GONE
                tourchOn_ufmt?.visibility=View.VISIBLE
            }
            else activeFifthBlink=false
        }

        return views
    }

    fun setToDefault()
    {
        //  if(threadObj!=null)threadObj?.interrupt()

        loopingLight=true

        tourchOff_smt?.visibility=View.VISIBLE
        tourchOff_st?.visibility=View.VISIBLE
        tourchOff_mmt?.visibility=View.VISIBLE
        tourchOff_mmt?.visibility=View.VISIBLE
        tourchOff_fmt?.visibility=View.VISIBLE
        tourchOff_vfmt?.visibility=View.VISIBLE
        tourchOff_ufmt?.visibility=View.VISIBLE

        tourchOn_st?.visibility=View.GONE
        tourchOn_smt?.visibility=View.GONE
        tourchOn_mmt?.visibility=View.GONE
        tourchOn_fmt?.visibility=View.GONE
        tourchOn_vfmt?.visibility=View.GONE
        tourchOn_ufmt?.visibility=View.GONE
    }

    fun blinkFirst()
    {

        if(!activeFirstBlink)
        {
            loopingLight=false
            try {
                cameraManager?.setTorchMode(cameraId!!, false)
            } catch (e: CameraAccessException) {
                e.printStackTrace()
                return
            }
            return
        }
        loopingLight = if (loopingLight) {
            try {
                cameraManager?.setTorchMode(cameraId!!, true)
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }
            false
        }
        else {
            try {
                cameraManager?.setTorchMode(cameraId!!, false)
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }
            true
        }
        Handler(Looper.getMainLooper()).postDelayed({
            blinkFirst()
        }, 1000)

    }

    fun blinkSecond()
    {

        if(!activeSecondBlink)
        {
            loopingLight=false
            try {
                cameraManager?.setTorchMode(cameraId!!, false)
            } catch (e: CameraAccessException) {
                e.printStackTrace()
                return
            }
            return
        }
        loopingLight = if (loopingLight) {
            try {
                cameraManager?.setTorchMode(cameraId!!, true)
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }
            false
        }
        else {
            try {
                cameraManager?.setTorchMode(cameraId!!, false)
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }
            true
        }
        Handler(Looper.getMainLooper()).postDelayed({
            blinkSecond()
        }, 500)

    }

    fun blinkThird()
    {

        if(!activeThirdBlink)
        {
            loopingLight=false
            try {
                cameraManager?.setTorchMode(cameraId!!, false)
            } catch (e: CameraAccessException) {
                e.printStackTrace()
                return
            }
            return
        }
        loopingLight = if (loopingLight) {
            try {
                cameraManager?.setTorchMode(cameraId!!, true)
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }
            false
        }
        else {
            try {
                cameraManager?.setTorchMode(cameraId!!, false)
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }
            true
        }
        Handler(Looper.getMainLooper()).postDelayed({
            blinkThird()
        }, 200)

    }

    fun blinkTForth()
    {

        if(!activeForthBlink)
        {
            loopingLight=false
            try {
                cameraManager?.setTorchMode(cameraId!!, false)
            } catch (e: CameraAccessException) {
                e.printStackTrace()
                return
            }
            return
        }
        loopingLight = if (loopingLight) {
            try {
                cameraManager?.setTorchMode(cameraId!!, true)
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }
            false
        }
        else {
            try {
                cameraManager?.setTorchMode(cameraId!!, false)
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }
            true
        }
        Handler(Looper.getMainLooper()).postDelayed({
            blinkTForth()
        }, 100)

    }

    fun blinkTFifth()
    {

        if(!activeFifthBlink)
        {
            loopingLight=false
            try {
                cameraManager?.setTorchMode(cameraId!!, false)
            } catch (e: CameraAccessException) {
                e.printStackTrace()
                return
            }
            return
        }
        loopingLight = if (loopingLight) {
            try {
                cameraManager?.setTorchMode(cameraId!!, true)
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }
            false
        }
        else {
            try {
                cameraManager?.setTorchMode(cameraId!!, false)
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }
            true
        }
        Handler(Looper.getMainLooper()).postDelayed({
            blinkTFifth()
        }, 50)

    }

    override fun onDestroy() {
        super.onDestroy()
        activeFirstBlink=false
        activeSecondBlink=false
        activeThirdBlink= false
        activeForthBlink= false
        activeFifthBlink= false
    }


}