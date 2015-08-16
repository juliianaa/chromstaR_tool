/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eriba.jwlgoh.JavaRIntegration;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class contains three methods which starts a job and checks if a job is 
 * finished. If the job is finished the result will be retrieved.
 *
 * @author jwlgoh
 */
public class Jobs {

    final private static AtomicInteger jobCounter = new AtomicInteger();
    final private static Map<Integer, Future<String>> jobs = new ConcurrentHashMap<>();

    final private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    /**
     * This method starts the job from a user. The job receives multiple variables
     * and will send it to the program to start the analysis of the user.
     * 
     * @param fileNames
     * @param wrongFiles
     * @param checked_functions
     * @param user_dir
     * @param tmp_dir
     * @return
     */
    public static int startJob(final ArrayList<String> fileNames,
            final ArrayList<Object> checked_functions, final ArrayList<String> wrongFiles,
            String user_dir, String tmp_dir) {

        Callable<String> jobje = () -> {
            String arguments1 = null;
            try {
                //Tries to call the class/method JavaRIntegration and gives the Map.
                System.out.println("call Java R integration START OF PROGRAM");
                //System.out.println(fileNames + "    " + checked_functions + "    \n" + wrongFiles);
                JavaRIntegration calculateWithR = new JavaRIntegration();
                arguments1 = calculateWithR.start(fileNames, checked_functions, wrongFiles,
                        user_dir, tmp_dir);
            } catch (NullPointerException e) {
                System.out.println("error servlet: " + e);
            }
            //gives back a response, where the tmp_dir will be given for compressing files to zip
            //in the DownloadZipFileServlet
            System.out.println("args: " + arguments1 + " \n Send to JS");
            return arguments1;
        };

        int jobNumber = jobCounter.incrementAndGet();
        System.out.println("jobNumber: " + jobNumber);
        Future<String> result = executorService.submit(jobje);
        jobs.put(jobNumber, result);

        return jobNumber;
    }

    /**
     * This method checks if the job is done with the help of the job number.
     * 
     * @param jobNumber
     * @return
     */
    public static boolean isFinished(int jobNumber) {
        Future<String> job = jobs.get(jobNumber);
        if (job == null) {
            throw new IllegalStateException("Unknown job!");
        }
        return job.isDone();
    }

    /**
     * This method checks if the job is finished with the help of the job number.
     * 
     * @param jobNumber
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static String getResult(int jobNumber) throws ExecutionException, InterruptedException {
        Future<String> job = jobs.get(jobNumber);
        if (job == null) {
            throw new IllegalStateException("Unknown job!");
        }
        if (!job.isDone()) {
            throw new IllegalStateException("Job still running");
        }
        System.out.println("get results from jobs.java " + job.get());
        return job.get();
    }

}
