import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlet.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

import javax.servlet.http.*;

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

            response.getWriter().println("Arif Rahman Habibie");
            response.getWriter().println(formattedDate);
            response.getWriter().println(roundedPercentage + "% menuju tahun " + nextYear);

        }
    }
}
