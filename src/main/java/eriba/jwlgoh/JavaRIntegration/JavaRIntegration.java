/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eriba.jwlgoh.JavaRIntegration;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * This class contains one method which is the start method where all the needed
 * classes/methods will be called when needed. This class/method can be seen as
 * the main class/method of the program.
 *
 * @author jwlgoh
 */
public class JavaRIntegration {

    private final CreateTempDir tmpDir = new CreateTempDir();
    private final ExportResults export = new ExportResults();
    // Tries to call the start function of the call methods
    private final CallRMethods call = new CallRMethods();
    private String args = null;

    /**
     * This method can be seen as the main method where the needed method will
     * be send to the next method with the needed values to retrieve the
     * results.
     *
     * @param fileNames
     * @param checkedFunctions
     * @param wrongFiles
     * @param user_dir
     * @param tmp_dir
     * @return a string of values separated with a ","
     * @throws java.io.IOException
     */
    public String start(ArrayList<String> fileNames,
            ArrayList<Object> checkedFunctions, ArrayList<String> wrongFiles, String user_dir,
            String tmp_dir
    ) throws IOException {

        System.out.println("In program!");
        int noa = 0;

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

        //Settings for the analysis in R as a String
        String settings = (String) checkedFunctions.get(1);
        String analysisResultsDir = user_dir + resultsDirName;
        //Settings for the analysis in R as a List
        ArrayList<String> settingsValues = new ArrayList<>(Arrays.asList(settings.split(",")));

        //writes a text file for a file error. This will
        //only be written when a wrong file format is 
        //given for the analysis
        if (!wrongFiles.isEmpty()) {
            export.writeErrorFile(user_dir, wrongFiles);
        }

        //Checks if list is not empty
        if (!fileNames.isEmpty()) {
            try {
                System.out.println("call R methods");

                //Send to the next function
                call.runRFunction(tmp_dir, settingsValues, analysisResultsDir);

                //writes a text file consisting of the files and settings used for the analysis
                export.writeToTxt(analysisResultsDir, resultsDirName, settingsValues, fileNames, noa);

                //sets with the needed information into a String format for later use if the user
                //wishes to do another analysis with the files but, with different settings.
                args = user_dir + "," + tmp_dir + "," + (noa + 1);

                System.out.println("Give argument to Servlet: \n" + args);

            } catch (NullPointerException e) {
                System.out.println("error integration: " + e);
            }
        } else {
            args = user_dir + "," + tmp_dir + "," + (noa);
        }

        return args;

    }
}
