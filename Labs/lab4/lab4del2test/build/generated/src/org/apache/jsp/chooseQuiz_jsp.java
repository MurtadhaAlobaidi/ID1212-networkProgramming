package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import javax.naming.Context;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import java.util.List;
import javax.servlet.http.HttpSession;

public final class chooseQuiz_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>chosseQuiz</title>        \n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <h2>Choose the subject of the quiz</h2>\n");
      out.write("\n");
      out.write("\n");
      out.write("        ");

            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/derby");

            Connection conn = ds.getConnection();
            Statement stmt = conn.createStatement();

            //String userId = Integer.toString((Integer) request.getSession().getAttribute("userId"));
            ArrayList<String[]> quizzes = new ArrayList<String[]>();
            //ArrayList<String> questions = new ArrayList<String>();

            ArrayList<String[]> scores = (ArrayList<String[]>) request.getSession().getAttribute("scores");

            ResultSet rs = stmt.executeQuery("SELECT * FROM quizzes");
            while (rs.next()) {
                String[] strArray = new String[2];
                strArray[0] = rs.getString("id");
                strArray[1] = rs.getString("subject");
                quizzes.add(strArray);
            }

            for (int i = 0; i < quizzes.size(); i++) {
        
      out.write("\n");
      out.write("        <form id=\"form\" action=\"DBServlet\" method=\"GET\" >\n");
      out.write("            <input type=\"submit\" name=\"quiz");
      out.print( i + 1);
      out.write("\" value=\"");
      out.print( quizzes.get(i)[1]);
      out.write("\">\n");
      out.write("\n");
      out.write("            ");

                for (int j = 0; j < scores.size(); j++) {
                    if (scores.get(j)[2].equals(quizzes.get(i)[0]) && scores.get(j)[1].equals(Integer.toString((Integer) request.getSession().getAttribute("userId")))) {

            
      out.write("\n");
      out.write("            <h6> Previous score: ");
      out.print( scores.get(j)[3]);
      out.write("/3</h6>\n");
      out.write("            ");
                    }

                }
            
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("        </form>\n");
      out.write("        ");

            }
        
      out.write("\n");
      out.write("        ");

            String reload = request.getParameter("reload");
            if (reload != null && reload.equals("true")) {
        
      out.write("\n");
      out.write("        <script>\n");
      out.write("            location.reload();\n");
      out.write("        </script>\n");
      out.write("        ");

            }

        
      out.write("\n");
      out.write("    </body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
