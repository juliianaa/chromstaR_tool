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
 *
 * @author Eriba
 */
public class Jobs {

    final private static AtomicInteger jobCounter = new AtomicInteger();
    final private static Map<Integer, Future<String>> jobs = new ConcurrentHashMap<>();

    final private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static int startJob(final ArrayList<String> fileNames, final String userDir,
            final String tmpDir, String arguments,
            final ArrayList<Object> checked_functions, final int numberOfAnalyses,
            final String resultsDirName) {

        Callable<String> jobje = () -> {
            String arguments1 = null;
            try {
                //Tries to call the class/method JavaRIntegration and gives the Map.
                System.out.println("call Java R integration START OF PROGRAM");
                System.out.println(userDir + "    " + tmpDir + "    \n" + fileNames
                        + "    \n" + checked_functions + "    " + numberOfAnalyses
                        + "    \n" + "    " + resultsDirName);
                JavaRIntegration calculateWithR = new JavaRIntegration();
                arguments1 = calculateWithR.start(userDir, tmpDir, fileNames,
                        checked_functions, numberOfAnalyses, resultsDirName);
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

    public static boolean isFinished(int jobNumber) {
        Future<String> job = jobs.get(jobNumber);
        if (job == null) {
            throw new IllegalStateException("Unknown job!");
        }
        return job.isDone();
    }

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
