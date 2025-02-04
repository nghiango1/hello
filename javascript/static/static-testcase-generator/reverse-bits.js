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
    let original = getRandomRange(1, 0b11111111111111111111111111111111);
    
    let coin_flip = getRandomInt(100) % 2;
    if (coin_flip == 1) {
        console.log("Fancy");
        if (original < 0b10000000000000000000000000000000)
            original += 0b10000000000000000000000000000000;
    }

    let input = original.toString(2);
    if (input.length < 32) {
        input = "0".repeat(32 - input.length) + input;
    }
    let output = parseInt(input.split("").reverse().join(""), 2);

    document.querySelector("#input").innerHTML=String(input);
    document.querySelector("#output").innerHTML=String(output);
}