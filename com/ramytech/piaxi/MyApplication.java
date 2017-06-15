package com.ramytech.piaxi;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;
import org.acra.sender.HttpSender.Method;
import org.acra.sender.HttpSender.Type;

import android.app.Application;

@ReportsCrashes(
    formKey = "", // This is required for backward compatibility but not used
	httpMethod = Method.PUT,
    reportType = Type.JSON,
    formUri = "http://acra.ramytech.com/acra-piaxi/_design/acra-storage/_update/report",
    formUriBasicAuthLogin = "piaxi",
    formUriBasicAuthPassword = "xiaor",
	additionalSharedPreferences={"app","user"}
)

public class MyApplication extends Application {
	@Override
    public void onCreate() {
        super.onCreate();

        // The following line triggers the initialization of ACRA
        ACRA.init(this);
        
        ACRA.getErrorReporter().checkReportsOnApplicationStart();
        ACRA.getErrorReporter().putCustomData("appname", "piaxi");
    }	
}