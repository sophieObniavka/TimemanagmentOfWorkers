function reject(id, link){
    document.getElementById("rejectModal").style.display = "block";
    document.getElementById("rejectForm").setAttribute("action", link + id);

}
function closeReject() {
    document.getElementById("comment").value = null;
    document.getElementById("rejectModal").style.display = "none";
}
function confirmAction(text) {
    if (confirm(text)) {
        document.getElementById("confirmForm").submit();
    }
}