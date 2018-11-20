package com.rahul.UtilSet;

import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import java.io.OutputStream;
import org.apache.commons.io.IOUtils;

import java.util.ArrayList;
import java.io.File;
import java.lang.ProcessBuilder;
import java.lang.ProcessBuilder.Redirect;

@SuppressWarnings("unchecked")
public class UtilCLIExecution {
	public UtilCLIExecution()
	{
	}
	public boolean ExecCommand(String lCmd, String lLogPath, String lLogFileName)
	{
		try {
			String[] cmd = lCmd.split(" ");
			ProcessBuilder pb =new ProcessBuilder(cmd);
			pb.directory(new File(lLogPath));
			File log = new File(lLogFileName);
			pb.redirectErrorStream(true);
			pb.redirectOutput(Redirect.appendTo(log));
			Process p = pb.start();
			assert pb.redirectInput() == Redirect.PIPE;
			assert pb.redirectOutput().file() == log;
			assert p.getInputStream().read() == -1;

			return true;
		} catch (Exception e) {
			System.out.println("ExecCommand-> lCmd:" + lCmd);
			e.printStackTrace();
			return false;
		}
	}
	public boolean IsProcessRunning(String lProcessName)
	{
		String script = "/tmp/IsProcessRunning.sh";
		if(!IsFilePresent(script))
		{
			System.out.println("file not present: " + script);
			CreateIsProcessRunningScript();
		}

		ArrayList<String> al = new ArrayList<String>();
		al = ExecCommandWithRsp("sh /tmp/IsProcessRunning.sh " + lProcessName);
		if(0 != al.size())
			if(al.get(0).equals("Running"))
				return true;
			else
				return false;
		return false;
	}

	public boolean StopProcess(String lProcessName)
	{
		String script = "/tmp/StopRunningProcess.sh";
		if(!IsFilePresent(script))
		{
			System.out.println("file not present: " + script);
			CreateStopRunningProcessScript();
		}

		ArrayList<String> al = new ArrayList<String>();
		al = ExecCommandWithRsp("sh /tmp/StopRunningProcess.sh " + lProcessName);
		if(0 != al.size())
			return true;
		else
			return false;
	}

	private void CreateStopRunningProcessScript()
	{
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/tmp/StopRunningProcess.sh"), StandardCharsets.UTF_8))) {
			writer.write("#!/bin/bash "
					+ "\nkill `ps -ef | grep $1 | grep -v 'grep' | grep -v $0 | awk {'print $2'}`");
		} 
		catch (IOException e) {
			System.out.println("CreateStopRunningProcessScript->");
			e.printStackTrace();
		}  
	}

	private void CreateIsProcessRunningScript()
	{
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/tmp/IsProcessRunning.sh"), StandardCharsets.UTF_8))) {
			writer.write("#!/bin/bash "
					+ "\nps_out=`ps -ef | grep $1 | grep -v 'grep' | grep -v $0`"
					+ "\nresult=$(echo $ps_out | grep \"$1\")"
					+ "\nif [ \"$result\" != \"\" ];then"
					+ "\n	echo \"Running\""
					+ "\nfi");
		} 
		catch (IOException e) {
			System.out.println("CreateIsProcessRunningScript->");
			e.printStackTrace();
		}  
	}

	public boolean IsFilePresent(String lFileName)
	{
		File f = new File(lFileName);
		if(!f.exists())
			return false;

		return true;
	}

	public ArrayList<String> ExecCommandWithRsp(String lCmd)
	{
		ArrayList<String> al = new ArrayList<String>();
		try {
			String[] cmd = lCmd.split(" ");
			Process p3 = Runtime.getRuntime().exec(cmd);
			OutputStream output = p3.getOutputStream();
			output.close(); // signals grep to finish
			al = new ArrayList<String>(IOUtils.readLines(p3.getInputStream()));
			return al;
		} catch (Exception e) {
			System.out.println("ExecCommandWithRsp-> lCmd:" + lCmd);
			e.printStackTrace();
			return al;
		}
	}
	public static void main(String args[]) {

		UtilCLIExecution obj = new UtilCLIExecution();
		/*
		   obj.CreateIsProcessRuningScript();
		   if(!obj.IsProcessRunning("module"))
		   System.out.println("not running");
		   else
		   System.out.println("Running");
		   */
obj.CreateStopRunningProcessScript();
obj.CreateIsProcessRunningScript();
		//obj.StopProcess("moduleV1");
		/*
		   String s;
		   Process p;
		   try {
		   p = Runtime.getRuntime().exec("ls -aF");
		   BufferedReader br = new BufferedReader(
		   new InputStreamReader(p.getInputStream()));
		   while ((s = br.readLine()) != null)
		   System.out.println("line: " + s);
		   p.waitFor();
		   System.out.println ("exit: " + p.exitValue());
		   p.destroy();
		   } catch (Exception e) {}
		   */
	}
}
