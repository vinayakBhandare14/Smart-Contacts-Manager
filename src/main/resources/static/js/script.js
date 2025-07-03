console.log("Script loading.....!");


let currentTheme=getTheme();
console.log(currentTheme);

document.addEventListener("DOMContentLoaded", () => {
    changetheme();
});


//TODO
function changetheme(){

    // set to web page
    chnagePageTheme(currentTheme,currentTheme);

    // set the listener to chnage theme
    const chnageThemeButton = document.querySelector('#theame_change_button');

    chnageThemeButton.addEventListener('click', (event) => {
        let oldTheme = currentTheme;
        console.log("Chnage theme button clicked....!");

       

        if (currentTheme === 'light') {
            //Theme to dark
            currentTheme='dark';
        }else{currentTheme='light';}

        chnagePageTheme(currentTheme,oldTheme);
    });
}

function setTheme(theme) {
    localStorage.setItem("theme",theme);
}

function getTheme() {
    let theme=localStorage.getItem("theme");

    // if (theme) {
    //     return theme;
    // }else{return "light";}

        //OR

    return theme ? theme : "light";
}

// chnage current page theme
function chnagePageTheme(theme, oldTheme) {

    //Updating in local Storage
        setTheme(currentTheme);

        //remove oldTheme
        document.querySelector('html').classList.remove(oldTheme);
        //adding the current theme
        document.querySelector('html').classList.add(theme);

        //chnage the text of the button
    document.querySelector('#theame_change_button').querySelector('span').textContent = theme === 'light' ? 'Dark' : 'Light';

    
}
