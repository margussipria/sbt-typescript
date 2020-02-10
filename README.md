sbt-typescript
==============

<a href="https://raw.githubusercontent.com/ArpNetworking/sbt-typescript/master/LICENSE">
    <img src="https://img.shields.io/hexpm/l/plug.svg"
         alt="License: Apache 2">
</a>
<a href="https://travis-ci.org/margussipria/sbt-typescript/">
    <img src="https://travis-ci.org/margussipria/sbt-typescript.png"
         alt="Travis Build">
</a>
<a href="http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.arpnetworking%22%20a%3A%22sbt-typescript%22">
    <img src="https://img.shields.io/maven-central/v/com.arpnetworking/sbt-typescript.svg"
         alt="Maven Artifact">
</a>

Allows TypeScript to be used from within sbt. Leverages the functionality of com.typesafe.sbt:js-engine to run the
typescript compiler.

To use this plugin use the addSbtPlugin command within your project's plugins.sbt (or as a global setting) i.e.:

```scala
addSbtPlugin("com.arpnetworking" % "sbt-typescript" % "0.3.4")
resolvers += Resolver.typesafeRepo("releases")
```

You will also need to enable the SbtWeb plugin in your project.

By default, all typescript files (*.ts and *.tsx) are included in the compilation and will generate corresponding javascript
files. In addition, if source map generation is enabled, the .ts and .tsx files will be copied to the output directory in 
order to make source maps work.  To change this, supply an includeFilter in the TypescriptKeys.typescript task configuration.

The supported sbt settings are:

Option                 | Description
-----------------------|------------
configFile             | By default the sbt-typescript will look into the assets directory (app/assets/tsconfig.json). If you want sbt-typescript look into the root folder, just set this property to 'yourconfigname.json'
sourceRoot             | Specifies the location where debugger should locate TypeScript files instead of source locations.

include and exclude, baseUrl properties of tsconfig are not supported.


For including specific files of your project you can write something like:

```scala
includeFilter in (Assets, typescript) := GlobFilter("myFile.ts")
```

You can also set an exclude filter in the same way:

```scala
excludeFilter in (Assets,typescript) := GlobFilter("*.d.ts") | GlobFilter("*.spec.ts") | GlobFilter("**/typings")
```

A note on typescript compiling speed
------------------------------------

Sometimes the default engine (Trireme) can be quite slow. If you're experiencing long typescript compile times you can always switch to node.js ([remember to install it first](http://nodejs.org/download/)) adding this line to your `build.sbt` file:

```scala
JsEngineKeys.engineType := JsEngineKeys.EngineType.Node
```
