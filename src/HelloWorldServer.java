import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlet.*;
import java.io.IOException;
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
            response.getWriter().println("Arif Rahman Habibie");
        }
    }
}
