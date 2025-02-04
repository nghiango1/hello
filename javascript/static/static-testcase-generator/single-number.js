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
    len = getRandomInt(30000)
    
    input = [];
    x = getRandomRange(-30000, 30000);
    
    arr = _.range(-30000, 30000, 1);
    shuffleArray(arr);
    
    input.push(x);
    for (let i = 0; i < arr.length; i ++ ) {
        if (arr[i] == x) {
            continue;
        }
        if (getRandomInt(4) % 2 == 1) {
            continue;
        }
        input.push(arr[i]);
        input.push(arr[i]);
        if (input.length >= len-1) {
            break;
        }
    }
    
    shuffleArray(input);
    
    function InputToString(input) {
        let line1 = String(input.length);
        let line2 = input.reduce((x, e) => {return x +" "+ e;});
        return `${line1}\n${line2}`
    }
    
    document.querySelector("#input").innerHTML=InputToString(input);
    document.querySelector("#output").innerHTML=String(x);
}