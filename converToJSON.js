var fs = require("fs");

var sampleObject = {

    "hp": 100,
    "str": 5,
    "intel": 0,
    "stam": 20,
    "spd": 3,
    "dex": 20,
    "color1": 1,
    "color2": 2,
    "name": "player1"
}

fs.writeFile("./object.json", JSON.stringify(sampleObject), (err) => {
    if (err) {
        console.error(err);
        return;
    };
    console.log("File has been created");
});
