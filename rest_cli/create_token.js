var request = require('request');
var fs = require('fs');

var serverAddressFile = fs.readFileSync('/opt/presidio/serverFile.json', 'utf8');
var serverAddresses = JSON.parse(serverAddressFile);

var options = {
    uri: 'https://' + serverAddresses.server + ':8443/token',
    method: 'POST',
    headers: {
        'Authorization': 'Basic ' + new Buffer('admin:Awesome123!').toString('base64')
    },
    rejectUnauthorized: false
};

request(options, function (error, response, body) {

    if(error) {
        console.log( error );
    }

    console.log("Got " + response.statusCode );

    if (!error && response.statusCode == 200) {
        console.log(body);
    }

});
