import com.google.gson.*;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlet.*;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.servlet.http.*;
import java.io.*;
import java.net.*;

public class HelloWorldServer {
    public static void main(String[] args) throws Exception {
        // TODO 1: Mengaktifkan peladen.
        Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(HelloWorldServlet.class, "/");
        context.addServlet(new ServletHolder(new FontServlet()), "/resources/fonts/*");
        
        server.start();
        System.out.println("Peladen berjalan pada Localhost port 8080");
        server.join();
    }

    // TODO 2: Menyusun method untuk memproses keluaran data.
    public static class HelloWorldServlet extends HttpServlet {
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
            response.setContentType("text/html; charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);

            // TODO 3: Menyetel nama yang akan ditampilkan.
            String myName = "Arif Rahman Habibie";

            // TODO 4: Menyetel format penanggalan Bahasa Indonesia.
            ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Jakarta"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy HH:mm:ss", java.util.Locale.forLanguageTag("id-ID"));
            String formattedDate = now.format(formatter);

            // TODO 5: Menampilkan angka presentase waktu sejak 1 Januari hingga 31 Desember.
            LocalDate today = LocalDate.now();
            int dayOfYear = today.getDayOfYear();
            boolean isLeapYear = today.isLeapYear();
            int totalDaysInYear = isLeapYear ? 366 : 365;

            double percentagePassed = (dayOfYear / (double) totalDaysInYear) * 100;
            int roundedPercentage = (int) percentagePassed;
            int nextYear = today.getYear() + 1;
            
            // TODO 7: Menampilkan informasi suhu secara daring yang telah diambil dari internet.
            String city = "Medan";
            String apiUrl = "http://api.open-meteo.com/v1/forecast?latitude=3.5833&longitude=98.6667&current=temperature_2m";
            String jsonResponse = fetchDataFromApi(apiUrl);

            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            JsonObject current = jsonObject.getAsJsonObject("current");
            JsonObject currentUnits = jsonObject.getAsJsonObject("current_units");
            
            double temperature = current.get("temperature_2m").getAsDouble();
            String temperatureUnit = currentUnits.get("temperature_2m").getAsString();
            String gmtTime = current.get("time").getAsString();
            
            // TODO 8: Menyetel format waktu WIB untuk menampilkan waktu pada informasi suhu udara.
            LocalDateTime gmtDateTime = LocalDateTime.parse(gmtTime);
            LocalDateTime wibDateTime = gmtDateTime.atZone(ZoneId.of("GMT"))
            .withZoneSameInstant(ZoneId.of("Asia/Jakarta"))
            .toLocalDateTime();
            Date wibDate = Date.from(wibDateTime.atZone(ZoneId.systemDefault()).toInstant());
            String formattedWIBTime = new SimpleDateFormat("HH:mm").format(wibDate) + " WIB";

            // TODO 11: Menampilkan semua informasi yang telah diproses pada file HTML.
            String htmlPath = "resources/index.html";
            String responseContent = loadHtmlTemplate(htmlPath)
            .replace("{{name}}", myName)
            .replace("{{date}}", formattedDate)
            .replace("{{percentage}}", roundedPercentage + "%")
            .replace("{{nextYear}}", nextYear + "")
            .replace("{{city}}", city)
            .replace("{{temperature}}", String.valueOf(temperature))
            .replace("{{temperatureUnit}}", temperatureUnit)
            .replace("{{formattedWIBTime}}", formattedWIBTime);

            response.getWriter().println(responseContent);
        }
    }

    // TODO 6: Menambahkan method untuk memproses data dari internet.
    private static String fetchDataFromApi (String apiUrl) throws IOException {
        StringBuilder response = new StringBuilder();
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(15000);
        connection.setReadTimeout(15000);

        try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(connection.getInputStream(), "UTF-8"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            } catch (Exception e) {
                System.err.println("Terjadi Kesalahan. Pesan Galat: " + e.getMessage());
                return "{\"current\":{\"temperature_2m\":0,\"time\":\"1970-01-01T00:00\"},\"current_units\":{\"temperature_2m\":\"°C\"}}";
    }
        return response.toString();
    }

    // TODO 10: Render keluaran sebagai berkas HTML.
    private static String loadHtmlTemplate (String filePath) throws IOException {
        StringBuilder htmlContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                htmlContent.append(line);
            }
        }
        return htmlContent.toString();
    }

    // TODO 13: Menambahkan beberapa methods agar fon Calibri dapat ditampilkan pada perangkat lain.
    public static class FontServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
            String filePath = "resources/fonts" + request.getPathInfo();
            File file = new File(filePath);

            if (!file.exists() || file.isDirectory()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            
            String mimeType = getMimeType(filePath);
            response.setContentType(mimeType);
            response.setStatus(HttpServletResponse.SC_OK);

            try (OutputStream os = response.getOutputStream();
                InputStream is = new FileInputStream(file)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }}
        }

        public static String getMimeType(String filePath) {
            if (filePath.endsWith(".ttf")) {
                return "font/ttf";
            } else if (filePath.endsWith(".woff")) {
                return "font/woff";
            } else if (filePath.endsWith(".woff2")) {
                return "font/woff2";
            } else {
                return "application/octet-stream";
            }
        }
    }

}
