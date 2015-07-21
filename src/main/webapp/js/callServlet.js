/**
 * 
 * Ajax function where the given files and checked functions will be received 
 * and send to the Servlet once being parsed and set in the correct format.
 * If data is set in the correct format this will allow the Servlet to parse the 
 * parameters without a problem.
 *   
 */

function performAjaxUpload() {

    NProgress.start();

    //Used for parsing multipart/form-data
    var formdata = new FormData();

    //gets the files that were given by the user
    var sampleFile = document.getElementById("sampleFile").files;

    //Loops through the list of files
    for (i = 0; i < sampleFile.length; i++) {
        //Appends the files to a formdata that easily can be use for HTML forms multipart/form-data
        formdata.append("sampleFile", sampleFile[i]);
    }

    var checkbox_value = [];
    //Goes through all the checked functions
    $(":checkbox").each(function () {
        var ischecked = $(this).is(":checked");
        if (ischecked) {
            //If function is checked, then add to list
            checkbox_value.push($(this).val());
        }
    });

    var arguments_value = [];
    $('form input[type="number"]').each(function () {
        arguments_value.push($(this).val());
    });

    var hidden_value = [];
    $('form input[type="hidden"]').each(function () {
        if ($(this).val() !== 'downloadPath') {
            hidden_value.push($(this).val());
        }

    });

    //adds the list of checked function
    formdata.append("rSettings", checkbox_value);
    formdata.append("argParams", arguments_value);
    formdata.append("hiddenValues", hidden_value);

    NProgress.inc();

    $.ajax({
        url: '../FileUploadServlet',
        data: formdata,
        processData: false,
        contentType: false,
        type: 'POST',
        success: function (jobNumber) {
            setTimeout(function () {
                checkJob(jobNumber);
            }, 30000);
        },
        fail: function (jqXHR, textStatus) {
            alert("Anlaysis failed: " + textStatus);
        }
    });
}

function checkJob(jobNumber) {
    $.ajax({
        url: '../JobServlet?jobNumber=' + jobNumber,
        type: 'GET',
        success: function (status) {
            if (status === 'Still running') {
                // job is still running, so poll again
                setTimeout(function () {
                    checkJob(jobNumber);
                }, 20000);
            } else {
                showResult(status);
                NProgress.done();
            }
        },
        fail: function (jqXHR, textStatus) {
            alert("Anlaysis failed: " + textStatus);
        }
    });
}

function showResult(data) {
    // voor als de job done is
    var values = data.split(",");
    $('#options').hide();
    $('#resultsTest').show();
    $('#download').html("<h3>Download results:</h3> <a target='_blank' href='../DownloadZipFileServlet?zipPath=" + values[0] + "' onclick='refreshPage()'>Download</a>");
    $(".tmpDirPath").val(values[1]);
    $(".userDirPath").val(values[0]);
    $(".numberOfAnalysis").val(values[2]);
    $("#performButton").show();

}


function refreshPage() {
    //reloads the online tool page as the user has downloaded the results
    location.reload(true);
}

function performGeneratingScript() {
    NProgress.start();

    var formData = new FormData();

    //gets the files that were given by the user
    var pathToFile = document.getElementById("pathToFiles").value;

    var arguments_values = [];
    $('form input[type="number"]').each(function () {
        arguments_values.push($(this).val());
    });

    formData.append("pathToFile", pathToFile);
    formData.append("argParams", arguments_values);


    NProgress.inc();

    $.ajax({
        url: '../GenerateRScript',
        data: formData,
        processData: false,
        contentType: false,
        type: 'POST',
        success: function (data) {
            NProgress.done();
            $('#settings').hide();
            $("#textArea").show();
            $('#rScript').html(data);

        }
    });
}

