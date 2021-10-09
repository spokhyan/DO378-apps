'use strict';

const express = require('express');
const bodyParser = require('body-parser');
const _const = require('./lib/constants');
const seedrandom = require('seedrandom');

const app = express();

app.use(bodyParser.json());

app.use(function(req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    next();
  });

const round = (number, decimalPlaces) => {
    const fot = Math.pow( 10, decimalPlaces);
    return Math.round(number * fot) / fot;
}

const variability = 0.1

const postData = (req, res) => {
    const src = req.body.source;
    const target = req.body.target;
    console.log(`Serving request: ${JSON.stringify(req.body)}`)

    const dataWithDate = [];

    var rng;
    var reverse;
    if (src > target) {
        rng = seedrandom(src + target);
        reverse = false;
    } else {
        rng = seedrandom(target + src);
        reverse = true;
    }

    var currentValue = 1 + rng()/10;
    for (let i = 0; i < 30; i++) {
        currentValue = currentValue + rng()*variability*2 - variability;
        var elementWithDate = {}
        var date = new Date();
        date.setDate(date.getDate() - i);
        elementWithDate["date"] = date;
        if (reverse) { 
            elementWithDate["value"] = round(currentValue,3);
        } else {
            elementWithDate["value"] = round(1/currentValue,3);
        }
        dataWithDate.push(elementWithDate);
    }

    res.setHeader('Content-Type', 'application/json');
    res.json(dataWithDate).status(200);
}

app.post('/', postData);

app.listen(_const.PORT, () => {
    console.log(
        "  App is running at http://localhost:%d",
        _const.PORT,
    );
    console.log("  Press CTRL-C to stop\n");
});
