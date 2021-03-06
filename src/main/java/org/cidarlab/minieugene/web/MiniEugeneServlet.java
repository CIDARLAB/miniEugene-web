/*
 * Copyright (c) 2014, Boston University
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or 
 * without modification, are permitted provided that the following 
 * conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright 
 *    notice, this list of conditions and the following disclaimer.
 *    
 * 2. Redistributions in binary form must reproduce the above copyright 
 *    notice, this list of conditions and the following disclaimer in 
 *    the documentation and/or other materials provided with the distribution.
 *    
 * 3. Neither the name of the copyright holder nor the names of its 
 *    contributors may be used to endorse or promote products derived 
 *    from this software without specific prior written permission.
 *    
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS 
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT 
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR 
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT 
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, 
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED 
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING 
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.cidarlab.minieugene.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cidarlab.minieugene.MiniEugene;
import org.cidarlab.minieugene.MiniEugeneStatistics;
import org.cidarlab.minieugene.stats.Measurement;
import org.cidarlab.minieugene.util.SolutionExporter;
import org.cidarlab.minieugene.web.data.ScriptCollector;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * The MiniEugeneServlet class implements the Back-End Logic of 
 * the miniEugene web application. It only serves HTTP POST requests.
 * 
 * @author Ernst Oberortner
 */
public class MiniEugeneServlet 
	extends HttpServlet {

	private static final long serialVersionUID = 1608090753599454559L;
	
	private static final String SOLVE = "solve";
	private static final int NR_OF_SOLUTIONS = 50000;
	
	private static String DATA_DIRECTORY;
	private static String PIGEON_DIRECTORY;
	private static String SBOL_DIRECTORY;
	private static String EUGENE_DIRECTORY;
	
    @Override
    public void init()
            throws ServletException {
        super.init();
    }

    @Override
    public void destroy() {
    }

    
    public void init(ServletConfig servletConfig) 
    		throws ServletException{
    	super.init(servletConfig);
    	
    	// create the data directory
    	DATA_DIRECTORY = Paths.get(this.getServletContext().getRealPath(""), "data", "scripts").toString();
        new File(DATA_DIRECTORY).mkdirs();

		// DIRECTORY FOR PIGEON IMAGES
		PIGEON_DIRECTORY = Paths.get(this.getServletContext().getRealPath(""), "data", "pigeon").toString();
        new File(PIGEON_DIRECTORY).mkdirs();

        // DIRECTORY FOR EUGENE FILES
        EUGENE_DIRECTORY = Paths.get(this.getServletContext().getRealPath(""), "data", "eugene").toString();
        new File(EUGENE_DIRECTORY).mkdirs();

        // DIRECTORY FOR SBOL FILES
        SBOL_DIRECTORY = Paths.get(this.getServletContext().getRealPath(""), "data", "sbol").toString();
        new File(SBOL_DIRECTORY).mkdirs();
    }
    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	// not GET requests allowed ...
    	throw new ServletException("Invalid request!");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
    		throws IOException {
        processPostRequest(request, response);
    }

    /**
     * Returns a short description of the Servlet.
     *
     * @return a String containing the servlet's description
     */
    @Override
    public String getServletInfo() {
        return "MiniEugeneServlet serving HTTP POST requests";
    }

    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response) 
               throws IOException {

        JSONObject result = new JSONObject();
        
    	PrintWriter out = null;
        try {
            response.setContentType("application/json");
            out = response.getWriter();
            String command = request.getParameter("command");
            
            /*
             * SOLVE the problem
             */
            if (MiniEugeneServlet.SOLVE.equals(command)) {
            	
            	// read the parameters from the request
            	int sizeOfDesign = Integer.parseInt(
                        request.getParameter("N"));
                String input = request.getParameter("input");                    
                
                /*
                 * execute miniEugene to solve the problem
                 */
               	result = executeMiniEugene(
                       	request.getSession().getId(), 
                       	sizeOfDesign, input, NR_OF_SOLUTIONS);
                
               	/*
               	 * return the results to the client
               	 */
               	out.write(result.toString());
            } else {
            	result.put("status", "bad");
            }
        } catch (Exception e) {
        	try {
        		result.put("status", "exception");
        	} catch(Exception exc) {}
        } finally {
            out.flush();
            out.close();
        }
    }


    /**
     * The executeMiniEugene method gets as input the request parameters of 
     * the HTTP POST request and instructs miniEugene to solve the problem. 
     *  
     * @param sessionId
     * @param N
     * @param input
     * @param nrOfSolutions
     * @return
     */
    public JSONObject executeMiniEugene(
            String sessionId, 
            int N, String input, int nrOfSolutions) {

        JSONObject returnJSON = new JSONObject();
        
    	/*
    	 * first, we instantiate miniEugene
    	 */
    	MiniEugene me = new MiniEugene();

    	/*
    	 * then, we turn the input string into 
    	 * an array of strings
    	 */
    	String[] rules = input.split(
    			System.getProperty("line.separator"));

    	try {
        	
        	/*
        	 * then, we build the script
        	 */
        	String script = this.buildScript(N, rules);

        	/*
    		 * for ``data'' collection
    		 * 
    		 * IDEA: I can relate the rules to the SBOL file
    		 * -> we can then do a lot of interesting stuff...
    		 */                    
    		collectScript(
    				sessionId, script);


            /*
             * then, we solve the problem
             * finding ALL solutions 
             */    
        	me.solve(script, nrOfSolutions);

            /*
             * SolutionExporter, what else?
             */
            SolutionExporter se = new SolutionExporter(
            		me.getSolutions(),
            		me.getInteractions());

            String uuid = UUID.randomUUID().toString();

            long T1 = System.nanoTime();
            
            // pigeon
            String imageName = PIGEON_DIRECTORY+"/"+uuid+".png";
            se.pigeonize(imageName, null, true, 10);
            returnJSON.put("pigeon", "data/pigeon/"+uuid+".png");

            // Eugene 
            String eugeneFile = EUGENE_DIRECTORY+"/"+uuid+".eug";
            se.toEugene(eugeneFile);
            returnJSON.put("eugene", "data/eugene/"+uuid+".eug");
            
            // SBOL
            String sbolFile = SBOL_DIRECTORY+"/"+uuid+".sbol";
            se.toSBOL(sbolFile);            
            returnJSON.put("sbol", "data/sbol/"+uuid+".sbol");
        	
            long T2 = System.nanoTime();
            
            me.getStatistics().add("Solution Processing Time [sec]", (T2-T1) * Math.pow(10, -9));
            
            // statistics
            returnJSON.put("statistics", processStatistics(me.getStatistics()));
            
            // everything's good
            returnJSON.put("status", "good");

        } catch(Exception e) {
        	e.printStackTrace();
            try {
                returnJSON.put("status", "exception");
                returnJSON.put("results", e.getMessage());
            } catch(JSONException jse) {}
        }

        /*
         * regardless what happened, we visualize the ACT
         */
    	try {
    		returnJSON.put("act-uri", me.visualizeACT().toString());
    	} catch(Exception e) {
    		// ignore
    	}
    	
        return returnJSON;
    }
    
    /**
     * The buildScript(int, String[]) method compiles 
     * a miniEugene web-app script into the miniEugene language.
     * That is, it compiles the length of the design into 
     * a miniEugene "N=n." and it replaces LINE FEEDS with dots ('.')
     * 
     * @param N  ... the length of the design
     * @param rules ... an array of rules
     * @return
     */
	public String buildScript(int N, String[] rules) {
		StringBuilder sb = new StringBuilder();

		// N = number .
		sb.append("N=").append(N).append(".").append("\r\n");
		
		// rules
		for(int i=0; i<rules.length; i++) {
			if(!rules[i].trim().isEmpty()) {
				if(!rules[i].startsWith("//")) {
					sb.append(rules[i]);
					if(!rules[i].endsWith(".")) {
						sb.append(".").append("\r\n");
					}
				}
			}
		}

		return sb.toString();
	}
    

	/**
	 * The processStatistics method generates HTML code for displaying 
	 * the statistic of a miniEugene run.
	 *  
	 * @param mes ... an object of statistics
	 * 
	 * @return a String the contains a HTML representation of the statistics
	 */
    private String processStatistics(MiniEugeneStatistics mes) {
    	
    	StringBuilder sb = new StringBuilder();
    	sb.append("<div><table class=\"table table-bordered table-hover\" id=\"outputList\">")
    	  .append("<thead><tr><th>Name</th><th>Value</th><th></th></tr></thead><tbody>");
    	
    	if (null != mes && !mes.isEmpty()) {
        	
        	for(Measurement m : mes.getMeasurements()) {
        		sb.append("<tr><td>").append(m.getKey()).append("</td><td>")
        			.append(m.getValue()).append("</td></tr>");
        	}
        }
    	sb.append("</tbody></table></div>");
    	return sb.toString();
    }
    
    /**
     * The collectScript method stores the executed miniEugene scripts 
     * into the miniEugene DATA_DIRECTORY.
     *  
     * @param sessionId
     * @param script
     */
    private void collectScript(String sessionId, String script) {
    	/*
    	 * get the path (of the servlet context)
    	 */
    	String scriptPath = Paths.get(DATA_DIRECTORY, sessionId).toString();

        /*
         * start a thread (the script collector) that 
         * writes the script to the given file
         */
    	new ScriptCollector(scriptPath, script).run();    	
    }
}
