function resetSearch(link) {
    var keywordInput = document.getElementById("keyword");
    if (keywordInput.value.trim() === "") {
        window.location.href = link;
    }
}

const loadFile = function(event) {
    var idToSet = '';
    if(document.getElementById('profileImageForm') === null){
        console.log(true);
        idToSet = 'defaultForm';
    }
    else{
        idToSet = 'profileImageForm'
    }

    const image = document.getElementById(idToSet);
    image.src = URL.createObjectURL(event.target.files[0]);

};