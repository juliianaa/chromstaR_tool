<%-- 
    Document   : histonehmm
    Created on : Mar 19, 2015, 2:01:03 PM
    Author     : jwlgoh
--%>

<div id="changeContent" >
    <script src="../js/callServlet.js" type="text/javascript"></script>
    <script src="../js/handlesAnalysisSettings.js" type="text/javascript"></script>

    <h1>Tool</h1>

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
    <br style="clear:both" />

    <a href="generateRscript.jsp">Generate R script</a>

    <br style="clear:both" />
    <br style="clear:both" />

    <div id="options">


        <h3>Upload file(s)</h3>
        <div>
            <form>
                <label class="sampleFile">Upload File:</label> 
                <input id="sampleFile" name="sampleFile" type="file"  multiple=""/><br/>
                <br style="clear:both" /> 
            </form>
        </div> 
        <div>
            <p>Example of advanced options!!! Need to change later on</p>
            <form id="advancedForm">
                <input class="option" type="checkbox" name="defaultOption" value="Default">Default<br /> 
                <input class="option" type="checkbox" name="advancedOption" value="Advanced">Advanced<br />
                <label class="advanced">Bins:</label> <input class="advanced" type="number" name="rFunctions[]" value="500"><br />
                <label class="advanced">Univariate max time: </label> <input class="advanced" type="number" name="rFunctions[]" value="1000"><br />
                <label class="XtraAdvanced">Multivariate max time: </label> <input class="XtraAdvanced" type="number" name="rFunctions[]" value="1000"><br />
                <input class="numberOfAnalysis" name="hiddenValue" type="hidden" value="1"><br />
                <input class="tmpDirPath" name="hiddenValue" type="hidden" value="none"><br />
                <input class="userDirPath" name="hiddenValue" type="hidden" value="none"><br />
            </form>
        </div> 

        <button id="uploadBtn" onClick="performAjaxUpload()">Run analysis</button>

    </div>
</div>

