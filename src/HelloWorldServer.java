import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlet.*;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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
            
            response.getWriter().println("Arif Rahman Habibie");
            response.getWriter().println(formattedDate);
        }
    }
}
