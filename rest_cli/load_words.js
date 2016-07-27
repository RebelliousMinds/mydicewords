var request = require('request');
var fs = require('fs');

var serverAddressFile = fs.readFileSync('/opt/presidio/serverFile.json', 'utf8');
var serverAddresses = JSON.parse(serverAddressFile);

var wordFile = fs.readFileSync('diceware.wordlist.asc', 'utf8');

var lines = wordFile.split("\n");

for ( lineNumber in lines ) {
	var elements = lines[lineNumber].split("\t")
	
	var options = {
	    uri: 'https://' + serverAddresses.server + ':8443/word',
	    method: 'POST',
	    json: {
	      "wordNumber": elements[0],
	      "word": elements[1]
	    },
		headers: {
			'Authorization': 'Basic ' + new Buffer('admin:Awesome123!').toString('base64')
		},
		rejectUnauthorized: false
	};

	request(options, function (error, response, body) {

        if(error) {
            console.log( error );
            return;
        }

	    console.log("Got " + response.statusCode );

	    if (!error && response.statusCode == 200) {
	        console.log(body);
	    }

	});
}
		
		
