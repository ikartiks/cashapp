package com.kartik.grevocab.bootstrap

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

/**
* Generates the TestApp Application class when running tests, called internally when u specify testInstrumentationRunner "com.kartik.grevocab.bootstrap.KoinTestRunner" in build.gradle
*
*/
class KoinTestRunner : AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, TestApp::class.java.name, context)
    }
}
