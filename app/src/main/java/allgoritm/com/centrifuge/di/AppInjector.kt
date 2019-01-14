package allgoritm.com.centrifuge.di

import allgoritm.com.centrifuge.CentrifugeApplication
import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.appcompat.app.AppCompatActivity

import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector

object AppInjector {

    private lateinit var component: AppComponent

    fun init(yApplication: CentrifugeApplication) {
        component = DaggerAppComponent.builder()
                .application(yApplication)
                .build()
        component
                .inject(yApplication)

        yApplication
                .registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
                    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                        handleActivity(activity)
                    }

                    override fun onActivityStarted(activity: Activity) {

                    }

                    override fun onActivityResumed(activity: Activity) {

                    }

                    override fun onActivityPaused(activity: Activity) {

                    }

                    override fun onActivityStopped(activity: Activity) {

                    }

                    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {

                    }

                    override fun onActivityDestroyed(activity: Activity) {

                    }
                })
    }

    private fun handleActivity(activity: Activity) {
        if (activity is HasSupportFragmentInjector) {
            AndroidInjection.inject(activity)
        }
        if ((activity is AppCompatActivity) && (activity is HasSupportFragmentInjector)) {
            activity.supportFragmentManager
                    .registerFragmentLifecycleCallbacks(
                            object : FragmentManager.FragmentLifecycleCallbacks() {
                                override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
                                    if (f is Injectable) {
                                        AndroidSupportInjection.inject(f)
                                    }
                                    super.onFragmentAttached(fm, f, context)
                                }
                            }, true)
        }
    }

}
