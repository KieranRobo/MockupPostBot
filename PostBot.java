import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class PostBot {

    public static void main(String[] args) {
        runTest();
    }

    private static void runTest() {
        String devwebTest = "https://devweb2017.cis.strath.ac.uk/~rjb13182/bot.php";

        ArrayList<String[]> data = new ArrayList<>();
        data.add(new String[]{"submit", ""});
        data.add(new String[]{"first_name", "Kiehhhhhran"});
        data.add(new String[]{"second_name", "Robertson"});

        postData(devwebTest, data);
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

                System.out.println(EntityUtils.toString(response2.getEntity()));

                // do something useful with the response body
                // and ensure it is fully consumed
                EntityUtils.consume(entity2);
            } finally {
                response2.close();
            }
        } catch (IOException ex) { }
    }

}
