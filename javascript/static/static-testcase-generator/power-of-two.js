function getRandomInt(max) {
    return Math.floor(Math.random() * max);
}

function getRandomRange(min, max) {
    return Math.floor(Math.random() * (max-min)) + min;
}

/* Randomize array in-place using Durstenfeld shuffle algorithm */
function shuffleArray(array) {
    for (var i = array.length - 1; i >= 0; i--) {
        var j = Math.floor(Math.random() * (i + 1));
        var temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}

function generate() {
    let output = "false";
    let intput = 0;

    let coin_flip = getRandomInt(100) % 2;
    if (coin_flip == 1) {
        output = "true";
        input = 1 << getRandomInt(30);
    } else {
        output = "false";
        input = getRandomRange(-1<<31, 0);
        if (getRandomInt(100) % 2 == 1) {
            console.log("Fancy!")
            input = getRandomRange(1, 2147483647);
            if ((input & (input - 1)) == 0) {
                output = "true";
            }
        }
    }

    document.querySelector("#input").innerHTML=String(input);
    document.querySelector("#output").innerHTML=output;
}