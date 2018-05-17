import com.anti_captcha.Api.NoCaptchaProxyless;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PostBot {

    private static final String captchaSolverKey = "8c7b4e3d97b3deebd8c1d2ae4a0cf813";

    public static void main(String[] args) {
        //adidasRegister();
        for(int i=0; i<5; i++) {
            System.out.println("Running submission...");
            runDevwebTest("Kieran", "Robertson"+i);
        }
    }

    /*
    private static void adidasRegister() {
        String devwebTest = "https://www.adidas.co.uk/on/demandware.store/Sites-adidas-GB-Site/en_GB/MyAccount-Register";

        ArrayList<String[]> data = new ArrayList<>();
        data.add(new String[]{"dwfrm_profile_register", "submit"});

        for(int i=0; i<5; i++) {
            postData(devwebTest, data);
            try {
                Thread.sleep(200);
            }catch (InterruptedException ex) { }
        }
    }
    */

    private static void runDevwebTest(String firstName, String secondName) {
        String devwebTest = "https://devweb2017.cis.strath.ac.uk/~rjb13182/bot.php";
        String captchaSiteKey = "6Lf66VkUAAAAAAmMrpVJ8YtRBEaKJArXytYwOOCH";

        ArrayList<String[]> data = new ArrayList<>();
        data.add(new String[]{"submit", ""});
        data.add(new String[]{"first_name", firstName});
        data.add(new String[]{"second_name", secondName});
        data.add(new String[]{"g-recaptcha-response", "CAPTCHA"});

        try {
            System.out.println("Attempting to solve captcha. Please wait...");
            data.add(new String[]{"g-recaptcha-response", getReCaptchaCode(devwebTest, captchaSiteKey)});
        }
        catch (MalformedURLException ex) { }
        catch (InterruptedException ex) { }

        postData(devwebTest, data);
    }

    private static String getReCaptchaCode(String url, String siteKey) throws MalformedURLException, InterruptedException {
        NoCaptchaProxyless api = new NoCaptchaProxyless();

        api.setClientKey(captchaSolverKey);
        api.setWebsiteUrl(new URL(url));
        api.setWebsiteKey(siteKey);

        if (!api.createTask()) {
            System.err.println("API v2 send failed. " + api.getErrorMessage());
            System.exit(1);
        } else if (!api.waitForResult()) {
            System.err.println("Could not solve the captcha.");
            System.exit(1);
        }

        return api.getTaskSolution().getGRecaptchaResponse();
    }

    private static void postData(String url, ArrayList<String[]> data) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        List <NameValuePair> nvps = new ArrayList<>();

        for (String[] postElement : data) {
            nvps.add(new BasicNameValuePair(postElement[0], postElement[1]));
        }

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        } catch (UnsupportedEncodingException ex) { }

        try {
            CloseableHttpResponse response2 = httpclient.execute(httpPost);

            try {
                System.out.println(response2.getStatusLine());
                HttpEntity entity2 = response2.getEntity();

                // Uncomment this to print out website source on submission
                //System.out.println(EntityUtils.toString(response2.getEntity()));
                System.out.println("SUBMITTED");

                // do something useful with the response body
                // and ensure it is fully consumed
                EntityUtils.consume(entity2);
            } finally {
                response2.close();
            }
        } catch (IOException ex) { }
    }

}
