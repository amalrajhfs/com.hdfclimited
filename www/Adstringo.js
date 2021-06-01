var exec = require('cordova/exec');



/* For Android call filepath*/
exports.compressFile = function(data, success, error) {
    exec(success, error, "Adstringo", "compressFile", [data]);
};

/* For Android Call Base64string */
exports.compressString = function(data, success, error) {
    exec(success, error, "Adstringo", "compressString", [data]);
};
/* For Android Video   */
exports.compressVideo = function(data, success, error) {
    exec(success, error, "Adstringo", "compressVideo", [data]);
};
/* For Android Audio   */
exports.compressAudio = function(data, success, error) {
    exec(success, error, "Adstringo", "compressAudio", [data]);
};



/*for IOS call*/
exports.compressString = function(data, success, error) {
    exec(success, error, "Adstringo", "compressString", [data]);
};

/* for IOS CompressFile*/
exports.compressFile = function(data, success, error) {
    exec(success, error, "Adstringo", "compressFile", [data]);
};

/* for IOS CompressFile*/
exports.compressVideo = function(data, success, error) {
    exec(success, error, "Adstringo", "compressVideo", [data]);
};

/* for IOS CompressFile*/
exports.compressAudio = function(data, success, error) {
    exec(success, error, "Adstringo", "compressAudio", [data]);
};

