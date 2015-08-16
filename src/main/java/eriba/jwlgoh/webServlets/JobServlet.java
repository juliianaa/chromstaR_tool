package eriba.jwlgoh.webServlets;

import eriba.jwlgoh.JavaRIntegration.*;

import java.io.*;
import java.util.concurrent.ExecutionException;
import java.util.logging.*;
import javax.servlet.ServletException;

import javax.servlet.http.*;

/**
 * JavaServlet implementation class JobServlet
 */
public class JobServlet extends HttpServlet {

    /**
     * Upon receiving the files and parameters that contains the R-package
     * settings upload submission. The request will be parsed for later use in
     * the program.
     *
     * @param request
     * @param response
     * @throws ServletException States that something went wrong with the
     * JavaServlet
     * @throws java.io.IOException Catches the exception if the previous
     * exception was not catch.
     */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException, NumberFormatException {

        //Retrieve the jobnumber of the Job
        int jobNumber = Integer.parseInt(request.getParameter("jobNumber"));

        //Checks if the Job is done or not
        if (Jobs.isFinished(jobNumber)) {
            try {
                //if the job is done
                String result = Jobs.getResult(jobNumber);
                response.getWriter().print(result);
            } catch (ExecutionException | InterruptedException | IOException ex) {
                Logger.getLogger(JobServlet.class.getName()).log(Level.SEVERE, null, ex);
                throw new ServletException(ex);
            }
        } else {
            //if the job is not done
            response.getWriter().print("Still running");
        }

        response.getWriter().close();
    }
}
