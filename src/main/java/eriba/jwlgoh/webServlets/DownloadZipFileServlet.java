/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eriba.jwlgoh.webServlets;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.FileUtils;
 
/**
 *  A JavaServlet implementation class DownloadZipFileServlet
 * 
 * @author Eriba
 */
public class DownloadZipFileServlet extends HttpServlet {
    String dir = "/srv/molgenis/temp_chromstaR/";
    /**
     * 
     * Upon receiving the path of the temporary directory where the result file(s) are stored,
     * a zip file will be created, where all the results will be compressed for the user to download.
     * 
     * @param request 
     * @param response
     * @throws ServletException States something went wrong with the JavaServlet
     * @throws java.io.IOException Catches the exception if the previous exception was not catch.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

                
            //Gets the path of the temporary directory where the exported result files are.
            String zipFilePath = request.getParameter("zipPath");
                    
            File directory = new File(zipFilePath);

            //Saves all the files in the temporary directory as a list
            File[] files = directory.listFiles();


            // Calls the zipFiles method for creating a zip stream.
            byte[] zip = zipFiles(files);

            // Sends the response back to the browser for the user. 
            ServletOutputStream sos = response.getOutputStream();
            // The content for zip file type is "application/zip". 
            response.setContentType("application/zip");
            // Sends the content as an attachmnet for the browser to show a dialog that will let 
            //the user choose where to save the zip file.
            response.setHeader("Content-Disposition", "attachment; filename=\"chromstaR_results.zip\"");

            sos.write(zip);
            sos.flush();


            //path of the root temp directory
            File tmp_dir = new File(dir);
            
            String[] UserDirs = tmp_dir.list();
            
            //int number of two days
            int twoDays = (2*24*60*60*1000);
            
            for(String userTmpDir : UserDirs)
            {   
                File userDir = new File(userTmpDir);
                //Gets the date of the last modified directory
                long dirDate = userDir.lastModified();

               if (dirDate > twoDays){
                    FileUtils.deleteDirectory(userDir);
                }
            }


            //Deletes the downloaded directory
            FileUtils.deleteDirectory(directory);
        }
            
    
 
    /**
     * Compress the results file(s) into a zip file, if the filename starts with "export". 
     * 
     * @param files
     * @return boas
     * @throws java.io.IOException something went wrong
     */
     public byte[] zipFiles(File[] files) throws IOException {
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        
        //Byte is used as the length of the input section is not known.
        byte bytes[] = new byte[2048];
        
        //Goes through all the subdirectories
        for (File dirName : files) {
            
            //takes the subdirectory that starts with "analysis" which contains the analysis result files
            if (dirName.isDirectory() && dirName.getName().startsWith("error_") 
                    || dirName.isDirectory() && dirName.getName().startsWith("analysis_")){
                
                //Takes the files that can be found in the given subdirectory
                File[] filesInDir = dirName.listFiles();
                
                //Goes through the list of subdirectory content
                for (File analysisFile : filesInDir){
                    
                    zos.putNextEntry(new ZipEntry(dirName.getName() + File.separator + analysisFile.getName()));
                    try (BufferedInputStream bis = new BufferedInputStream(
                            new FileInputStream(analysisFile))) {
                        int bytesRead;
                        while ((bytesRead = bis.read(bytes)) != -1) {
                            zos.write(bytes, 0, bytesRead);
                        }
                        zos.closeEntry();
                    }
                }
            }
        }
        zos.flush();
        baos.flush();
        zos.close();
        baos.close();
 
        return baos.toByteArray();
    }
}