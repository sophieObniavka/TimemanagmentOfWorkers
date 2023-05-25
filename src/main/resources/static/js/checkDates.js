function checkDates(errorMessage, limit, beginId, endId, atOwn, message, leftId){
    console.log("Blya " + beginId);
    var begin = document.getElementById(beginId).value;
    var end = document.getElementById(endId).value;
    var leftSpan = document.getElementById(leftId);
    var left = parseInt(leftSpan.textContent.split(":")[1].trim());

    console.log("Begin " + begin);
    console.log("End " + end);
    console.log("Left " + left);
    var checkBox = document.getElementById(atOwn);
    var isChecked = checkBox.checked;

    var date1 = new Date(begin);
    var date2 = new Date(end);
    var amount = getWorkDays(date1, date2)


    if(date1 > date2){
        document.getElementById(message).innerHTML = errorMessage;
        return false;
    }

    if(amount>left && !isChecked){
        console.log("Too much");
        document.getElementById(message).innerHTML = null;
        document.getElementById(message).innerHTML = limit;
        return false;
    }

}

function getWorkDays(startDate, endDate) {

    var oneDay = 24 * 60 * 60 * 1000;
    var diffInMs = endDate.getTime() - startDate.getTime();

    var diffInDays = Math.round(diffInMs / oneDay);

    var workDays = 0;
    for (var i = 0; i <= diffInDays; i++) {
        var currentDate = new Date(startDate.getTime() + (i * oneDay));
        var currentDay = currentDate.getDay();
        if (currentDay != 0 && currentDay != 6) {
            workDays++;
        }
    }

    return workDays;
}
