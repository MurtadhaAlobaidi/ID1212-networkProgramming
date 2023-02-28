/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;

/**
 *
 * @author murtadha & Abdullah
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/derby");
            Connection conn = ds.getConnection();
            java.sql.Statement stmt = conn.createStatement();

            conn.setAutoCommit(false);

            ResultSet rs = stmt.executeQuery("select * from users");
            while (rs.next()) {
                if (rs.getString("username").equalsIgnoreCase(username)
                        && rs.getString("password").equals(password)) {

                    request.getSession().setAttribute("userId", rs.getInt("id"));
                    request.getRequestDispatcher("/chooseQuiz.jsp").forward(request, response);

                } else {

                }
            }
            
            ArrayList<String[]> scores = new ArrayList<>();
            //java.sql.Statement stmt = conn.createStatement();
            ResultSet rs3 = stmt.executeQuery("SELECT * FROM results WHERE user_id=" + request.getSession().getAttribute("userId"));
            while (rs3.next()) {
                String[] strArray = new String[4];
                strArray[0] = rs3.getString("id");
                strArray[1] = rs3.getString("user_id");
                strArray[2] = rs3.getString("quiz_id");
                strArray[3] = rs3.getString("score");
                scores.add(strArray);
            }
            request.getSession().setAttribute("scores", scores);
            
            conn.commit(); // commit the transaction

        } catch (SQLException e) {
            GradingServlet.conn.rollback(); // rollback the transaction if an exception occurs
        } catch (NamingException ex) {
            System.out.println(ex.getMessage());
        }

    }

}
