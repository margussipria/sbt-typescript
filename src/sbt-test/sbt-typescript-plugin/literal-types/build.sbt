
lazy val root = (project in file("."))
  .enablePlugins(SbtWeb)
  .settings(
    JsEngineKeys.engineType := JsEngineKeys.EngineType.Node
  )
