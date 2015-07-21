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
 */
public class FileUploadServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs temporary directory path to store upload file
     *
     */
    CreateTempDir tmpDir = new CreateTempDir();
    private final String uploadPath = "/srv/molgenis/temp_chromstaR/";
    private String tmp_dir = null;
    private String user_dir = null;
    private final String args = null;

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
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException, NumberFormatException {

        ArrayList<Object> checkedFunctions = new ArrayList<>();
        ArrayList<String> fileName = new ArrayList<>();
        ArrayList<String> wrongFiles = new ArrayList<>();
        int noa = 0;

        try {

            //Saves the given parameters as from Ajax in a List as a FileItem.
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory())
                    .parseRequest(request);

            //Checks if the given upload is not empty
            if (items != null && items.size() > 0) {

                // iterates over the form fields
                items.stream().filter((itemFormField) -> (itemFormField.isFormField())).forEach((itemFormField) -> {
                    //Is not a File, all other parameters will be added to the ArrayList
                    checkedFunctions.add(itemFormField.getString());
                });

                //Settings for an analysis N times
                String secondAnalysisValue = (String) checkedFunctions.get(2);
                System.out.println(secondAnalysisValue);
                ArrayList secondAnalysisList = new ArrayList<>(Arrays.asList(
                        secondAnalysisValue.split(",")));

                //number of analysis
                String x = (String) secondAnalysisList.get(0);
                noa = noa + Integer.parseInt(x);

                String resultsDirName = File.separator + "analysis_" + noa;

                //Path to the file where the previous files are for a second analysis run
                String tmp_dirAnalysis = (String) secondAnalysisList.get(1);
                String user_dirAnalysis = (String) secondAnalysisList.get(2);

                if (tmp_dirAnalysis.equals("none")) {
                    
                    user_dir = tmpDir.createDir(uploadPath, "User_");

                    // iterates over the not form fields
                    for (FileItem item : items) {
                        if (!item.isFormField()) {

                            //Checks if the files that were given are accepted by the program
                            if (item.getName().endsWith("bam")
                                    | item.getName().endsWith("bed")
                                    | item.getName().endsWith(".bed.gz")) {

                              
                                //Calls the java class/method that creates the temporary directory to save the
                                //uploaded file(s) from the client.
                                tmp_dir = tmpDir.createDir(user_dir, "User_files_");

                                fileName.add(item.getName());
                                //Saves the file in the temporary directory that was created
                                String filePath = tmp_dir + File.separator
                                        + item.getName();
                                File storeFile = new File(filePath);

                                System.out.println("print: " + storeFile);

                                // saves the file on disk
                                item.write(storeFile);
                            } else {
                                //writes a text file for a file error. This will
                                //only be written when a wrong file format is given for the analysis
                                ExportResults error = new ExportResults();

                                System.out.println("wrong: " + item.getName());
                                
                                String error_dir = tmpDir.createDir(user_dir, "error_");
                                
                                wrongFiles.add(item.getName());
                                error.writeErrorFile(error_dir, wrongFiles);
                            }
                        }
                    }
                } else {
                    tmp_dir = tmp_dirAnalysis;
                    user_dir = user_dirAnalysis;
                    fileName.add("notEMPTY");
                }

                String resultsDir = user_dir + resultsDirName;

                System.out.println("Jobs.startJob(" + fileName + "," + user_dir
                        + "," + tmp_dir + "," + args + "," + checkedFunctions
                        + "," + noa + ")");

                int jobNumber = Jobs.startJob(fileName, user_dir, tmp_dir, args,
                        checkedFunctions, noa, resultsDirName);

                response.getWriter().print(jobNumber);
                response.getWriter().close();
            }
        } catch (Exception ex) {
            Logger.getLogger(FileUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }
    }
}
