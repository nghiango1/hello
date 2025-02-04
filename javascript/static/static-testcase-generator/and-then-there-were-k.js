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
    t = 1;
    
    input = [];
    output = [];
    for (let i = 0; i < t; i ++ ) {
        let x = getRandomRange(1, 1000000000);
        input.push(x);
        while ((x & (x-1)) != 0) {
            x = x & (x - 1);
        }
        output.push(x-1);
    }
    
    function InputToString(input) {
        let line1 = String(input.length);
        let testCases = input.reduce((x, e) => {return x +"\n"+ e;});
        return `${line1}\n${testCases}`
    }
    
    function OutputToString(output) {
        let results = output.reduce((x, e) => {return x +"\n"+ e;});
        return `${results}`
    }
    
    document.querySelector("#input").innerHTML=InputToString(input);
    document.querySelector("#output").innerHTML=OutputToString(output);
}