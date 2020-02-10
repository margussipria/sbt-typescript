
lazy val root = (project in file("."))
  .enablePlugins(SbtWeb)
  .settings(
    JsEngineKeys.engineType := JsEngineKeys.EngineType.Trireme
  )

libraryDependencies += "org.webjars" % "bootstrap" % "3.0.2"
