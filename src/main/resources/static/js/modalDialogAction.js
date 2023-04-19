function openModal(getId) {
    let id =document.getElementById("inputId" + getId).innerText;

    console.log("This is Id: " + id);
    document.getElementById("inputForm").setAttribute("action", "/report/edit")
    document.getElementById("myModal").style.display = "block";
    document.getElementById("description").value = document.getElementById("inputDesc" + getId).innerText;
    document.getElementById("begi").value = document.getElementById("inputBegi" + getId).innerText;
    document.getElementById("end").value = document.getElementById("inputEnd" + getId).innerText;
    document.getElementById("pause").value = document.getElementById("inputPause" + getId).innerText;
    document.getElementById("date").value = document.getElementById("inputMade" + getId).innerText;

        document.getElementById("sId").value = document.getElementById("inputId" + getId).innerText;


        console.log( document.getElementById("sId").value)
        console.log(document.getElementById("begi").value)
        console.log(document.getElementById("end").value)

    console.log(document.getElementById("inputMade" + getId).innerText)

}

/* Close the modal window */
function closeModal() {

    document.getElementById("myModal").style.display = null;
    document.getElementById("description").value = null;
    document.getElementById("begin").value = null;
    document.getElementById("end").value = null;
    document.getElementById("pause").value = null;
    document.getElementById("setId").value = null;
    document.getElementById("date").value = null;
    document.getElementById("myModal").style.display = "none";

}

function openModalToSaveNewReport(){
    document.getElementById("inputForm").setAttribute("action", "/report")
    document.getElementById("myModal").style.display = "block";
}

function openModalToSaveVacation(){
    document.getElementById("inputVacationForm").setAttribute("action", "/vacations")
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
    document.getElementById("inputSickleaveForm").setAttribute("action", "/sickleaves")
    document.getElementById("sickLeaveModal").style.display = "block";
}
function closeSickLeaveModal(){
    document.getElementById('sickleaveBegin').value = null;
    document.getElementById('sickleaveEnd').value = null;
    document.getElementById('sickLeaveAtOwn').value = null;
    document.getElementById('sickleaveDescription').value = null;
    document.getElementById('badDate').innerHTML  = null;
    document.getElementById("sickLeaveModal").style.display = "none";
}