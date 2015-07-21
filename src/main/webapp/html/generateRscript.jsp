<%-- 
    Document   : generateRscript
    Created on : Apr 15, 2015, 4:51:53 PM
    Author     : Eriba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
     <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Eriba</title>
        <link rel="stylesheet" type="text/css" href="../css/style.css" />
        <link href="../css/nprogress.css" rel="stylesheet" type="text/css"/>
        <script src="../js/jquery.js" type="text/javascript"></script>
        <script src="../js/jquery-ui.js" type="text/javascript"></script>
        <script src="../js/callServlet.js" type="text/javascript"></script>
        <script src="../js/nprogress.js" type="text/javascript"></script>
    </head>
        <body>
        <div id="main">
            <%@include file='../includes/menu.jsp' %>	
                <div id="content">
                    <div class="content_item">
                        <div class='about'>
                            <h1>Generate R script</h1>
                            
                            <div>
                                Generating a R script that can be used in R for the analysis with the 
                                R package chromstaR
                            </div>
                            
                            <br style="clear:both" />
                            
                            <div id="settings">
                                <div>        
                                    <form id="advancedForm">
                                        <label>Path:</label><input type="text" size="50" id="pathToFiles"><br /> 
                                        <label>Bins:</label> <input type="number" name="rFunctions[]" value="800"><br />
                                        <label>Univariate max time: </label> <input type="number" name="rFunctions[]" value="30"><br />
                                        <label>Multivariate max time: </label> <input type="number" name="rFunctions[]" value="300"><br />
                                    </form>
                                </div> 

                                <button id="uploadBtn" onClick="performGeneratingScript()">Generate script</button>
                            
                            </div>
                            
                            <div id="rScript">
                               
                            </div>
                                

                            
                           
                    </div><!--close content_item-->	
                </div><!--close content-->	
                </div><!--close site_content-->	
            </div><!--close site_background-->	
            <%@include file='../includes/footer.jsp' %>
        </div><!--close main-->	
    </body>
</html>

