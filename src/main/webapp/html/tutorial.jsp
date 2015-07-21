<%-- 
    Document   : tutorial
    Created on : Feb 2, 2015, 4:44:26 PM
    Author     : Eriba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Eriba</title>
        <link rel="stylesheet" type="text/css" href="../css/style.css" />
        <script src="../js/jquery.js" type="text/javascript"></script>
        <script src="../js/jquery-ui.js" type="text/javascript"></script>
        <script type="text/javascript" src='../js/accordion.js'></script>
    </head>

        <body>
        <div id="main">
            <%@include file='../includes/menu.jsp' %>	
                <div id="content">
                    <div class="content_item">
                        <div class='about'>
                            <h1>Tutorial</h1>
                            <div id="accordion">
                                <h3>R package</h3>
                                <div>
                                  <p>
                                      Explanation + tutorial
                                      <br style="clear:both" />
                                      Download R package (Link here)
                                      <br style="clear:both" />
                                      Link to bioconductor for libraries
                                  </p>
                                </div>
                                <h3>Online Tool</h3>
                                <div>
                                  <p>
                                      Explanation + tutorial
                                      <br style="clear:both" />
                                      .....
                                  </p>
                                </div>
                          </div>
                    </div><!--close content_item-->	
                </div><!--close content-->	
                </div><!--close site_content-->	
            </div><!--close site_background-->	
            <%@include file='../includes/footer.jsp' %>
        </div><!--close main-->	
    </body>
</html>
