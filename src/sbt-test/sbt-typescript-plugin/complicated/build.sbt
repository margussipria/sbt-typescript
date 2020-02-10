
lazy val root = (project in file("."))
  .enablePlugins(SbtWeb)
  .settings(
    JsEngineKeys.engineType := JsEngineKeys.EngineType.Node
  )

libraryDependencies += "org.webjars" % "bootstrap" % "3.0.2"
