/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eriba.jwlgoh.webServlets;

import eriba.jwlgoh.JavaRIntegration.ExportResults;
import java.io.IOException;
import java.util.*;
import java.util.logging.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * JavaServlet implementation class GenerateRScriptServlet
 *
 * @author Eriba
 */
public class GenerateRScriptServlet extends HttpServlet {

    /**
     * Upon receiving the values a R-script will be generated with te given
     * values
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<FileItem> items = null;
        ArrayList<Object> givenValues = new ArrayList<>();
        String rScript = null;

        try {
            items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);

            //Checks if the given upload is not empty
            if (items != null && items.size() > 0) {

                // iterates over the form fields
                for (FileItem itemFormField : items) {
                    if (itemFormField.isFormField()) {

                        //Is not a File, all other parameters will be added to the ArrayList
                        givenValues.add(itemFormField.getString());
                    }
                }

                //Path that is given by the user
                String pathToFile = (String) givenValues.get(0);
                //Sets the list of settings to a String
                String settings = (String) givenValues.get(1);
                //Splits the string to a list consist of three settings
                ArrayList<String> settingsValues = new ArrayList<>(Arrays.asList(settings.split(",")));

                //Sends the values to the ExportResults class to have the R-script generated.
                ExportResults export = new ExportResults();
                rScript = export.writeToRscript(pathToFile, settingsValues);

            }

        } catch (FileUploadException ex) {
            Logger.getLogger(GenerateRScriptServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Sends the generated script to the website page
        response.getWriter().print(rScript);

    }

}
