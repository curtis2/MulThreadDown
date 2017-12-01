package com.simon.bigfiledownload;

import org.litepal.LitePalApplication;

/**
 * auther: Simon zhang
 * Emaill:18292967668@163.com
 */

public class Application extends LitePalApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandleUtil.getInstance().init(this,"crash");
    }

}
