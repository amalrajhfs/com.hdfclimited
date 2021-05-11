var exec = require("cordova/exec");

/* For Android call filepath*/
exports.compressFile = function(data, success, error) {
    exec(success, error, "Adstringo", "compressFile", [data]);
};



