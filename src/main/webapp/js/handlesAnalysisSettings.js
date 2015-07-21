/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function(){
    
    $("#resultsTest").hide();
    $("#performButton").hide();
    
    var sampleFile = document.getElementById("sampleFile").files;

    $(".advanced").hide(); 
    $(".XtraAdvanced").hide(); 
    $(".option").change(function(){
        $(".option").not(this).prop("disabled", this.checked);
        if($(this).val() === "Advanced" && sampleFile.length === 1){
            $(".advanced").show();
        }else{
            if($(this).val() === "Advanced"){
               $(".advanced").show();
               $(".XtraAdvanced").show();           
            }else{
                $(".advanced").hide(); 
                $(".XtraAdvanced").hide(); 
            }
        }
    });    
});

function showAnlaysisSettings(){
    $("#performButton").hide();
    $("#options").show(); 
    $("#sampleFile").hide(); 
}