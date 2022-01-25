package com.digitalresource.RModule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RunCorrlation {

  public void MakeCorrplot(String pheno_list, String path, String pfilename) {

//    String r_path = "/data/apache-tomcat-9.0.8/webapps/ROOT/common/r/";
    String r_path = "/data/";
    String phenotype_file = path + "r_plot/corrplot/" + pfilename + "/" + pfilename + "_phenotype_list.txt";
    String corrplotfile = path + "r_plot/corrplot/" + pfilename + "/" + pfilename + "_corrplot_whitespace.png";

    String cmd = "Rscript " + r_path + "pc_corrplot.R " + " " + phenotype_file + " " + pheno_list + " " + corrplotfile;

    System.out.println(" RunCorrlation cmd : " + cmd);
    execute(cmd);
    String convert_whitespace = "convert " + path + "r_plot/corrplot/" + pfilename + "/" + pfilename + "_corrplot_whitespace.png" + " -trim -bordercolor White +repage " + path + "r_plot/corrplot/" + pfilename + "/" + pfilename + "_corrplot.png";
    execute(convert_whitespace);
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
        System.out.println("Success " + "\n");
        //System.out.println(successOutput.toString());
      } else {
        System.out.println("Fail " + "\n");
        //System.out.println(errorOutput.toString());
      }

    } catch (IOException e) {
      System.out.println(e);
      e.printStackTrace();
    } catch (InterruptedException e) {
      System.out.println(e);
      e.printStackTrace();
    } finally {
      try {
        process.destroy();
        if (successBufferReader != null) successBufferReader.close();
        if (errorBufferReader != null) errorBufferReader.close();
      } catch (IOException e1) {
        System.out.println(e1);
        e1.printStackTrace();
      }
    }
  }
}