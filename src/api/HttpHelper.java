package api;

import resources.SendType;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpHelper {
    public String send(SendType sendType, String url, Map<String, String> headers, String body) {
        try {
            URL connUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) connUrl.openConnection();

            if (sendType == SendType.GET)
                conn.setDoOutput(false);
            else if (sendType == SendType.POST || sendType == SendType.PUT)
                conn.setDoOutput(true);
            conn.setRequestMethod(sendType.name());

            if (headers != null)
                headers.forEach(conn::addRequestProperty);
            if (body != null) {
                DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                out.write(body.getBytes());
            }

            StringBuilder builder = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;

            while ((line = br.readLine()) != null) {
                builder.append(line);
                builder.append(System.lineSeparator());
            }
            return builder.toString();
        } catch (IOException e) {
            return null;
        }
    }
}
