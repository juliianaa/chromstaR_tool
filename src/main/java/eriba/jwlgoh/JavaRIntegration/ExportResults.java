/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eriba.jwlgoh.JavaRIntegration;

import java.io.*;
import java.util.ArrayList;

/**
 * This class contains one method that is responsible for writing a text file
 * that contains all the information about which file(s) were uploaded and which
 * settings where used for the analysis.
 *
 * @author Eriba
 */
public class ExportResults {

    /**
     * Writes the information of the settings and files that were used for the
     * analysis to a text file
     *
     * @param analysisResultsDir path of where the results will be stored
     * @param resultsDirName name of the directory
     * @param settings settings that were used 
     * @param fileNames names of the files uploaded
     * @param noa number of analysis done
     */
    public void writeToTxt(String analysisResultsDir, String resultsDirName,
            ArrayList<String> settings, ArrayList<String> fileNames, int noa) {

        System.out.println("Writing file");

        try {
            
            //First sentence in text file
            String firstSentence = "You gave the following file for analysis number " + noa + ": \n \n ";
            
            //text of analysis settings + values
            String Secondcontent = "\n Used the settings of: \n \n Bins: " + settings.get(0)
                    + " \n Univariate maximum time: " + settings.get(1)
                    + " \n Multivariate maximum time: " + settings.get(2);
            
            File file = new File(analysisResultsDir + resultsDirName + "_settings.txt");

            // Checks if file does not exists in the resuts directory.
            // If not it will create the file
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
           
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                //writes the first sentence of the file about which files where used for the analysis
                bw.write(firstSentence);

                //if the analysis has not been done yet the files name will be given.
                //If the analysis has been done at least once, the files name will not be given
                //but will ask the user to go to the file called analysis_1_settings.txt
                if (noa == 1) {

                    for (String name : fileNames) {
                        bw.write(name + "\n");
                    }
                } else {
                    bw.write("Used the previous files given: see analysis_1_settings.txt");
                }

                //writes the second part of the txt file which consists of the settings that were used
                //for the analysis
                bw.write(Secondcontent);
            }

            System.out.println("Done writing");

        } catch (IOException e) {
            System.out.println("error writing to file: " + e);
        }

    }

    /**
     * Writes an error message in a .txt file when a user uploads the wrong file format
     * 
     * @param user_dir path of where the user directory is
     * @param fileNames list of the wrong uploaded file formats
     * @throws IOException 
     */
    public void writeErrorFile(String user_dir, ArrayList<String> fileNames) throws IOException {
        CreateTempDir tmpDir = new CreateTempDir();
        //Creates a directory where the error text file 
        //will be stored
        String error_dir = tmpDir.createDir(user_dir, "error_");
        
        //Explanation of file format error 
        String firstSentence = "The given file(s) is/are not in the correct format. \n"
                + " This program only accepts file in bed or bam format. \n"
                + " A compressed bed file in the format .gz is accepted too. \n \n "
                + " The file(s) you have given is/are: \n";

        File file = new File(error_dir + File.separator + "errorFile.txt");

        // if file doesnt exists, then create it
        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        //writes the first sentence of the file about which files where used for the analysis
        try (BufferedWriter bw = new BufferedWriter(fw)) {
            //writes the first sentence of the file about which files where used for the analysis
            bw.write(firstSentence);

            for (String name : fileNames) {
                bw.write(name + "\n");
            }

        }

        System.out.println("Done writing error report");

    }

    /**
     * 
     * 
     * @param pathToFile
     * @param settingsValues
     * @return
     */
    public String writeToRscript(String pathToFile, ArrayList<String> settingsValues) throws FileNotFoundException, IOException {

        
        String GenerateRscript = null;

        if (pathToFile.contains("\\") | pathToFile.contains("/")) {
            
            if(pathToFile.contains("\\")){
                pathToFile= pathToFile.replace("\\", File.separator);
            }
            File rScript = new File("/srv/molgenis/rScript/callChromstaROptions.R");
            try (BufferedReader br = new BufferedReader(new FileReader(rScript))) {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line + "\n");
                    sb.append("\n");
                    line = br.readLine();
                }
                
                String input = "inputOption(\"" + pathToFile + "\"," + 500 + "," + 1000 + "," + 1000 + ")";
                GenerateRscript = sb.toString() + input;
                
                
                
            }
            
            
            
            
        } else {
            GenerateRscript = "The path given is not a valid path";
        }
        
        return GenerateRscript;

    }

}
