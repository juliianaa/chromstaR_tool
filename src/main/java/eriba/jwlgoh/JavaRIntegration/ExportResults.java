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
     * analysis to a .txt file
     *
     * @param analysisResultsDir
     * @param resultsDirName
     * @param settings
     * @param fileNames
     * @param noa
     */
    public void writeToTxt(String analysisResultsDir, String resultsDirName,
            ArrayList<String> settings, ArrayList<String> fileNames, int noa) {

        System.out.println("Writing file");

        try {

            String firstSentence = "You gave the following file for analysis number " + noa + ": \n \n ";

            String Secondcontent = "\n Used the settings of: \n \n Bins: " + settings.get(0)
                    + " \n Univariate maximum time: " + settings.get(1)
                    + " \n Multivariate maximum time: " + settings.get(2);

            File file = new File(analysisResultsDir + resultsDirName + "_settings.txt");

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            //writes the first sentence of the file about which files where used for the analysis
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

    public void writeErrorFile(String path, ArrayList<String> fileNames) throws IOException {

        String firstSentence = "The given file(s) is/are not in the correct format. \n"
                + " This program only accepts file in bed or bam format. \n"
                + " A compressed bed file in the format .gz is accepted too. \n \n "
                + " The file(s) you have given is/are: \n";

        File file = new File(path + File.separator + "errorFile.txt");

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

    public String writeToRscript(String pathToFile, ArrayList<String> settingsValues) {

        String rScript = null;

        if (pathToFile.contains("\\") | pathToFile.contains("/")) {

            pathToFile = pathToFile.replace("\\", File.separator);

            rScript
                    = "#install.packages(\"chromstaR\")<br />"
                    + "<br />"
                    + "library(chromstaR)<br />"
                    + "<br />"
                    + "runDefaultUniFile <- function(bedFile, bins, uMaxT){<br />"
                    + "  binned.data3 <- bed2binned(bedFile, assembly='hg19', binsize=bins, save.as.RData=FALSE)<br />"
                    + "  <br />"
                    + "  # Fit the univariate Hidden Markov Model<br />"
                    + "  uni.HMM <- callPeaksUnivariate(binned.data3, ID='example_H3K36me3', max.time=uMaxT)<br />"
                    + "  <br />"
                    + "  exportUnivariates(list(uni.HMM), filename = \"exportedFilesUnivariates\", what = 'peaks')<br />"
                    + "  <br />"
                    + "  setwd(a)<br />"
                    + "  <br />"
                    + "  pdf(\"exported_Univariate_plots.pdf\")<br />"
                    + "  <br />"
                    + "  print(plot(uni.HMMs[[1]], type=\"histogram\"))<br />"
                    + "  print(plot(uni.HMMs[[1]], type=\"karyogram\"))<br />"
                    + "  print(plot(uni.HMMs[[1]], type=\"boxplot\"))<br />"
                    + "  print(plot(uni.HMMs[[1]], type=\"normalTransformation\"))<br />"
                    + "  <br />"
                    + "  dev.off()<br />"
                    + "}<br />"
                    + "<br />"
                    + "runChromstaROptions <- function(bedFiles, bins, uMaxT, mMaxT){<br />"
                    + "  <br />"
                    + "  uni.HMMs <- list()<br />"
                    + "  binned.data.list <- list()<br />"
                    + "  <br />"
                    + "  for (bedfile in bedFiles) {<br />"
                    + "    binned.data <- bed2binned(bedfile, assembly='hg19', binsize=bins, save.as.RData=F)<br />"
                    + "    binned.data.list[[bedfile]] <- binned.data<br />"
                    + "    uni.HMMs[[bedfile]] <- callPeaksUnivariate(binned.data, ID=basename(bedfile), max.time=uMaxT, eps=0.01)<br />"
                    + "  }<br />"
                    + "  <br />"
                    + "  multi.hmm <- callPeaksMultivariate(uni.HMMs, eps=0.1, max.time=mMaxT)<br />"
                    + "  <br />"
                    + "  <br />"
                    + "  exportUnivariates(uni.HMMs, filename=\"exportedFilesUnivariates\", what = 'peaks')<br />"
                    + "  exportMultivariate(multi.hmm,file=\"exportFilesMultivariate\",exclude.states=c(0,127), what = 'peaks')<br />"
                    + "  <br />"
                    + "  pdf(\"exported_Univariate_plots.pdf\")<br />"
                    + "  <br />"
                    + "  for (i in seq(uni.HMMs)){<br />"
                    + "    print(plot(uni.HMMs[[i]], type=\"histogram\"))<br />"
                    + "    print(plot(uni.HMMs[[i]], type=\"karyogram\"))<br />"
                    + "    print(plot(uni.HMMs[[i]], type=\"boxplot\"))<br />"
                    + "    print(plot(uni.HMMs[[i]], type=\"normalTransformation\"))<br />"
                    + "  }<br />"
                    + "  <br />"
                    + "  dev.off()<br />"
                    + "  <br />"
                    + "  pdf(\"exported_Multivariate_plots.pdf\")<br />"
                    + "  <br />"
                    + "  print(plot(multi.hmm, type=\"histograms\"))<br />"
                    + "  print(plot(multi.hmm, type=\"transitionMatrix\"))<br />"
                    + "  <br />"
                    + "  dev.off()<br />"
                    + "  <br />"
                    + "}<br />"
                    + "<br />"
                    + "inputOption <- function(p,bins, uMaxT, mMaxT){<br />"
                    + "  <br />"
                    + "  bedFiles <- list.files(p, full=T)<br />"
                    + "  <br />"
                    + "  setwd(p)<br />"
                    + "  <br />"
                    + "  if(length(bedFiles) == 1){<br />"
                    + "    <br />"
                    + "    runDefaultUniFile(bedFiles, bins, u)<br />"
                    + "    <br />"
                    + "  }else{<br />"
                    + "    <br />"
                    + "    runChromstaROptions(bedFiles, bins, u, m)<br />"
                    + "    <br />"
                    + "  }<br />"
                    + "  <br />"
                    + "}<br />"
                    + "<br />"
                    + "<br />"
                    + "inputOption(\"" + pathToFile + "\"," + settingsValues.get(0) + "," + settingsValues.get(1) + "," + settingsValues.get(2) + ")";

        } else {
            rScript = "The path given is not a valid path";

        }

        return rScript;

    }

}
