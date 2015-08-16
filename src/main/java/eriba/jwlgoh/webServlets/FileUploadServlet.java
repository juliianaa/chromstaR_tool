package eriba.jwlgoh.webServlets;

import eriba.jwlgoh.JavaRIntegration.*;

import java.io.*;
import java.util.*;
import java.util.logging.*;
import javax.servlet.ServletException;

import javax.servlet.http.*;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * JavaServlet implementation class FileUploadServlet
 *
 * @author Eriba
 */
public class FileUploadServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final String uploadPath = "/srv/molgenis/temp_chromstaR/";
    private final CreateTempDir tmpDir = new CreateTempDir();
    private String user_dir = null;
    private String tmp_dir = null;

    /**
     * Upon receiving the files and parameters that contain the R-package
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
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException,
            NumberFormatException {

        //Consist mainly of the settings for the program
        ArrayList<Object> checkedFunctions = new ArrayList<>();
        //Consist of the filenames with the correct file format uploaded by the user
        ArrayList<String> fileName = new ArrayList<>();
        //Consit of the filenames with the wrong file format uploaded by the user
        ArrayList<String> wrongFiles = new ArrayList<>();

        int noa = 0;

        try {

            //Saves the given parameters from Ajax in a List as a FileItem.
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory())
                    .parseRequest(request);

            //Checks if the given upload is not empty
            if (items != null) {

                // iterates over the form fields
                items.stream().filter((itemFormField) -> (itemFormField.isFormField())).forEach((itemFormField) -> {
                    //If paramter is not a File, it will be added to the ArrayList
                    checkedFunctions.add(itemFormField.getString());
                });

                //Settings for an analysis N times
                String secondAnalysisValue = (String) checkedFunctions.get(2);
                System.out.println(secondAnalysisValue);

                //Splits the value to a list of multiple values for the analysis
                ArrayList secondAnalysisList = new ArrayList<>(Arrays.asList(
                        secondAnalysisValue.split(",")));

                //Path to the file where the previous files are for a second 
                //analysis run
                String tmp_dirAnalysis = (String) secondAnalysisList.get(1);
                String user_dirAnalysis = (String) secondAnalysisList.get(2);

                //if this value equals to the string none, this means that the 
                //user is using the tool for the first time.
                if (user_dirAnalysis.equals("none")) {
//
                    //Creates a temporary User directory for the user in the 
                    //root directory
                    user_dir = tmpDir.createDir(uploadPath, "User_");

                    //Creates a user file directory by calling the 
                    //method to store the uploaded files, that are 
                    //in the correct file format.
                    tmp_dir = tmpDir.createDir(user_dir, "User_files_");

                    //Iterates over the form fields 
                    for (FileItem item : items) {
                        //If the item is considered not a form field, it means 
                        //that is a File 
                        if (!item.isFormField()) {
                            //Checks if the file format of the uploaded files 
                            //are accepted by the program
                            if (item.getName().endsWith("bam")
                                    | item.getName().endsWith("bed")
                                    | item.getName().endsWith(".bed.gz")) {

                                //Adds the filename to the list 
                                fileName.add(item.getName());

                                //Saves the file in the temporary file directory 
                                //that was created for the accepted files
                                String filePath = tmp_dir + File.separator
                                        + item.getName();
                                File storeFile = new File(filePath);

                                // saves the file on disk
                                item.write(storeFile);
                            } else {

                                System.out.println("wrong: " + item.getName());
                                // Adds the wrong file format to a list
                                wrongFiles.add(item.getName());
                            }
                        }
                    }
                } else {
                    //If the parameter user_dirAnalysis does not equal to none, 
                    //this means that the user is performing a second analysis 
                    //with the previous uploaded files.
                    tmp_dir = tmp_dirAnalysis;
                    user_dir = user_dirAnalysis;
                    fileName.add("notEMPTY");
                }

                System.out.println("Jobs.startJob(" + fileName + "," + checkedFunctions
                        + "," + wrongFiles + "," + user_dir + "," + tmp_dir + ")");

            }
            //Sends the job with the required parameters for the analysis 
            //and is saved as an integer
            int jobNumber = Jobs.startJob(fileName, checkedFunctions, wrongFiles, user_dir, tmp_dir);

            //The jobNumber will be given back to the Ajax success, to be  
            //used to check if the job is still running or not
            response.getWriter().print(jobNumber);
            response.getWriter().close();

        } catch (FileUploadException ex) {
            Logger.getLogger(FileUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(FileUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
