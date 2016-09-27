var waitAjaxLock = false;

$(document).ready(function () {

      $(window).resize(function() {
        resizeContent();
      });
      
     
      $('#logout').tooltipster( {
            contentAsHTML: true,
            interactive: true,
            trigger: "click",
        
            functionReady: function(){
           
                // $("#addNewPatientBtnGene").button({icons: {primary: "ui-icon-circle-plus"}});
            },
            
            side:"bottom",
            viewportAware:false,
            functionPosition: function(instance, helper, position){
                position.coord.left = position.target - position.size.width+15;
                console.log(position.size.width);
                return position;
            },
            delay:0,
            animationDuration:0,
            minWidth:175,
            maxWidth:575
      });
      
});


function sendLogoutRequest(){
   
    if (waitAjaxLock){return;}
    
    waitAjaxLock = true;
    $.post("logout",function(data){
        waitAjaxLock = false;
        window.location.href = data.redirectAddr;
    }, "json")
    .fail(function(jqXHR, textStatus, errorThrown ) {
        waitAjaxLock = false;
        alert(errorThrown);
    });
                
    
     
}

function resizeContent(){

  var $addPatientElement = $("#addPatientDiv");

  if ($addPatientElement.is(":visible") ){
     $("#patientSearchDiv").height($(window).height()-$addPatientElement.height()-63);
     $("#visitFormDiv").height($(window).height()-$addPatientElement.height()-63);
     $("#visitListDiv").height($(window).height()-$addPatientElement.height()-63);
  }
  else{
       $("#patientSearchDiv").height($(window).height()-63);
       $("#visitFormDiv").height($(window).height()-63);
       $("#visitListDiv").height($(window).height()-63);
  }

}


function editPatient(target){

      var obj = JSON.parse($(target).parent().data("info"));
      var $addPetientElem = $("#addPatientDiv");
     

      if (!$addPetientElem.is(":visible") ){
      
           var currentH = $addPetientElem.height();
           $addPetientElem.slideDown(0);
           $("#patientSearchDiv").height("-="+currentH); 
           $("#visitFormDiv").height("-="+currentH);
           $("#visitListDiv").height("-="+currentH);
          
      }

    
      $("#nameField").val(obj.name);
      $("#surnameField").val(obj.surname);
      $("#commentField").val(obj.comment);
      
      var $radios = $('#leftAddUserFormDiv input:radio[name=gender]');
      $radios.filter('[value='+obj.gender+']').prop('checked', true);
       
      useAdduserDates($("#monthSelectAge"), $("#daySelectAge"), $("#yearSelectAge"), obj.birthDate );
      useAdduserDates($("#monthSelectFirst"), $("#daySelectFirst"), $("#yearSelectFirst"), obj.firstVisit );
      
      
      if ( $("#updatePatientBtn").length===0){
          $("#addPatientBtn").after('<button type="button" id="updatePatientBtn" onclick="addUpdatePatient(&quot;update&quot;)">Update</button>');
      }
      $("#updatePatientBtn").data("id", obj.id);
      
}

function useAdduserDates(monthElem, dayElem, yearElem, date){
      var dateArr = date.split("-");
      if (dateArr.length===3){
          monthElem.val(parseInt(dateArr[1])+"");//removes 0 in 09
          dayElem.val(dateArr[2]);
          yearElem.val(dateArr[0]);
      }
      else{
          monthElem.val("1");
          dayElem.val("");
          yearElem.val("");
      }



}

function deletePatient(target){
      if (waitAjaxLock){return;}
      
      $("#updatePatientBtn").remove();
      $("#patientListHeader p").text('Deleting patient...');
    
    
      var obj = JSON.parse($(target).parent().data("info"));
      var patientId = obj["id"];
      
      waitAjaxLock = true;
     
      
       $.post("deletePatient", {patientId: patientId}, function(){
        
            $(target).parent().remove();
            $("#patientListHeader p").text('Patient deleted successfully');
            waitAjaxLock = false;
            

        }).fail(function( jqXHR,  textStatus, errorThrown) {

             $("#patientListHeader p").text(jqXHR.responseText);
             waitAjaxLock = false;
        });
     
    

}



//VIEW ALL VISITS

function viewVisits(target){
       
      if (waitAjaxLock){return;}

      $("#updatePatientBtn").remove();
      $("#visitFormDiv").css("display", "none");
      $(".page").remove();
      $("#pageNumberDiv").html('');
   
      
    
      $("#visitListDiv").css("display", "inline-block");
      
      $("#patientSearchDiv").css("margin-left","-100%");
      
      var par = $("#visitListDiv .visitMessageDiv p");

      par.text('Retrieving visit list...');
     
        
    

    
      var obj = JSON.parse($(target).parent().data("info"));
        
      var patientId = obj["id"];
   
  
    
      waitAjaxLock = true;
    
    $.get("getVisits",{id:patientId},function(data){
        viewVisitsDisplay(data,obj);
    })
    .fail(function(jqXHR,errorStatus,errorThrown){
        $("#visitListDiv .visitMessageDiv p").text(errorThrown);
        $("#visitListDiv .visitMessageDiv").addClass("mistakeFieldLabel");
        waitAjaxLock = false;
                
    });  
    
}



function viewVisitsDisplay(responseArray, patientData){
   
     
     
                  //      var allData = {clinicians:{name:nameOfClinician, date:visitDate, attendees:attendee}, 
//                          appointmentType:appointmentType, 
//                          plan:{weightGrowthOk:weightGrowthOk,bloodPressureOk:bloodPressureOk, comment:commentPlan, reviewIn:reviewIn},
//                          progressProblems:{parentViewNotes:parentViewNotes, childViewNotes:childViewNotes},
//                          treatmentScores:{inattentionTot:inattentionTot,impulsivityTotal:impulsivityTotal,inattentionMean:inattentionMean,deportmentMean:deportmentMean,cgas:cgas },
//                          allMedications:allMedications,
//                          otherSymptoms:symptoms,
//                          id: $("#visitFormDiv").data("id")};
     
     
//     {name: name, surname:surname, gender:gender,birth:birth, firstVisit: firstVisit, comment:comment, nOfVisits:nOfVisits, id:id}


      $("#pageNumberDiv").append('<a class="disabledPaginationControl" id="leftArrow">'+'«'+'</a>');
      
       
      var rangeLength = 5;
      var targetAysLength = Math.max(Math.ceil(responseArray.length/rangeLength)*rangeLength, rangeLength);
      
      for (var i=0;i<targetAysLength;i++){//both <a> with links and empty ones for same width
  
          
          var nextPageId = responseArray.length-i;
          
          
          if (i<responseArray.length){
          
              var page = $('<div class="page" id="page'+nextPageId+'">');
              $("#visitListDiv").append(page);
              
              
              page.append('<label class="sameLabelOptional">Clinician name:</label><p class="visitResColumn2" >'+responseArray[i].clinician.name+'</p><br/>');
              page.append('<label class="sameLabelOptional">Date:</label><p class="visitResColumn2" >'+responseArray[i].clinician.date+'</p><br/>');
              page.append('<label class="sameLabelOptional">Attendees:</label><p class="visitResColumn2" >'+responseArray[i].clinician.attendees.join(", ")+'</p><br/>');
          
          
              page.append('<label class="sameLabelOptional">Appointment type:</label><p class="visitResColumn2" >'+responseArray[i].appointmentType+'</p><br/>');
              
              page.append('<label class="sameLabelOptional">Weight growth OK:</label><p class="visitResColumn2" >'+(responseArray[i].plan.weightGrowthOk===1?"yes":"no")+'</p><br/>');
              page.append('<label class="sameLabelOptional">Blood pressure OK:</label><p class="visitResColumn2" >'+(responseArray[i].plan.bloodPressureOk===1?"yes":"no")+'</p><br/>');
              page.append('<label class="sameLabelOptional">Summary/plan notes:</label><p class="visitResColumn2" >'+responseArray[i].plan.comment+'</p><br/>');
              page.append('<label class="sameLabelOptional">Review in:</label><p class="visitResColumn2" >'+responseArray[i].plan.reviewIn+' months </p><br/>');
         
         
              page.append('<label class="sameLabelOptional">Progress/problems (parent view):</label><p class="visitResColumn2" >'+responseArray[i].progressProblems.parentViewNotes+'</p><br/>');
              page.append('<label class="sameLabelOptional">Progress/problems (child view):</label><p class="visitResColumn2" >'+responseArray[i].progressProblems.childViewNotes+'</p><br/>');
      
      
              page.append('<label class="sameLabelOptional">Inattention total:</label><p class="visitResColumn2" >'+responseArray[i].treatmentScores.inattentionTot+'</p><br/>');
              page.append('<label class="sameLabelOptional">Impulsivity total:</label><p class="visitResColumn2" >'+responseArray[i].treatmentScores.impulsivityTotal+'</p><br/>');
              page.append('<label class="sameLabelOptional">Inattention mean:</label><p class="visitResColumn2" >'+responseArray[i].treatmentScores.inattentionMean+'</p><br/>');
              page.append('<label class="sameLabelOptional">Deportment mean:</label><p class="visitResColumn2" >'+responseArray[i].treatmentScores.deportmentMean+'</p><br/>');
              page.append('<label class="sameLabelOptional">CGAS:</label><p class="visitResColumn2" >'+responseArray[i].treatmentScores.cgas+'</p><br/>');
      
      
              page.append('<label class="sameLabelOptional">Medication Name/Dose:</label><p class="visitResColumn2" >'+responseArray[i].allMedications[0]+'</p><br/>');
              for (var j=1;j<responseArray[i].allMedications.length;j++){
                  page.append('<label class="sameLabelOptional"></label><p class="visitResColumn2" >'+responseArray[i].allMedications[j]+'</p><br/>');
              }
              
              
              
              page.append('<table class="symptomTable"><thead><tr><th></th><th>Not present</th><th>Preset but not impairing</th><th>Present &amp; impairing</th></tr></thead><tbody></tbody>');
              var fatBody = $("#page"+nextPageId+" tbody");
              for (var j=0;j< responseArray[i].otherSymptoms.length;j++){
        
                    var propName = responseArray[i].otherSymptoms[j];
                    var val = parseInt(responseArray[i].otherSymptoms[j+1]);
                    j++;
                   
                    var tableDatas = '<td><label class="tableLable" >'+propName+'</label></td>';
                    for (var k=1;k<=3;k++){
                        tableDatas+= (val===k?'<td>&#10004;</td>' : '<td></td>');
                    }
                    
                    fatBody.append('<tr>'+tableDatas+'</tr>');
              }
              
              var pageA = $('<a id="'+nextPageId+'" onclick="switchPage('+nextPageId+');">'+nextPageId+'</a>');
              
          
          
          }
          else{
               var pageA = $('<a class="disabledPaginationControl" id="'+nextPageId+'">_</a>');

          }
          
          $("#pageNumberDiv").append(pageA);
          if (i>(rangeLength-1)){
              pageA.css("display", "none");
          }
           
          
    }
    
    if (responseArray.length>rangeLength){
        
          $("#pageNumberDiv").append('<a id="rightArrow" >'+'»'+'</a>');
          $("#rightArrow").click(goRight);
    }
    else{
         $("#pageNumberDiv").append('<a class="disabledPaginationControl" id="rightArrow" >'+'»'+'</a>');
    }
   

    
    $("#page"+responseArray.length).css("display", "block");
    $("#"+responseArray.length).addClass("activePage");
    
    var par = $("#visitListDiv .visitMessageDiv p");
    par.html(patientData.name+" "+patientData.surname);

    
    waitAjaxLock = false;
}



function goRight(){

     var currentId = parseInt($("#pageNumberDiv .activePage").eq(0).attr("id"));
     var rangeLength = 5;
     
     var firstIdInCurrentRange = parseInt($("#pageNumberDiv a:visible").eq(1).attr("id"));//after '<<'
     var firstIdInNextRange = firstIdInCurrentRange -rangeLength;//!
       
      console.log(firstIdInCurrentRange+" "+firstIdInNextRange);
     //remove previous range
     $("#"+currentId).removeClass("activePage");
     $("#page"+currentId).css("display","none");
     for (var j=0;j<rangeLength;j++){
          $("#"+(firstIdInCurrentRange - j)).css("display","none");
      
      
         //create new range
        $("#"+(firstIdInNextRange - j)).css("display", "");
     }
     
     $("#"+firstIdInNextRange).addClass("activePage");
     $("#page"+firstIdInNextRange).css("display","block");

     //update arrows
     var rightArrow = $('#rightArrow');
     if (firstIdInNextRange- rangeLength<=0){
         rightArrow.addClass("disabledPaginationControl");
         rightArrow.off('click');
     }
     
     var leftArrow = $('#leftArrow');
     if (leftArrow.hasClass("disabledPaginationControl")){
         leftArrow.removeClass("disabledPaginationControl");
         leftArrow.click(goLeft);
     }  
}


function goLeft(){

     var currentId = parseInt($("#pageNumberDiv .activePage").eq(0).attr("id"));
     var rangeLength = 5;
     
     var firstIdInCurrentRange = parseInt($("#pageNumberDiv a:visible").eq(1).attr("id"));//after '<<'
     var firstIdInNextRange = firstIdInCurrentRange +rangeLength;
      
      
      console.log(firstIdInCurrentRange+" --left-- "+firstIdInNextRange);
     //remove previous range
     $("#"+currentId).removeClass("activePage");
     $("#page"+currentId).css("display","none");
     for (var j=0;j<rangeLength;j++){
          $("#"+(firstIdInCurrentRange - j)).css("display","none");
      
      
         //create new range
         $("#"+(firstIdInNextRange - j)).css("display", "");
      
     }
     

     $("#"+firstIdInNextRange).addClass("activePage");
     $("#page"+firstIdInNextRange).css("display","block");
     
     
     //update arrows
     var leftArrow = $('#leftArrow');
     if ($("#"+(firstIdInNextRange+1)).length===0){
         leftArrow.addClass("disabledPaginationControl");
         leftArrow.off('click');
     
     }
     
     var rightArrow = $('#rightArrow');
     if (rightArrow.hasClass("disabledPaginationControl")){
         rightArrow.removeClass("disabledPaginationControl");
         rightArrow.click(goRight);
     }


}


function switchPage(clickedId){

      var currentId = $("#pageNumberDiv .activePage").eq(0).attr("id");
      
      $("#page"+currentId).css("display", "none");
      $("#page"+clickedId).css("display", "block");
     
      $("#"+currentId).removeClass("activePage");
       $("#"+clickedId).addClass("activePage");
}


function hideVisitList(){

      $("#visitListDiv .visitMessageDiv.mistakeFieldLabel").removeClass("mistakeFieldLabel");
      $("#patientSearchDiv").css("margin-left","0");
}




//ADD VISIT


function showVisitForm(target){
     
//http://jsfiddle.net/3SYka/228/
    
       $("#updatePatientBtn").remove();
       
       $("#visitListDiv").css("display","none");
       $("#visitFormDiv").css("display","inline-block");
       $("#patientSearchDiv").css("margin-left","-100%");

    
       var obj = JSON.parse($(target).parent().data("info"));
       $("#visitFormDiv").data("id", obj["id"]);
}




function hideVisitForm(){
      $("#patientSearchDiv").css("margin-left","0");
      clearVisitForm();
}

function clearVisitForm(){
      $(".mistakeFieldLabel").removeClass("mistakeFieldLabel");
     
     $("#visitFormDiv .visitMessageDiv").html('<p>Ready</p>');
     
      $("#visitFormDiv .visitMessageDiv").removeClass("mistakeFieldLabel");
      
        $("#visitMonth").val("1");


       $('#visitFormDiv input:text').val("");
       $('#visitFormDiv textarea').val("");
       
       $('#visitFormDiv input:radio').prop('checked', false);
       $('#visitFormDiv table input:radio').filter('[value=1]').prop('checked', true);
       
       
       $('#visitFormDiv input:checkbox').prop('checked',false);
       $('#otherAttender').prop('disabled', true);
       
}




function submitVisit(){

    if (waitAjaxLock){return;}

    $(".mistakeFieldLabel").removeClass("mistakeFieldLabel");
    $("#visitFormDiv .visitMessageDiv").html('<p>Submitting...</p>');
    $("#visitFormDiv").scrollTop(0);  //  $(document).scrollTop( $("#visitFormDiv").offset().top );  
    
     
    var doValidation = true;
    if (doValidation){
    
        var result = readVisitFormValues();
        var type = typeof result;
        if ( type === "string"){//error
        
         
         
            $("#visitFormDiv .visitMessageDiv").html(result);
            $("#visitFormDiv .visitMessageDiv").addClass("mistakeFieldLabel");
           
        
        }
        else if (type === "object"){
            waitAjaxLock = true;
            
            $.post("saveVisit", {data: JSON.stringify(result)}, function(){

                $("#visitFormDiv .visitMessageDiv p").text('Done');
                 waitAjaxLock = false;

           }).fail(function( jqXHR,  textStatus, errorThrown) {

                $("#visitFormDiv .visitMessageDiv p").text(jqXHR.responseText);
                $("#visitFormDiv .visitMessageDiv").addClass("mistakeFieldLabel");
                waitAjaxLock = false;
           });
        }
    }
}



function readVisitFormValues(){

      var errorHtmlString="";
     

//CLINICIANS
      
      
      //clinician name
      var nameOfClinician = $("#clinician").val();
      
      if (nameOfClinician===""){
           errorHtmlString+= '<p>Name is not set</p>';
           $("#clinician").prev().addClass("mistakeFieldLabel");
      }
      
      
      //visit date
      var visitDay =  $("#visitDay").val();
      var visitYear = $("#visitYear").val();
      var visitMonth = $("#visitMonth").val();
      
       try{
           validateDate(visitDay, visitMonth, visitYear, "Visit date ");
           var visitDate = assembleDate(visitDay, visitMonth, visitYear);
       }
       catch(message){
           errorHtmlString+= '<p>'+message+'</p>';
           $("#visitMonth").prev().addClass("mistakeFieldLabel");
       }
      
      //attendee
      
      var attendee=[];
      $('input[name="attender"]:checked').each(function() {
         attendee.push(this.value);
      });
      
      if ($("#other").is(":checked")){
         attendee.push($("#otherAttender").val());
      }
      if (attendee.length===0){
          errorHtmlString+= '<p>Attendee not specified </p>';
          $("#child").parent().prev().addClass("mistakeFieldLabel");
      }
      
      
//APPOINTMENT TYPE
      var appointmentType = "";
      var selected = $("input[type='radio'][name='type']:checked");
      if (selected.length > 0) {
          appointmentType = selected.val();
      }
      else{
          errorHtmlString+= '<p>Appointment type not selected</p>';
         $("input[type='radio'][name='type']:first").parent().prev().addClass("mistakeFieldLabel");
      
      }
//SUMMARY AND PLAN
      var weightGrowthOk = "";
      selected = $("input[type='radio'][name='wgOk']:checked");
      if (selected.length > 0) {
          weightGrowthOk = parseInt(selected.val());
      }
      else{
          errorHtmlString+= '<p>Specify if weight growth is ok</p>';
          $("input[type='radio'][name='wgOk']:first").parent().prev().addClass("mistakeFieldLabel");
      
      }
      
      var bloodPressureOk = "";
      selected = $("input[type='radio'][name='bpOk']:checked");
      if (selected.length > 0) {
          bloodPressureOk = parseInt(selected.val());
      }
      else{
          errorHtmlString+= '<p>Specify if blood pressure is ok</p>';
          $("input[type='radio'][name='bpOk']:first").parent().prev().addClass("mistakeFieldLabel");
      
      }
      
      var commentPlan = $("#commentPlan").val();
      
      var reviewInStr = $("#nextReview").val();
      if (!(/^\d+$/.test(reviewInStr))){
           errorHtmlString+= '<p>"Review in" must be a number</p>';
           $("#nextReview").prev().addClass("mistakeFieldLabel");
      }
      else{
          var reviewIn = parseInt(reviewInStr);
          if(reviewIn<0){
              errorHtmlString+= '<p>"Review in" must be a positive number</p>';
              $("#nextReview").prev().addClass("mistakeFieldLabel");
      
          }
      }
      
      


//OVERALL PROGRESS
      var parentViewNotes = $("#parentView").val();
      var childViewNotes = $("#childView").val();
      
//TREATMENT SCORES

      var inattentionTot =   parseInt($("#inattentionTotal").val());
      if (isNaN(inattentionTot)){
          errorHtmlString+= '<p>Total inattention must be a number</p>';
          $("#inattentionTotal").prev().addClass("mistakeFieldLabel");
      }
      
      var impulsivityTotal =   parseInt($("#impulsivityTotal").val());
      if (isNaN(impulsivityTotal)){
          errorHtmlString+= '<p>Total impulsivity must be a number</p>';
          $("#impulsivityTotal").prev().addClass("mistakeFieldLabel");
      }
      
      var inattentionMean =   parseInt($("#inattentionMean").val());
      if (isNaN(inattentionMean)){
          errorHtmlString+= '<p>Inattention mean must be a number</p>';
          $("#inattentionMean").prev().addClass("mistakeFieldLabel");
      }
      else if(inattentionMean>6){
          errorHtmlString+= '<p>Inattention mean must be from 0 to 6</p>';
          $("#inattentionMean").prev().addClass("mistakeFieldLabel");
      }
      
      var deportmentMean =   parseInt($("#deportmentMean").val());
      if (isNaN(deportmentMean)){
          errorHtmlString+= '<p>Deportment mean must be a number</p>';
          $("#deportmentMean").prev().addClass("mistakeFieldLabel");
      }
      else if(deportmentMean>6){
          errorHtmlString+= '<p>Deportment mean must be from 0 to 6</p>';
          $("#deportmentMean").prev().addClass("mistakeFieldLabel");
      }
      
      var cgasStr = $("#cgas").val();
      if (!(/^\d+$/.test(cgasStr))){
          errorHtmlString+= '<p>CGAS must be a number</p>';
          $("#cgas").prev().addClass("mistakeFieldLabel");
      }
      else{
           var cgas =   parseInt(cgasStr);
           if(cgas>100 || cgas<0){
               errorHtmlString+= '<p>CGAS must be from 0 to 100</p>';
               $("#cgas").prev().addClass("mistakeFieldLabel");
           }
      }
      
      
//MEDICATION CURRENT TAKING
      var allMedications = [];

      $('#medicationsDiv :input').not(':button').each(function(ind, elem){
            var value = elem.value;
            
            if (value===""){
                errorHtmlString+= '<p>Medication not specified</p>';
                $(elem).prev().addClass("mistakeFieldLabel");
            
            }
            else{
                allMedications.push(value);
            
            }
      
      });
      

//OTHER SYMPTOMS

 
       var symptomsArr=[];   
       $("#visitFormDiv table tbody tr")  
          .each(function() { 
                   var text = $(this).children().eq(0).children().eq(0).text();
                   var selected = $(this).find("input[type='radio']:checked");
                   symptomsArr.push(text);
                   symptomsArr.push(selected.val());
                   
          });
        

//FINALLY
      if (errorHtmlString===""){//CHANGE TO === DON'T FORGET
           var allData = {clinician:{name:nameOfClinician, date:visitDate, attendees:attendee}, 
                          appointmentType:appointmentType, 
                          plan:{weightGrowthOk:weightGrowthOk,bloodPressureOk:bloodPressureOk, comment:commentPlan, reviewIn:reviewIn},
                          progressProblems:{parentViewNotes:parentViewNotes, childViewNotes:childViewNotes},
                          treatmentScores:{inattentionTot:inattentionTot,impulsivityTotal:impulsivityTotal,inattentionMean:inattentionMean,deportmentMean:deportmentMean,cgas:cgas },
                          allMedications:allMedications,
                          otherSymptoms:symptomsArr,
                          id: $("#visitFormDiv").data("id")};

// var allData = {clinician:{name:"nameOfClinician", date:"visitDate", attendees:["attendee1", "att2"]}, 
//                          appointmentType:"appointmentType", 
//                          plan:{weightGrowthOk:1,bloodPressureOk:1, comment:"commentPlan", reviewIn:1},
//                          progressProblems:{parentViewNotes:"parentViewNotes", childViewNotes:"childViewNotes"},
//                          treatmentScores:{inattentionTot:1,impulsivityTotal:1,inattentionMean:1,deportmentMean:1,cgas:1 },
//                          allMedications:["cucuil","mrazil"],
//                          otherSymptoms:["zzzz","1","kkkk","2"],
//                          id: $("#visitFormDiv").data("id")};
      
           return allData;
      }
      else{
      
          return errorHtmlString;
      
      }
}


//FIND PATIENTS

function findPatients(){

    if (waitAjaxLock){return;}

    $("#updatePatientBtn").remove();
    var name = $("#searchCriteria").val();
    
    $("#patientListHeader p").text("Searching...");
    waitAjaxLock = true;
    
    $.post("findPatients", {name: name}, function(data){
        
        findPatientsSucceed(data);
       
    }).fail(function( jqXHR,  textStatus, errorThrown) {
       
         $("#patientListHeader p").text(jqXHR.responseText); 
         waitAjaxLock = false;
    });
}


function findPatientsSucceed(allPatients){//[{},{},{}...]

     var patientListHolder = $("#patientListHolder");
     patientListHolder.html('');
     
    
     for (var i=0; i<allPatients.length;i++){
         
         htmlString= '<div class="patientDiv">'+
                            '<span class="nsSpan">'+allPatients[i].name+" "+allPatients[i].surname+ '</span>'+
                            '<span class="patientSpan">Gender: '+allPatients[i].gender+'</span>'+
                            '<span class="patientSpan">Visits: '+allPatients[i].nOfVisits+'</span>'+
                            
                          
                            '<span class="patientSpan">Birth: '+allPatients[i].birthDate+'</span>'+
                           
                            '<span class="patientSpan">First Visit: '+allPatients[i].firstVisit+'</span>'+
                            '<p>'+allPatients[i].comment+'</p>'+        

                           '<button type="button"  onclick="showVisitForm(this)" >Add visit</button>'+
                           '<button type="button"  onclick="viewVisits(this)" >View Visits</button>'+
                           '<button type="button"  onclick="editPatient(this)" >Edit patient</button>'+
                           '<button type="button"  onclick="deletePatient(this)">Delete patient</button>'+
              
                      '</div>';
         
        patientListHolder.append( htmlString);
        patientListHolder.children().last().data("info", JSON.stringify(allPatients[i]));
        
     }
     

     $("#patientListHeader p").text('Results: '+allPatients.length); 
     waitAjaxLock = false;
}




//ADD PATIENT


function toggleAddPatient(){
    
      var $addPatientElement = $("#addPatientDiv");
      var currentH = $addPatientElement.height();
      
      if ($addPatientElement.is(":visible") ){
            
             $("#updatePatientBtn").remove();
             $("#patientSearchDiv").height("+="+currentH); 
             $("#visitFormDiv").height("+="+currentH);
             $("#visitListDiv").height("+="+currentH);
            $addPatientElement.slideUp(0);
            
      }
      else{
         
          
          $("#patientSearchDiv").height("-="+currentH);
          $("#visitFormDiv").height("-="+currentH);
          $("#visitListDiv").height("-="+currentH);
          $addPatientElement.slideDown(0);
          
          
      }
      
}

function addUpdatePatient(type){

           if (waitAjaxLock){return;}


           if (type==="add"){
               $("#messageP").text("Saving...");
           }
           else if (type==="update"){
                $("#messageP").text("Updating...");
           }

           var name = $("#nameField").val();
           var surname = $("#surnameField").val();
           
           var monthNumAge = $("#monthSelectAge").val();
           var dayStrAge = $("#daySelectAge").val();
           var yearStrAge = $("#yearSelectAge").val();
           
           var monthNumFirst = $("#monthSelectFirst").val();
           var dayStrFirst = $("#daySelectFirst").val();
           var yearStrFirst = $("#yearSelectFirst").val();
           
           var comment =  $("#commentField").val();
           var gender =  $('input[name=gender]:checked').val();
           
        
         
         if (true){//prevent user mistakes only. 
         
               if (name==="" || surname==="" || dayStrAge==="" || yearStrAge==="" || dayStrFirst==="" || yearStrFirst==="" ){
                    $("#messageP").text("All required fields must be non-empty"); 
                    return;
               }
         
         
               try{
                   validateDate(dayStrAge, monthNumAge, yearStrAge, "Birth date ");
                   validateDate(dayStrFirst, monthNumFirst, yearStrFirst, "First visit date ");
               }
               catch(message){
                   $("#messageP").text(message); 
                   return;
               }
         }
         
        
        
        
         
         var dataToSave = {name: name, surname: surname, 
                           birthDate:assembleDate(dayStrAge, monthNumAge, yearStrAge), 
                           firstVisit: assembleDate(dayStrFirst, monthNumFirst, yearStrFirst), comment: comment, gender: gender   };
         
         waitAjaxLock = true;
         if (type==="add"){
           
                $.post("saveUpdatePatient", {data:  JSON.stringify(dataToSave), type:"save"}, function(){
                     $("#messageP").text("Saved user successfully"); 
                     waitAjaxLock = false;
                }).fail(function( jqXHR,  textStatus, errorThrown) {
                     $("#messageP").text(jqXHR.responseText); 
                     waitAjaxLock = false;
                });
           
         }
         else if (type==="update"){
                dataToSave.id = $("#updatePatientBtn").data("id");

                $.post("saveUpdatePatient", {data:  JSON.stringify(dataToSave), type:"update"}, function(){
                      updateUserSucceed(dataToSave);
                }).fail(function( jqXHR,  textStatus, errorThrown) {
                      $("#messageP").text(jqXHR.responseText); 
                      waitAjaxLock = false;
                });
              
 
         }
    

}





function updateUserSucceed( dataToSave){
     $("#messageP").text("Updated user successfully"); 
     
     $(".patientDiv").each(function(){
            var id = JSON.parse($(this).data("info"))["id"];
            if (id===dataToSave.id){
                  var $allSpans =  $(this).children("span");
                  $allSpans.eq(0).text(dataToSave.name+" "+dataToSave.surname);
                  $allSpans.eq(1).text("Gender: "+dataToSave.gender);
                  $allSpans.eq(3).text("Birth: "+dataToSave.birthDate);
                  $allSpans.eq(4).text("First Visit: "+dataToSave.firstVisit);
                  
             
                  $(this).children("p").eq(0).text(dataToSave.comment);        
                           
                  return false;//break from each      
            }
     
     });
     
     waitAjaxLock = false;
     
}



//HELPERS


function assembleDate(dayStr, monthStr,yearStr){

       dayStr = (dayStr.length===1) ? ("0"+dayStr): dayStr;
       monthStr = (monthStr.length===1) ? ("0"+monthStr): monthStr;
       
       return yearStr+"-"+monthStr+"-"+dayStr;
}


function validateDate(dayStr, monthStr,yearStr, who){

               var day = parseInt(dayStr);
               var year = parseInt(yearStr);
               
               if (!(/^\d+$/.test(dayStr)) || !(/^\d+$/.test(yearStr))){
                   throw (who+ "is not correct. Date may contain numbers only");
               }
               else if (day<=0 || year<=0){
                    throw (who+ "is not correct. Date cannot be negative or 0");
               }
               else if(yearStr.length!==4){
                    throw (who+ "is not correct. Year is 4 digits long");
               }
               
               var maxDays;
               switch (monthStr) {
                 case "2" :
                     maxDays = ((year % 4 == 0 && year % 100!==0) ||year % 400 == 0) ? 29 : 28; 
                     break;
                 case "9" : case "4" : case "6" : case "11" :
                     maxDays = 30;
                     break;
                 default :
                       maxDays=  31;
             }
             
             
           
               if (day>maxDays){
            
                       throw who+ "is not correct. "+maxDays+ " days in selected month" ;
               }
               
}


function addMedicationLine(e){

        
      $("<div style='width:100%'><label class='sameLabel'>Medication Name/Dose</label>"+
        "<input type='text' style='width:300px' class='visitInputElement'>"+
        "<button type='button' onclick='(function(e){ $(e).parent().remove();})(this)'>remove</button><br/><div>").insertBefore($(e));
}
