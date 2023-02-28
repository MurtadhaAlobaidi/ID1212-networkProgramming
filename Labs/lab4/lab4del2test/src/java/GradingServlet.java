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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;

/**
 *
 * @author murtadha & Abdullah
 */
@WebServlet(name = "GradingServlet", urlPatterns = {"/GradingServlet"})
public class GradingServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/derby");
            Connection conn = ds.getConnection();// get a connection from the pool

            conn.setAutoCommit(false);

            ArrayList<String[]> questions = (ArrayList<String[]>) request.getSession().getAttribute("selcetedQuestions");
            String answers;
            String answered = "";
            int score = 0;

            for (int i = 0; i < questions.size(); i++) {
                answers = questions.get(i)[3];
                //System.out.println("The real answer is " + answers);

                for (int j = 0; j < 3; j++) {

                    String temp = request.getParameter("question" + questions.get(i)[0] + "_option" + j);
                    System.out.println("From GradingServlet: question" + questions.get(i)[0] + "_option" + j);
                    if (temp == null) {
                        temp = "0";
                    }

                    answered += temp + "/";

                }
                answered = answered.substring(0, answered.length() - 1);
                if (answered.equals(answers)) {
                    score++;
                }
                //System.out.println("User answered: " + answered);
                answered = "";
            }

            System.out.println("The score is: " + score);
            /**
             * ********************************************************************************************
             */
            /*
             * visning av tidigare resultat.
             */
            ArrayList<String[]> scores = new ArrayList<>();
            java.sql.Statement stmt = conn.createStatement();
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
            /**
             * ********************************************************************************************
             */

            conn.setAutoCommit(false);
            PreparedStatement update_statement = conn.prepareStatement("UPDATE results SET score=" + score + " WHERE user_id="
                    + request.getSession().getAttribute("userId") + " AND quiz_id="
                    + request.getSession().getAttribute("selectedQuizId"));
            update_statement.executeUpdate();

            request.getRequestDispatcher("/chooseQuiz.jsp?reload=true").forward(request, response);
            conn.commit(); // commit the transaction

        } catch (SQLException e) {
            conn.rollback(); // rollback the transaction if an exception occurs
        } catch (NamingException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static class conn {

        static void rollback() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }
}
