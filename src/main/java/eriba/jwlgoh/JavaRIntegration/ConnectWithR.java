/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eriba.jwlgoh.JavaRIntegration;

import org.rosuda.JRI.Rengine;

/**
 *
 * This class contains only one method which is the method that will connect
 * Java with R.
 *
 * @author jwlgoh
 */
public class ConnectWithR {

    private Rengine re = Rengine.getMainEngine();

    /**
     * Creates a new R engine which is the bridge between Java and R. There can
     * be only one true R instance at a time, this is also the only instance.
     *
     * @return the connection that has been made with R
     */
    public Rengine rConnect() {
        if (re == null) {
            System.out.println("Connection made");
            //new R engine
            String rArgs[] = {"--vanilla"};
            re = new Rengine(rArgs, false, null);

            if (!re.waitForR()) {
                //Can't make connection with R, program will be stopped immediately
                System.out.println("Cannot load R");
                re = null;
            }
            re.eval("library(rJava)");

            // Calls the script where all the calculations will be made
            re.eval("source('/srv/molgenis/rScript/callChromstaROptions.R')");
            System.out.println("source('/srv/molgenis/rScript/callChromstaROptions.R')");

        }

        //Returns the connection with R 
        return re;
    }
}
