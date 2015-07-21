<%-- 
    Document   : download
    Created on : Feb 4, 2015, 1:12:10 PM
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
                         <div class="about">
                            <h1>Download</h1>
                            
                            <div id='accordion'>
                                <h3>Download R package</h3>
                                <div>
                                    EXPLANATION
                                    <br style="clear:both" />
                                    Download package: .......link here......
                                </div> 
                                <h3>Bioconductor libraries</h3>
                                <div>
                                    <ul>
                                        <li>
                                            GenomicRanges explanation?
                                            <br style="clear:both" />
                                            link : <a href='http://www.bioconductor.org/packages/release/bioc/html/GenomicRanges.html' target="_blank">GenomicRanges</a>
                                        </li>
                                        <li>
                                            
                                            IRanges explanation?
                                            <br style="clear:both" />
                                            link : <a href='http://www.bioconductor.org/packages/release/bioc/html/IRanges.html' target="_blank">IRanges</a>
                                        </li>
                                        <li>
                                            Rsamtools explanation?
                                            <br style="clear:both" />
                                            link : <a href='http://www.bioconductor.org/packages/release/bioc/html/Rsamtools.html' target="_blank">Rsamtools</a>
                                        </li>
                                        <li>
                                            BiocGenerics explanation?
                                            <br style="clear:both" />
                                            link : <a href='http://www.bioconductor.org/packages/release/bioc/html/BiocGenerics.html' target="_blank">BiocGenerics</a>
                                        </li>
                                        <li>
                                            GenomicAlignments explanation?
                                            <br style="clear:both" />
                                            link : <a href='http://www.bioconductor.org/packages/release/bioc/html/GenomicAlignments.html' target="_blank">GenomicAlignments</a>
                                        </li>
                                    </ul>
                                    
                                </div> 
                                <h3>Download ...</h3>
                                <div>
                                    EXPLANATION
                                    <br style="clear:both" />
                                    Link
                                </div> 
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

