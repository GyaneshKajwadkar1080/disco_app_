package `in`.disco_app

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback


class MainActivity : AppCompatActivity() {

    var adView:AdView?=null

    companion object {
        var mInterstitialAd: InterstitialAd? = null
        var showAds=true
        var cameraManager: CameraManager? = null
        var cameraId: String? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adView=findViewById(R.id.adView) as AdView

        MobileAds.initialize(this) { }
        var adRequest = AdRequest.Builder().build()

        setFragment(1)

        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            cameraId = cameraManager!!.cameraIdList[0]
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }

        adView?.loadAd(adRequest)

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        val hasFlash = this.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)

        if(hasFlash==false)
        {
            noFlash()
            showInterstitial()
        }
    }

     fun showInterstitial() {

         if(showAds==false) {
             return
         }
         var adRequest = AdRequest.Builder().build()
           InterstitialAd.load(this,"ca-app-pub-9145347634785014/6719196602", adRequest, object : InterstitialAdLoadCallback() {
       //  InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
             override fun onAdFailedToLoad(adError: LoadAdError) {
                 mInterstitialAd = null

             }
             override fun onAdLoaded(interstitialAd: InterstitialAd) {
                 mInterstitialAd = interstitialAd

             }
         })

         mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
             override fun onAdClicked() {
             }
             override fun onAdDismissedFullScreenContent() {
             }
             override fun onAdImpression() {
             }
             override fun onAdShowedFullScreenContent() {
             }
         }

        if (mInterstitialAd != null) {
            showAds=false
            mInterstitialAd?.show(this)
            invokeTimer()
        }
        else{

        }
    }

    fun invokeTimer()
    {
        Handler(Looper.getMainLooper()).postDelayed({
            showAds=true
        },30000)
    }


    fun removeAllview()
    {
      /*  val fragmentLayout=findViewById(R.id.frameLayout) as FrameLayout
        fragmentLayout.removeAllViews();*/
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentByTag("home")
        if (fragment is MainFragment){
            super.onBackPressed()
        }
        else{
           // removeAllview()

            setFragment(1)
        }
    }

    fun setFragment( type : Int)
    {

        val fragmentManger= supportFragmentManager
        val fragmentTrans= fragmentManger.beginTransaction()
        var fragment :Fragment?=null


        if(type==1) {
            fragment= MainFragment()
            fragmentTrans.replace(R.id.frameLayout, fragment, "home")
        }
            else if(type==2){
            fragment= SimpleBlink()
            fragmentTrans.replace(R.id.frameLayout, fragment, "simple")
        }
                else
        {
            fragment= PatternBlink()
            fragmentTrans.replace(R.id.frameLayout, fragment, "pattern")
        }
        fragmentTrans?.commit()

    }

    fun noFlash()
    {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Oops...")
        builder.setMessage("The device has no flash.")
        builder.setIcon(R.drawable.torch_on)

        builder.setPositiveButton("Ok"){dialogInterface, which ->
            super.finish();
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }


}