package com.anti_captcha;

import com.anti_captcha.Api.CustomCaptcha;
import com.anti_captcha.Api.FunCaptcha;
import com.anti_captcha.Api.ImageToText;
import com.anti_captcha.Api.NoCaptcha;
import com.anti_captcha.Api.NoCaptchaProxyless;
import com.anti_captcha.Helper.DebugHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) throws InterruptedException, MalformedURLException, JSONException {
        exampleGetBalance();
        exampleNoCaptchaProxyless();
        //exampleNoCaptcha();
    }

    private static void exampleNoCaptchaProxyless() throws MalformedURLException, InterruptedException {
        DebugHelper.setVerboseMode(true);

        NoCaptchaProxyless api = new NoCaptchaProxyless();
        api.setClientKey("8c7b4e3d97b3deebd8c1d2ae4a0cf813");
        api.setWebsiteUrl(new URL("http://http.myjino.ru/recaptcha/test-get.php"));
        api.setWebsiteKey("6Lc_aCMTAAAAABx7u2W0WPXnVbI_v6ZdbM6rYf16");

        if (!api.createTask()) {
            DebugHelper.out(
                    "API v2 send failed. " + api.getErrorMessage(),
                    DebugHelper.Type.ERROR
            );
        } else if (!api.waitForResult()) {
            DebugHelper.out("Could not solve the captcha.", DebugHelper.Type.ERROR);
        } else {
            DebugHelper.out("Result: " + api.getTaskSolution().getGRecaptchaResponse(), DebugHelper.Type.SUCCESS);
        }
    }

    /*
    private static void exampleNoCaptcha() throws MalformedURLException, InterruptedException {
        DebugHelper.setVerboseMode(true);

        NoCaptcha api = new NoCaptcha();
        api.setClientKey("1234567890123456789012");
        api.setWebsiteUrl(new URL("http://http.myjino.ru/recaptcha/test-get.php"));
        api.setWebsiteKey("6Lc_aCMTAAAAABx7u2W0WPXnVbI_v6ZdbM6rYf16");
        api.setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/52.0.2743.116");

        // proxy access parameters
        api.setProxyType(NoCaptcha.ProxyTypeOption.HTTP);
        api.setProxyAddress("xx.xxx.xx.xx");
        api.setProxyPort(8282);
        api.setProxyLogin("login");
        api.setProxyPassword("password");

        if (!api.createTask()) {
            DebugHelper.out(
                    "API v2 send failed. " + api.getErrorMessage(),
                    DebugHelper.Type.ERROR
            );
        } else if (!api.waitForResult()) {
            DebugHelper.out("Could not solve the captcha.", DebugHelper.Type.ERROR);
        } else {
            DebugHelper.out("Result: " + api.getTaskSolution().getGRecaptchaResponse(), DebugHelper.Type.SUCCESS);
        }
    }
    */

    private static void exampleGetBalance() {
        DebugHelper.setVerboseMode(true);

        ImageToText api = new ImageToText();
        api.setClientKey("8c7b4e3d97b3deebd8c1d2ae4a0cf813");

        Double balance = api.getBalance();

        if (balance == null) {
            DebugHelper.out("GetBalance() failed. " + api.getErrorMessage(), DebugHelper.Type.ERROR);
        } else {
            DebugHelper.out("Balance: " + balance, DebugHelper.Type.SUCCESS);
        }
    }
}
