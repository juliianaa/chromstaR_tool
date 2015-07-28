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

    /**
     * Constructs a temporary directory path to store uploaded file(s)
     */
    CreateTempDir tmpDir = new CreateTempDir();
    //Path of the root directory of the temporary directory
    private final String uploadPath = "/srv/molgenis/temp_chromstaR/";
    private String tmp_dir = null;
    private String user_dir = null;
    private final String args = null;

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

                //number of analysis
                String x = (String) secondAnalysisList.get(0);
                noa = noa + Integer.parseInt(x);

                //Name for the results directory of the analysis
                String resultsDirName = File.separator + "analysis_" + noa;

                //Path to the file where the previous files are for a second 
                //analysis run
                String tmp_dirAnalysis = (String) secondAnalysisList.get(1);
                String user_dirAnalysis = (String) secondAnalysisList.get(2);

                //if this value equals to the string none, this means that the 
                //user is using the tool for the first time.
                if (user_dirAnalysis.equals("none")) {

                    //Creates a temporary User directory for the user in the 
                    //root directory
                    user_dir = tmpDir.createDir(uploadPath, "User_");

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

                                //Creates a user file directory by calling the 
                                //method to store the uploaded files.
                                tmp_dir = tmpDir.createDir(user_dir, "User_files_");

                                //Adds the filename to the list 
                                fileName.add(item.getName());

                                //Saves the file in the temporary directory 
                                //that was created
                                String filePath = tmp_dir + File.separator
                                        + item.getName();
                                File storeFile = new File(filePath);

                                // saves the file on disk
                                item.write(storeFile);
                            } else {

                                ExportResults error = new ExportResults();

                                System.out.println("wrong: " + item.getName());

                                //Creates a directory where the error text file 
                                //will be stored
                                String error_dir = tmpDir.createDir(user_dir, "error_");

                                wrongFiles.add(item.getName());

                                //writes a text file for a file error. This will
                                //only be written when a wrong file format is 
                                //given for the analysis
                                error.writeErrorFile(error_dir, wrongFiles);
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

                System.out.println("Jobs.startJob(" + fileName + "," + user_dir
                        + "," + tmp_dir + "," + args + "," + checkedFunctions
                        + "," + noa + ")");

                //Sends the job with the required parameters for the analysis 
                //and is saved as an integer
                int jobNumber = Jobs.startJob(fileName, user_dir, tmp_dir, args,
                        checkedFunctions, noa, resultsDirName);

                //The jobNumber will be given back to the Ajax success, to be  
                //used to check if the job is still running or not
                response.getWriter().print(jobNumber);
                response.getWriter().close();
            }
        } catch (Exception ex) {
            Logger.getLogger(FileUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }
    }
}
