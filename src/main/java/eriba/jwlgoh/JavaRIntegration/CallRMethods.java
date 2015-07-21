/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eriba.jwlgoh.JavaRIntegration;

import java.util.ArrayList;
import org.rosuda.JRI.Rengine;


/**
 * This class only has one method, which will be calling the method to make connection with R and
 * gives the parameters to the function in the R-script.
 *
 * @author jwlgoh
 */
public class CallRMethods {
    
    
    private final ConnectWithR r = new ConnectWithR();
    
    /**
     * Upon receiving the parameters, this method will be calling the function in the R-script 
     * with the needed parameters for the calculations. 
     * 
     * @param analysisOption
     * @param tmp_dir
     * @param noaPath
     * @param options
     */
    public void runRFunction(String tmp_dir, ArrayList options, String noaPath){
        
        System.out.println("call r methods program");
        
        try{
            //Receives the R connection
            Rengine re = r.rConnect();
            
            System.out.println(re);
            
            System.out.println("inputOption('" + tmp_dir + "','" + noaPath + 
                    "',\n" + options.get(0) + "," + options.get(1) +
                    "," + options.get(2) + ")");
            

             
            //How the parameters will be given to the R-script function
            re.eval("inputOption('" + tmp_dir + "','" + noaPath + "'," 
                    + options.get(0) + "," + options.get(1) +
                    "," + options.get(2) + ")");


        }catch(NullPointerException e){
                System.out.println("error call R methods: " + e);
        }
        
        System.out.println("done with calculations");
    }
  
}
