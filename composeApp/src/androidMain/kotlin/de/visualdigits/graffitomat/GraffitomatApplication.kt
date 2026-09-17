package de.visualdigits.graffitomat

import android.app.Application
import co.touchlab.kermit.Logger
import de.visualdigits.graffitomat.di.platformModule
import de.visualdigits.graffitomat.di.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class GraffitomatApplication: Application() {

    override fun onCreate() {
        Logger.i("Starting koin...")
        startKoin {
            androidContext(this@GraffitomatApplication)
            modules(sharedModule, platformModule)
        }

        // IMPORTANT do super create AFTER koin initializing to avoid problems with work managers
        Logger.i("Initializing application...")
        super.onCreate()

        Logger.i("Application initialized")
    }
}
