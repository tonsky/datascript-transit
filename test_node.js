#!/usr/local/bin/node

var fs = require('fs'),
    vm = require('vm');

global.goog = {};

global.CLOSURE_IMPORT_SCRIPT = function(src) {
  require('./target/none/goog/' + src);
  return true;
};

function nodeGlobalRequire(file) {
  vm.runInThisContext.call(global, fs.readFileSync(file), file);
}

nodeGlobalRequire('./target/main.js');
datascript.test.transit.test_all();
