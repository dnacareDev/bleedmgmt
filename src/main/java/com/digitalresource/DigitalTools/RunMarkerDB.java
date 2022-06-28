package com.digitalresource.DigitalTools;

import java.io.*;
import java.util.*;
import java.text.*;

public class RunMarkerDB{
    
		//public static void main(String[] args) {
  
	public  void MakeRunMarkerDB(String jobid) {

		

		String r_path = "/data/apache-tomcat-9.0.8/webapps/ROOT/common/r/";
		String result_path = r_path+"qtl/"+jobid+"/";

		String cmd = "Rscript " +r_path+"markerDB_merge.R "+jobid;

		System.out.println(" RunMarkerDB cmd : " + cmd);
		execute(cmd);
    }
	
	 public static void execute(String cmd) {
        Process process = null;
        Runtime runtime = Runtime.getRuntime();
        StringBuffer successOutput = new StringBuffer(); // suc string buffer
        StringBuffer errorOutput = new StringBuffer(); //  err string buffer
        BufferedReader successBufferReader = null; // suc buffer
        BufferedReader errorBufferReader = null; //  err buffer
        String msg = null; // msg
 
        List<String> cmdList = new ArrayList<String>();
 
		cmdList.add("/bin/sh");
		cmdList.add("-c");
        
        // cmd setting
        cmdList.add(cmd);
		   String[] array = cmdList.toArray(new String[cmdList.size()]);
 
		   try { 
            // cmd exec
            process = runtime.exec(array);

			successBufferReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "EUC-KR"));
 
            while ((msg = successBufferReader.readLine()) != null) {
                //successOutput.append(msg + System.getProperty("line.separator"))
				System.out.println(msg);
            }
 
            errorBufferReader = new BufferedReader(new InputStreamReader(process.getErrorStream(), "EUC-KR"));
           
			while ((msg = errorBufferReader.readLine()) != null) {
                //errorOutput.append(msg + System.getProperty("line.separator"));
				System.out.println(msg);
            }

			process.waitFor();
 
            if (process.exitValue() == 0) {
                System.out.println("Success "+"\n");
                //System.out.println(successOutput.toString());
            } else {
                System.out.println("Fail "+"\n");
                //System.out.println(errorOutput.toString());
            }
  
        } catch (IOException e) {
        	System.out.println("error");
        } catch (InterruptedException e) {
        	System.out.println("error");
        } finally {
            try {
            	//process.destroy();
            	if (process != null) process.destroy();
                if (successBufferReader != null) successBufferReader.close();
                if (errorBufferReader != null) errorBufferReader.close();
            } catch (IOException e1) {
            	System.out.println("error");
            }
        }
    }
}
