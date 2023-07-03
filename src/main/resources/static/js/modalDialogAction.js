function openModal(getId, taskId) {
    document.getElementById("myModal").style.display = "block";
    document.getElementById("description").value = document.getElementById("inputDesc" + getId).innerText;
    document.getElementById("begi").value = document.getElementById("inputBegi" + getId).innerText;
    document.getElementById("end").value = document.getElementById("inputEnd" + getId).innerText;
    document.getElementById("pause").value = document.getElementById("inputPause" + getId).innerText;
    document.getElementById("date").value = document.getElementById("inputMade" + getId).innerText;
    document.getElementById("taskReport").value = parseInt(taskId);

    document.getElementById("sId").value = getId;


}

function closeModal() {
    document.getElementById("description").value = null;
    document.getElementById("begi").value = null;
    document.getElementById("end").value = null;
    document.getElementById("pause").value = null;
    document.getElementById("sId").value = null;
    document.getElementById("date").value = null;
    document.getElementById("myModal").style.display = "none";

}

function openModalToSaveNewReport(){
    document.getElementById("myModal").style.display = "block";
    document.getElementById("taskReport").value = null;
}

function openModalToSaveVacation(){
    document.getElementById("vacationModal").style.display = "block";
}

function closeVacationModal(){
    document.getElementById('vacationBegin').value = null;
    document.getElementById('vacationEnd').value = null;
    document.getElementById('atOwn').value = null;
    document.getElementById('badDateVacation').innerHTML  = null;
    document.getElementById("vacationModal").style.display = "none";
}


function openModalToSaveSickLeave(){
    document.getElementById("sickLeaveModal").style.display = "block";
}
function closeSickLeaveModal(){
    document.getElementById('sickleaveBegin').value = null;
    document.getElementById('sickleaveEnd').value = null;
    document.getElementById('sickLeaveAtOwn').value = null;
    document.getElementById('sickleaveDescription').value = null;
    document.getElementById('badDateSL').innerHTML  = null;
    document.getElementById("sickLeaveModal").style.display = "none";
}

function openTask(getId, userId){
    document.getElementById("taskModal").style.display = "block";
    document.getElementById("taskName").value = document.getElementById("inputTaskName" + getId).innerText;
    document.getElementById("deadline").value = document.getElementById("inputTaskDeadline" + getId).innerText;
    document.getElementById("editTaskAssignedTo").value = parseInt(userId);
    document.getElementById("taskDescription").value = document.getElementById("inputTaskDesc" + getId).innerText;

    document.getElementById("taskId").value = getId;
}

function openNewTask(){
    document.getElementById("taskModal").style.display = "block";
}

function closeTask(){
    document.getElementById("taskModal").style.display = "none";
    document.getElementById("taskName").value = null;
    document.getElementById("deadline").value = null;
    document.getElementById("editTaskAssignedTo").value = null;
    document.getElementById("taskDescription").value = null;

    document.getElementById("taskId").value = null;
}

function openNewAssignment(){
    document.getElementById("assignmentModal").style.display = "block";
}

function closeAssignment(){
    document.getElementById("assignmentModal").style.display = "none";
    document.getElementById("selectedUser").value = null;
    document.getElementById("userList").innerText = null;
    document.getElementById("assignment").value = null;
    document.getElementById("assignmentBegin").value = null;
    document.getElementById("assignmentEnd").value = null;
    document.getElementById("assignmentCountry").value = null;
    document.getElementById("assignmentCity").value = null;
    document.getElementById("assignmentDescription").value = null;
    document.getElementById("userList").value = null;

}

function openToUpload(){
    document.getElementById("upload").style.display = "block"
}

function closeUpload(){
    document.getElementById("upload").style.display = "none"
    document.getElementById("invoice").value = null;
}

function changeAssignment(getId, users){
    document.getElementById("assignmentModal").style.display = "block";

    document.getElementById("assignmentId").value = getId;
    document.getElementById("assignment").value = document.getElementById("assignmentName" + getId).innerText;
    document.getElementById("assignmentCountry").value = document.getElementById("assignmentCountry" + getId).innerText;
    document.getElementById("assignmentCity").value = document.getElementById("assignmentCity" + getId).innerText;
    document.getElementById("assignmentBegin").value = document.getElementById("assignmentBegin" + getId).innerText;
    document.getElementById("assignmentEnd").value = document.getElementById("assignmentEnd" + getId).innerText;
    document.getElementById("assignmentDescription").value = document.getElementById("assignmentDescription" + getId).innerText;

    var userIdArray = users.split(" ");

    document.getElementById("selectedUser").value = userIdArray[0]

    userIdArray.shift();
    var userList = document.getElementById("userList");

    for (var i = 0; i < userIdArray.length; i++) {
        var select = document.createElement("select");
        select.name = "selectedUser" + document.querySelectorAll('select[name^="selectedUser"]').length;

        var br = document.createElement("br");

        // Add options to the select list
        var options = document.querySelectorAll('select[name="selectedUser"] option');

        for (var j = 0; j < options.length; j++) {
            var option = options[j].cloneNode(true);
            if (option.value === userIdArray[i]) {
                        option.selected = true
            }
            select.appendChild(option);
        }

        var deleteBtn = document.createElement("button");
        deleteBtn.className = "delete-btn";
        deleteBtn.type = "button";
        deleteBtn.textContent = "x";

        deleteBtn.onclick = function(select, deleteBtn, br) {
            return function() {
                userList.removeChild(select);
                userList.removeChild(deleteBtn);
                userList.removeChild(br);
            }
        }(select, deleteBtn, br);

        // Append the new select list and delete button to the user list container
        userList.appendChild(select);
        userList.appendChild(deleteBtn);
        userList.appendChild(br);
    }
}
