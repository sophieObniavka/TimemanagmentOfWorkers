document.addEventListener('DOMContentLoaded', () => {

    const toastContainer = document.getElementById('toastContainer');
    const toastElements = document.querySelectorAll('.toast');

    function displayToast(toast) {
        onBeforeOpen();
        toast.classList.add('show');

        const timer = setTimeout(() => {
            closeToast(toast);
            setTimeout(() => {
                displayNextToast();
            }, 3000);
        }, 15000);

        toast.dataset.timer = timer;
    }

    function closeToast(toast) {
        toast.classList.remove('show');
        clearTimeout(parseInt(toast.dataset.timer));
    }

    function displayNextToast() {
        const toast = toastElements[currentIndex];
        closeToast(toast);
        currentIndex = (currentIndex + 1) % toastElements.length;
        displayToast(toast);
    }

    const hasNotificationsShownToday = getCookie('notificationsShownToday');
    if (hasNotificationsShownToday) {
        return; // Exit early if already shown
    }

    toastElements.forEach((toast) => {
        const closeIcon = toast.querySelector('.close');
        closeIcon.addEventListener('click', () => {
            closeToast(toast);
        });
    });

    let currentIndex = 0;
    toastElements.forEach((toast) => {
        displayToast(toast);
    });

    setCookie('notificationsShownToday', true, 1);
});

function getCookie(name) {
    const cookieName = name + '=';
    const decodedCookie = decodeURIComponent(document.cookie);
    const cookieArray = decodedCookie.split(';');
    for (let i = 0; i < cookieArray.length; i++) {
        let cookie = cookieArray[i];
        while (cookie.charAt(0) === ' ') {
            cookie = cookie.substring(1);
        }
        if (cookie.indexOf(cookieName) === 0) {
            return cookie.substring(cookieName.length, cookie.length);
        }
    }
    return '';
}

function setCookie(name, value, days) {
    const date = new Date();
    date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
    const expires = 'expires=' + date.toUTCString();
    document.cookie = name + '=' + value + ';' + expires + ';path=/';
}
function onBeforeOpen(e) {
    var audio = new Audio('https://drive.google.com/uc?export=download&id=1M95VOpto1cQ4FQHzNBaLf0WFQglrtWi7');
    audio.play();
}