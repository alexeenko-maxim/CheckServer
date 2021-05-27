import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class SimpleBot {

    private static HttpURLConnection con;
    private static final String tgToken = "1865952447:AAHNniV1vxmpxl-6kSJW-Wy08OFhqfrJ5UY";
    private static final String urlToken = "https://api.telegram.org/bot"+tgToken+"/sendMessage";

    public static void main(String[] args) throws IOException, InterruptedException {

        while (true){
            String status = getCategory();
            if (!status.contains("\"code\" : 200,")) sendMsg("Сервер недоступен!");
            else sendMsg("Сервер работает...");
            Thread.sleep(300000);
}





//        sendMsg("Сервер недоступен!");
    }

    private static String getCategory() throws IOException {
        URLConnection connection = new URL("https://webservice-test.winelab.ru/winelabwebservices/v2/siteWineLab/catalogs/mobile/winelabProductCatalog/Online/v3").openConnection();

        InputStream is = connection.getInputStream();
        InputStreamReader reader = new InputStreamReader(is);
        char[] buffer = new char[256];
        int rc;

        StringBuilder sb = new StringBuilder();

        while ((rc = reader.read(buffer)) != -1)
            sb.append(buffer, 0, rc);

        reader.close();

        System.out.println(sb);
        return sb.toString();
    }

    private static void sendMsg(String str) throws IOException {

        int chatId = 1406246416;
        String urlParameters = "chat_id="+ chatId +"&text="+str;
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);

        try {
            URL url = new URL(urlToken);
            con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Java upread.ru client");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }

            StringBuilder content;

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {
                String line;
                content = new StringBuilder();

                while ((line = br.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }
            System.out.println(content.toString());

        } finally {
            con.disconnect();
        }
    }
}


