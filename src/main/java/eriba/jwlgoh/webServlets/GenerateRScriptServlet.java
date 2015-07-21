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
 *
 * @author Eriba
 */
public class GenerateRScriptServlet extends HttpServlet {
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
  
       List<FileItem> items = null;
       ArrayList<Object> givenValues = new ArrayList<>();
       String rScript = null;
       
        try {
            items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            
            
            //Checks if the given upload is not empty
        if (items != null && items.size() > 0) {

            // iterates over the form fields
            for (FileItem itemFormField : items) {
                if (itemFormField.isFormField()){

                    //Is not a File, all other parameters will be added to the ArrayList
                    givenValues.add(itemFormField.getString());
                }
            }
            
            
            String pathToFile = (String) givenValues.get(0);
            String settings = (String) givenValues.get(1);
            ArrayList<String> settingsValues = new ArrayList<>(Arrays.asList(settings.split(",")));
            
            
            ExportResults export = new ExportResults();
            rScript = export.writeToRscript(pathToFile, settingsValues);
            
        }
            
            
        } catch (FileUploadException ex) {
            Logger.getLogger(GenerateRScriptServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        response.getWriter().print(rScript);
       
    }


}
