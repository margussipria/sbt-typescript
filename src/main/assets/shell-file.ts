///<reference path="types/node.d.ts"/>
///<reference path="types/sbt-ts.d.ts"/>
///<reference path="typescript-compiler.ts"/>

(() => {
  const fs = require('fs');
  const jst = require('jstranspiler');
  const ts = require('typescript');
  const path = require('path');
  const args: sbtweb.Arguments<sbtts.Options> = jst.args(process.argv);

  const typescriptCompiler = new sbtts.TypescriptCompiler(fs, path, ts, args);
  const compilerOutput = typescriptCompiler.compile(args);

  console.log('\u0010' + JSON.stringify(compilerOutput));
})();
