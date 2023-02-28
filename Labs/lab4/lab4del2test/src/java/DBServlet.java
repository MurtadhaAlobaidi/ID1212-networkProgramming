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
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpSession;

/**
 *
 * @author murtadha & Abdullah
 */
@WebServlet(name = "DBServlet", urlPatterns = {"/DBServlet"})
public class DBServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/derby");
            Connection conn = ds.getConnection();

            conn.setAutoCommit(false);

            Statement stmt = conn.createStatement();

            ArrayList<String[]> questions = new ArrayList<>();

            ResultSet rs = stmt.executeQuery("SELECT * FROM questions");
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            while (rs.next()) {
                String[] strArray = new String[columnCount];
                strArray[0] = rs.getString("id");
                strArray[1] = rs.getString("text");
                strArray[2] = rs.getString("options");
                strArray[3] = rs.getString("answer");
                questions.add(strArray);
            }

            String quiz = request.getParameter("quiz1");
            if (quiz != null) {

                request.getSession().setAttribute("selectedQuiz", quiz);
                request.getSession().setAttribute("selectedQuizId", 1);
            }

            quiz = request.getParameter("quiz2");
            if (quiz != null) {
                // System.out.println("Selected quiz is: " + quiz);
                request.getSession().setAttribute("selectedQuiz", quiz);
                request.getSession().setAttribute("selectedQuizId", 2);
            }

           
            ArrayList<String[]> selectors = new ArrayList<>();
            ResultSet rs2 = stmt.executeQuery("SELECT * FROM selector");

            ResultSetMetaData rsmd6 = rs2.getMetaData();
            int columnCount6 = rsmd6.getColumnCount();

            while (rs2.next()) {
                String[] strArray6 = new String[columnCount6];
                strArray6[0] = rs2.getString("quiz_id");
                strArray6[1] = rs2.getString("question_id");
                selectors.add(strArray6);
            }

            ResultSet rs1 = stmt.executeQuery("SELECT * FROM results WHERE user_id=" + request.getSession().getAttribute("userId")
                    + " AND quiz_id=" + request.getSession().getAttribute("selectedQuizId"));
            rs1.next();

            request.getSession().setAttribute("selectors", selectors);

            request.getSession().setAttribute("score", rs1.getString("score"));
            request.getSession().setAttribute("questions", questions);
            request.getRequestDispatcher("/quiz.jsp").forward(request, response);

            conn.commit(); // commit the transaction
        } catch (SQLException e) {
            GradingServlet.conn.rollback(); // rollback the transaction if an exception occurs
        } catch (NamingException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
