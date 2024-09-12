package com.vamsi.SocketMachineServlet;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;



@WebServlet(name="QuoteServlet")
public class SocketServlet extends HttpServlet {

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SocketServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        double quote = 0;
        String socketType = request.getParameter("socketType");
        String quantityStr = request.getParameter("quantity");
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        // Validate inputs
        if (socketType == null || name == null || email == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Missing required parameters.");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr);
            if (quantity <= 0) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println("Invalid quantity.");
                return;
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Invalid quantity format.");
            return;
        }

        // Calculate price quote based on socket type and quantity
        switch (socketType.toLowerCase()) {
            case "type a":
                quote = quantity * 5;
                break;
            case "type b":
                quote = quantity * 10;
                break;
            case "type c":
                quote = quantity * 15;
                break;
            default:
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println("Invalid socket type.");
                return;
        }

        // Generate response with quote details
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<html>");
        response.getWriter().println("<head><title>Socket Ltd Quote</title></head>");
        response.getWriter().println("<body>");
        response.getWriter().println("<h1>Quote Details</h1>");
        response.getWriter().println("<p>Socket Type: " + socketType + "</p>");
        response.getWriter().println("<p>Quantity: " + quantity + "</p>");
        response.getWriter().println("<p>Name: " + name + "</p>");
        response.getWriter().println("<p>Email: " + email + "</p>");
        response.getWriter().println("<p>Price Quote: $" + quote + "</p>");
        response.getWriter().println("</body>");
        response.getWriter().println("</html>");
    }
}
