<%-- 
    Document   : index
    Created on : 23-Sep-2016, 15:17:11
    Author     : Vlad
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <base target="_top">
    
    <link rel="stylesheet" type="text/css" href="static/tooltipsterCSS.css">
     <link rel="stylesheet" type="text/css" href="static/style.css">
     <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
     <script src="static/javascript.js"></script>
     <script src="static/tooltipster.js"></script>
     
    
  </head>
  <body>
    
    <div id="headerDiv">
        
         <div class="linksSection">
              <button type="button"  class="inlineElem" onclick="toggleAddPatient()" >Add patient toggles</button>
         </div>
        
         <div class="linksSection">
                  
                   <button class="imageButton" id="fitbitLink"  onclick="location.href='http://silva.computing.dundee.ac.uk/2016-vlad-fitbit/'"></button>
                   <button class="imageButton" id="geneactivLink"  onclick="location.href='http://silva.computing.dundee.ac.uk/2016-vlad-geneactiv'"></button>                
                   <button class="imageButton tooltipShower" id="logout" data-tooltip-content="#logoutFrame" ></button>
                 
                   <div class="tooltip_content">
                        
                         <div id="logoutFrame">

                              <p >${sessionScope.user.username}</p>
                             <button onclick="sendLogoutRequest();" >Sign out</button>
                         </div>
                   </div>
                   
                   
         </div> 
    </div>
    
  
    
    <div id="addPatientDiv" >
           <div >
             <p id="messageP">Ready</p>
             <div id="leftAddUserFormDiv" class="inlineElem" >
                 <label for="nameField" class="sameLabel">Name:</label><input type="text" id="nameField" maxlength="18"><br>
                 <label for="surnameField" class="sameLabel">Surname:</label><input type="text" id="surnameField" maxlength="18"><br>
                 
                 <label for="radioGenderM" class="sameLabel">Gender:</label>
                 <span>
                     <input type="radio" name="gender" id="radioGenderM" value="M" checked=true/>
                     <label for="radioGenderM" >M</label>
                     <input type="radio" name="gender" id="radioGenderF" value="F" />
                     <label for="radioGenderF">F</label>
                     
                 </span><br/>
                 
                 
                 <span>
                     <label for="monthSelectAge" class="sameLabel">Birth Date:</label><select id="monthSelectAge">
                         <option value="1">January</option><option value="2">February</option><option value="3">March</option><option value="4">April</option><option value="5">May</option><option value="6">June</option><option value="7">July</option><option value="8">August</option><option value="9">September</option><option value="10">October</option><option value="11">November</option><option value="12">December</option>
                     </select>
                     <input type="text" maxlength="2" placeholder="dd" id="daySelectAge" class="daySelect">
                     <input type="text" maxlength="4" placeholder="yyyy" id="yearSelectAge" class="yearSelect"><br>
                 </span>
                 
                
                 <span>
                      <label for="monthSelectFirst" class="sameLabel">First visit:</label><select id="monthSelectFirst">
                         <option value="1">January</option><option value="2">February</option><option value="3">March</option><option value="4">April</option><option value="5">May</option><option value="6">June</option><option value="7">July</option><option value="8">August</option><option value="9">September</option><option value="10">October</option><option value="11">November</option><option value="12">December</option>
                     </select>
                     <input type="text" maxlength="2" placeholder="dd" id="daySelectFirst" class="daySelect">
                     <input type="text" maxlength="4" placeholder="yyyy" id="yearSelectFirst" class="yearSelect"><br>
                 </span>
                 
                 
                 
                 
             </div>
             
             <div id="rightAddUserFormDiv" class="inlineElem">
            
             
                 <label for="commentField" >Comment:</label>
                 <textarea id="commentField" rows="5" cols="40" maxlength="200" placeholder="200 characters max (optional)" style="resize: none;"></textarea>
                 
             </div>
             
            </div> 
            <div >
              <button type="button" id="addPatientBtn" onclick="addUpdatePatient('add')">Add</button>
            </div>
            
        
    </div>
    
  
    <div id="patientSearchDiv">
        <div id="patientListHeader" > 
           <p class="inlineElem">Ready</p>
           <div>
               <input type="text" maxlength="18" placeholder="Search by First Name (optional)" id="searchCriteria" class="inlineElem">
               <button type="button" title="Find in database" class="inlineElem" onclick="findPatients()" >Find</button>
           </div>
        </div>
        <div id="patientListHolder"></div>
    
    </div><!--ADD VISIT, same line to avoid indent--><div id="visitFormDiv" >
    
    
          <div >
              <div class="backBtnDiv">
                     <button type="button"  class="inlineElem" onclick="hideVisitForm()">back</button>
              </div><div   class="visitMessageDiv" class="inlineElem">
                  <p>Ready</p>
              </div>
          
          </div>
          
          <div class="sectionDiv"> 
              <div class="sectionHeader">Clinicians</div>
              <label for="clinician" class="sameLabel">Name of clinician(s):</label>
              <input type="text" id="clinician" maxlength="50" class="visitInputElement"><br>
              
              
              <span>
                   <label for="visitMonth" class="sameLabel">Visit date:</label>
                   <select id="visitMonth" class="visitInputElement">
                       <option value="1">January</option><option value="2">February</option><option value="3">March</option><option value="4">April</option><option value="5">May</option><option value="6">June</option><option value="7">July</option><option value="8">August</option><option value="9">September</option><option value="10">October</option><option value="11">November</option><option value="12">December</option>
                   </select>
                   <input type="text" maxlength="2" placeholder="dd" id="visitDay" class="visitInputElement">
                   <input type="text" maxlength="4" placeholder="yyyy" id="visitYear" class="visitInputElement"><br>
               </span>
               
               <label class="sameLabel">Appointment attended by:</label><div class="visitInputElement" >
                     <input type="checkbox"  name="attender" id="child" value="child" />
                     <label for="child" >Child</label><br/>
                     
                     <input type="checkbox" name="attender"  id="parent" value="parent" />
                     <label for="parent" >Parent</label><br/>
                     
                     <input type="checkbox" name="attender"  id="carer" value="carer" />
                     <label for="carer" >Carer</label><br/>
                     
                     <input type="checkbox" id="other" value="other" onclick="(function(e){e.checked ? $('#otherAttender').prop('disabled', false) : $('#otherAttender').prop('disabled', true);})(this)" />
                     <label for="other" >Other</label>
                     <input type="text" id="otherAttender" maxlength="18" class="visitInputElement" disabled><br>
                     
                </div><br/>
               
          </div>
          
          
          <div class="sectionDiv"> 
                <div class="sectionHeader">Appointment type</div>
                <label  class="sameLabel">Type:</label>
                <div  class="visitInputElement">
                     <input type="radio" name="type"  value="hello" />
                     <label >Hello</label>
                     
                     <input type="radio" name="type"  value="titration" />
                     <label >Titration</label>
                     
                     <input type="radio" name="type"  value="continuingCare" />
                     <label >Continuing care</label>
                     
                     <input type="radio" name="type"  value="askedToSee" />
                     <label >Asked to see</label> 
                </div>
          </div>
    
    
          <div class="sectionDiv"> 
                <div class="sectionHeader">Summary and plan</div>
                
                <label  class="sameLabel">Weight/growth ok:</label>
                <div  class="visitInputElement">
                     <input type="radio" name="wgOk"  value="1" />
                     <label >yes</label>
                     
                     <input type="radio" name="wgOk"  value="0" />
                     <label >no</label>
                </div>     
                
                <label  class="sameLabel">BP ok:</label>
                <div  class="visitInputElement">
                     <input type="radio" name="bpOk"  value="1" />
                     <label >yes</label>
                     
                     <input type="radio" name="bpOk"  value="0" />
                     <label >no</label>
                </div><br/>
                
                <label for="commentPlan" class="sameLabelOptional">Notes:</label>
                <textarea id="commentPlan" rows="10"  maxlength="800" placeholder="Comment. 800 characters max " style="resize: none;"></textarea><br/>
                
                <label class="sameLabel">Review in </label> 
                <input type="text" maxlength="2"  id="nextReview" class="visitInputElement" >
                <span>   months</span>
                
           </div>
           
           
           <div class="sectionDiv"> 
              <div class="sectionHeader">Overall progress and problems</div>
               
              <label for="parentView" class="sameLabelOptional">Parent/carer view:</label>
              <textarea id="parentView" rows="10"  maxlength="800" placeholder="Comment. 800 characters max " style="resize: none;"></textarea><br/>
                
              <label for="childView" class="sameLabelOptional">Child view:</label>
              <textarea id="childView" rows="10"  maxlength="800" placeholder="Comment. 800 characters max " style="resize: none;"></textarea>
           </div>
    
    
            <div class="sectionDiv"> 
              <div class="sectionHeader">Response to treatment scores</div>
              
              <label class="sameLabel">Inattention total </label> 
              <input type="text" maxlength="1"  id="inattentionTotal" class="visitInputElement" placeholder="9 max"><br/>
              <label class="sameLabel">Hyp-Imp total </label> 
              <input type="text" maxlength="1"  id="impulsivityTotal" class="visitInputElement"  placeholder="9 max"><br/>
              
          
                  
              <label class="sameLabel scamp" >Inattention mean score </label> 
              <input type="text" maxlength="1" id="inattentionMean" class="visitInputElement" placeholder="6 max"><br/>
              
              <label class="sameLabel scamp"  >Deportment mean score  </label> 
              <input type="text" maxlength="1"  id="deportmentMean" class="visitInputElement" placeholder="6 max"> <br/>

              <label class="sameLabel">CGAS </label> 
              <input type="text" maxlength="3"  id="cgas" class="visitInputElement" placeholder="100 max">
              
            </div>  
            
            <div class="sectionDiv" id="medicationsDiv"> 
               <div class="sectionHeader"> Medication current taking / recommended (If none, write none)</div>
               <div style='width:100%'><label class="sameLabel" >Medication Name/Dose</label><input type="text" style="width:300px" class="visitInputElement" ></div>
               <button type="button" id="addMedicationBtn" onclick="addMedicationLine(this)">Add item</button>
            </div>  
           
            <div class="sectionDiv"> 
                <div class="sectionHeader">Other symptoms</div>
                
                
             
 
                <table class="symptomTable">
                    <thead>
                          <tr>
                             <th></th>
                             <th>Not present</th>
                             <th>Preset but not impairing</th>
                             <th>Present &amp; impairing</th>
                          </tr>
                    </thead>
                    
                    <tbody>
                        <tr id="insomnia">
                          <td><label class="tableLable" >Insomnia or trouble sleeping</label></td>
                          <td><input type="radio" name="insomnia"  value="1" checked="true"/></td>
                          <td><input type="radio" name="insomnia"  value="2" /></td>
                          <td><input type="radio" name="insomnia"  value="3" /></td>
                        </tr>
                        <tr id="nightmares">
                          <td><label class="tableLable" >Nightmares</label></td>
                          <td><input type="radio" name="nightmares"  value="1" checked="true"/></td>
                          <td><input type="radio" name="nightmares"  value="2" /></td>
                          <td><input type="radio" name="nightmares"  value="3" /></td>
                        </tr>
                        <tr id="drowsiness">
                          <td><label class="tableLable" >Drowsiness</label></td>
                          <td><input type="radio" name="drowsiness"  value="1" checked="true"/></td>
                          <td><input type="radio" name="drowsiness"  value="2" /></td>
                          <td><input type="radio" name="drowsiness"  value="3" /></td>
                        </tr>
                        <tr id="nausea">
                          <td><label class="tableLable" >Nausea</label></td>
                          <td><input type="radio" name="nausea"  value="1" checked="true"/></td>
                          <td><input type="radio" name="nausea"  value="2" /></td>
                          <td><input type="radio" name="nausea"  value="3" /></td>
                        </tr>
                        <tr id="anorexia">
                          <td><label class="tableLable" >Anorexia/less hungry than other children</label></td>
                          <td><input type="radio" name="anorexia"  value="1" checked="true" /></td>
                          <td><input type="radio" name="anorexia"  value="2" /></td>
                          <td><input type="radio" name="anorexia"  value="3" /></td>
                        </tr>
                        <tr id="stomachaches">
                          <td><label class="tableLable" >Stomach aches</label></td>
                          <td><input type="radio" name="stomach"  value="1" checked="true"/></td>
                          <td><input type="radio" name="stomach"  value="2" /></td>
                          <td><input type="radio" name="stomach"  value="3" /></td>
                        </tr>
                        <tr id="headaches">
                          <td><label class="tableLable" >Headaches</label></td>
                          <td><input type="radio" name="headaches"  value="1" checked="true"/></td>
                          <td><input type="radio" name="headaches"  value="2" /></td>
                          <td><input type="radio" name="headaches"  value="3" /></td>
                        </tr>
                        <tr id="dizziness">
                          <td><label class="tableLable" >Dizziness</label></td>
                          <td><input type="radio" name="dizziness"  value="1" checked="true"/></td>
                          <td><input type="radio" name="dizziness"  value="2" /></td>
                          <td><input type="radio" name="dizziness"  value="3" /></td>
                        </tr>
                        <tr id="unhappy">
                          <td><label class="tableLable" >Sad/unhappy</label></td>
                          <td><input type="radio" name="sad"  value="1" checked="true"/></td>
                          <td><input type="radio" name="sad"  value="2" /></td>
                          <td><input type="radio" name="sad"  value="3" /></td>
                        </tr>
                        <tr id="crying">
                          <td><label class="tableLable" >Prone to crying</label></td>
                          <td><input type="radio" name="crying"  value="1" checked="true"/></td>
                          <td><input type="radio" name="crying"  value="2" /></td>
                          <td><input type="radio" name="crying"  value="3" /></td>
                        </tr>
                        <tr id="irritable">
                          <td><label class="tableLable" >Irritable</label></td>
                          <td><input type="radio" name="irritable"  value="1" checked="true"/></td>
                          <td><input type="radio" name="irritable"  value="2" /></td>
                          <td><input type="radio" name="irritable"  value="3" /></td>
                        </tr>
                         <tr id="selfharm">
                          <td><label class="tableLable" >Thoughts of self-harm</label></td>
                          <td><input type="radio" name="harm"  value="1" checked="true"/></td>
                          <td><input type="radio" name="harm"  value="2" /></td>
                          <td><input type="radio" name="harm"  value="3" /></td>
                        </tr>
                         <tr id="suicidal">
                          <td><label class="tableLable" >Suicidal ideation</label></td>
                          <td><input type="radio" name="suicidal"  value="1" checked="true"/></td>
                          <td><input type="radio" name="suicidal"  value="2" /></td>
                          <td><input type="radio" name="suicidal"  value="3" /></td>
                        </tr>
                         <tr id="euphoric">
                          <td><label class="tableLable" >Euphoric/unusually happy</label></td>
                          <td><input type="radio" name="euphoric"  value="1" checked="true"/></td>
                          <td><input type="radio" name="euphoric"  value="2" /></td>
                          <td><input type="radio" name="euphoric"  value="3" /></td>
                        </tr>
                         <tr id="anxious">
                          <td><label class="tableLable" >Anxious</label></td>
                          <td><input type="radio" name="anxious"  value="1" checked="true"/></td>
                          <td><input type="radio" name="anxious"  value="2" /></td>
                          <td><input type="radio" name="anxious"  value="3" /></td>
                        </tr>
                         <tr id="ticks">
                          <td><label class="tableLable" >Ticks or nervous movements</label></td>
                          <td><input type="radio" name="ticks"  value="1" checked="true"/></td>
                          <td><input type="radio" name="ticks"  value="2" /></td>
                          <td><input type="radio" name="ticks"  value="3" /></td>
                        </tr>
                        <tr id="zombie">
                          <td><label class="tableLable" >“Spaced-out”/“Zombie-like”</label></td>
                          <td><input type="radio" name="zombie"  value="1" checked="true"/></td>
                          <td><input type="radio" name="zombie"  value="2" /></td>
                          <td><input type="radio" name="zombie"  value="3" /></td>
                        </tr>
                        <tr id="lesstalk">
                          <td><label class="tableLable" >Less talkative than other children</label></td>
                          <td><input type="radio" name="silent"  value="1" checked="true"/></td>
                          <td><input type="radio" name="silent"  value="2" /></td>
                          <td><input type="radio" name="silent"  value="3" /></td>
                        </tr>
                        <tr id="lesssociable">
                          <td><label class="tableLable" >Less sociable than other children</label></td>
                          <td><input type="radio" name="lessSociable"  value="1" checked="true"/></td>
                          <td><input type="radio" name="lessSociable"  value="2" /></td>
                          <td><input type="radio" name="lessSociable"  value="3" /></td>
                        </tr>
                    
                    </tbody>
                
                
                </table>
                
                
                
            </div>
            
            <button type="button" id="addVisitBtn" onclick="submitVisit()">Submit visit</button>
           
           
           
    </div><!--VIEW VISITS--><div id="visitListDiv" >
    
    
         
              <div class="backBtnDiv">
                     <button type="button"  class="inlineElem" onclick="hideVisitList()">back</button>
              </div><div class="inlineElem" style="width:80%">
                    <div class="visitMessageDiv" id="flexContainer">
                        <p class="inlineElem">Ready</p>
                        <div id="pageNumberDiv" title="Visits sorted by date">
                    
                        </div>
                    </div>
              </div>
              
              
              
              
              
          
         
          
          <!--HERE GO PAGES-->
         
    
    </div>
    
    
    
  </body>
</html>



