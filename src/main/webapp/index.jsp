<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Eriba</title>
        <link rel="stylesheet" type="text/css" href="css/style.css" />
    </head>

        <body>
        <div id="main">
            
           <div id="header">  
                <h1><span>Eriba</span></h1>	
                <h2>European Research Institute for the Biology of Ageing</h2>
            </div><!--close header-->			
            <div class="container_header"><p>&nbsp;</p></div>
                <div id="site_background"> 	
            <div id="site_content">
                <div id="menubar">
                    <ul class="lavaLampWithImage" id="lava_menu">
                        <li class="selected"><a href="#">Home</a></li>
                        <li><a href="html/onlineTool.jsp">Online Tool</a></li>
                        <li><a href="html/tutorial.jsp">Tutorial</a></li>
                        <li><a href="html/download.jsp">Download</a></li>
                        <li><a href="html/sampleData.jsp">Sample Data</a></li>
                        <li><a href="html/sampleGraphics.jsp">Sample Graphics</a></li>
                        <li><a href="html/faq.jsp">FAQ</a></li>
                        <li><a href="html/citation.jsp">Citation</a></li>
                        <li><a href="html/contact.jsp">Contact Us</a></li>
                      </ul>
                </div><!--close menubar-->	
                <div id="content">
                    <div class="content_item">
                        <div class='about'>
                            <h1>Welcome</h1>
                            <div style="width:200px; float:left; padding: 0px 20px 10px 0px;"><img alt="image" src="img/image.jpg" /></div>
                            <p>
                               Lorem ipsum dolor sit amet, consectetur adipiscing 
                                elit. Sed quis enim fringilla, efficitur sem at, 
                                porta justo. Nullam sit amet consectetur velit, at 
                                laoreet eros. Nunc sit amet maximus metus. Sed 
                                sollicitudin maximus pellentesque. Vestibulum 
                                fermentum libero neque, quis pretium nibh facilisis 
                                molestie. Curabitur quis tincidunt quam, nec auctor 
                                ante. Curabitur non risus a ante tincidunt pharetra 
                                vitae eu dui. Aenean vel turpis viverra, posuere 
                                nunc et, tempus urna. Aliquam tristique mauris 
                                magna, aliquam lobortis nibh dictum eget. 
                            </p>
                        </div>
                    </div><!--close content_item-->	
                </div><!--close content-->	
                </div><!--close site_content-->	
            </div><!--close site_background-->	

            <%@include file='includes/footer.jsp' %>
        
        </div><!--close main-->	
    </body>
</html>
