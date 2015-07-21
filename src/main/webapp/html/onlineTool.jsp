<%-- 
    Document   : tutorial
    Created on : Feb 2, 2015, 4:44:26 PM
    Author     : jwlgoh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Eriba</title>
        <link rel="stylesheet" type="text/css" href="../css/style.css" />
        <link href="../css/nprogress.css" rel="stylesheet" type="text/css"/>
        <script src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
        <script src="https://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
        <script src="../js/jquery-ui.js" type="text/javascript"></script>
        <script src="../js/callServlet.js" type="text/javascript"></script>
        <script src="../js/accordion.js" type="text/javascript"></script>
        <script src="../js/handlesAnalysisSettings.js" type="text/javascript"></script>
        <script src="../js/nprogress.js" type="text/javascript"></script>
    </head>

        <body>
        <div id="main">
            <%@include file='../includes/menu.jsp' %>	
                <div id="content">
                    <div class="content_item">
                        <div class='about'>
                            <div id="resultsTest">
                                
                                <p id="download"></p>
                                
                                <button id="performButton" onclick="showAnlaysisSettings()">Perform another analysis </button>
                                
                                
<!--                                <p id="test">
                                    <form>
                                        <input type="hidden" id='downloadLink' name="downloadResults" value="downloadPath"><br>
                                        <input type="submit" value="Download" onclick="DownloadResultsAnalysis()">
                                    </form>
                                </p>-->
                                
                            </div>
                            
                            <br style="clear:both" />
                            
                            <div id="changeContent">
                                <%@include file="histonehmm.jsp" %>
                            </div>
                            
                    </div><!--close content_item-->	
                </div><!--close content-->
                </div><!--close site_content-->	
            </div><!--close site_background-->	
            <%@include file='../includes/footer.jsp' %>
        </div><!--close main-->	
    </body>
</html>
