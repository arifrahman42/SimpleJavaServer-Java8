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
        Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(HelloWorldServlet.class, "/");
        
        server.start();
        System.out.println("Peladen berjalan pada Localhost port 8080");
        server.join();
    }

    public static class HelloWorldServlet extends HttpServlet {
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
            response.setContentType("text/plain");
            response.setStatus(HttpServletResponse.SC_OK);

            ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Jakarta"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy HH:mm:ss", java.util.Locale.forLanguageTag("id-ID"));
            String formattedDate = now.format(formatter);

            LocalDate today = LocalDate.now();
            int dayOfYear = today.getDayOfYear();
            boolean isLeapYear = today.isLeapYear();
            int totalDaysInYear = isLeapYear ? 366 : 365;

            double percentagePassed = (dayOfYear / (double) totalDaysInYear) * 100;
            int roundedPercentage = (int) percentagePassed;
            int nextYear = today.getYear() + 1;
            
            String city = "Medan";
            String apiUrl = "http://api.open-meteo.com/v1/forecast?latitude=3.5833&longitude=98.6667&current=temperature_2m";
            String jsonResponse = fetchDataFromApi(apiUrl);

            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            JsonObject current = jsonObject.getAsJsonObject("current");
            JsonObject currentUnits = jsonObject.getAsJsonObject("current_units");
            
            double temperature = current.get("temperature_2m").getAsDouble();
            String temperatureUnit = currentUnits.get("temperature_2m").getAsString();
            String gmtTime = current.get("time").getAsString();
            
            LocalDateTime gmtDateTime = LocalDateTime.parse(gmtTime);
            LocalDateTime wibDateTime = gmtDateTime.atZone(ZoneId.of("GMT"))
            .withZoneSameInstant(ZoneId.of("Asia/Jakarta"))
            .toLocalDateTime();
            Date wibDate = Date.from(wibDateTime.atZone(ZoneId.systemDefault()).toInstant());
            String formattedWIBTime = new SimpleDateFormat("HH:mm").format(wibDate) + " WIB";

            response.getWriter().println("Arif Rahman Habibie");
            response.getWriter().println(formattedDate);
            response.getWriter().println(roundedPercentage + "% menuju tahun " + nextYear);
            response.getWriter().println("Suhu udara di " + city + " saat ini adalah " + temperature + temperatureUnit + ". Terakhir dimutakhirkan pada pukul " + formattedWIBTime);
        }
    }

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
}
