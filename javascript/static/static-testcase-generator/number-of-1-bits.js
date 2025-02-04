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
    let input = getRandomRange(1, 2147483647);;
    let output = 0;
    let temp = input;
    while (temp != 0) {
        if ((temp & 1) == 1)
            output += 1;
        temp >>= 1;
    }

    document.querySelector("#input").innerHTML=String(input);
    document.querySelector("#output").innerHTML=String(output);
}