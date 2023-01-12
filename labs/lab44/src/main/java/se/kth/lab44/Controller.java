/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package se.kth.lab44;

import java.io.IOException;
import java.util.LinkedHashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Lab4 part1
 * inspiration from "https://docs.oracle.com/javaee/7/tutorial/"
 */
/**
 *
 * @author murtadha and @author Abdullah 
 */
@WebServlet(name = "controller", urlPatterns = {"/controller"})
public class Controller extends HttpServlet {

    private GameModel game;
    LinkedHashMap<String, GameModel> games;
    int guessedValue = -1;
    String guessStr;

    public Controller() {
        this.games = new LinkedHashMap<>();
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");

        if (games.isEmpty()) {
            game = new GameModel();
            games.put(request.getSession().getId(), game);
        } else {
            game = games.get(request.getSession().getId());
        }

        if (game == null) {
            game = new GameModel();
            games.put(request.getSession().getId(), game);

        } else if (game != null) {
            guessStr = request.getParameter("guess");
            System.out.println("guessStr: " + guessStr);
        }

        RequestDispatcher requestDispatcher;
        //New Game
        if (guessStr != null) {
            guessedValue = Integer.parseInt(guessStr);
            game.game(guessedValue);

            int target = game.getTarget();

            //System.out.println("target: " + target);
            // System.out.println("Attempts: "+ game.getAttempts());
            //System.out.println("Target: "+game.getTarget());
            //Guess Higher (0)
            if (target > guessedValue) {
                requestDispatcher = request.getRequestDispatcher("/index_Higher.jsp");
                request.getSession().setAttribute("game", game);
                requestDispatcher.forward(request, response);
            }

            //Guess Lower(-1)
            if (target < guessedValue) {
                requestDispatcher = request.getRequestDispatcher("/index_Lower.jsp");
                request.getSession().setAttribute("game", game);
                requestDispatcher.forward(request, response);
            }

            //Win (1)
            if (target == guessedValue) {
                request.getRequestDispatcher("/index_Win.jsp");
                requestDispatcher = request.getRequestDispatcher("/index_Win.jsp");

                //Start new game
                requestDispatcher.forward(request, response);
                game = new GameModel();
                games.put(request.getSession().getId(), game);
            }
        }

        requestDispatcher = request.getRequestDispatcher("/index_New.jsp");
        request.getSession().setAttribute("game", game);
        requestDispatcher.forward(request, response);

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
