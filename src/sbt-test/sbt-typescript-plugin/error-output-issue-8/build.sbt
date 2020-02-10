
lazy val root = (project in file("."))
  .enablePlugins(SbtWeb)
  .settings(
    name := "test-build",

    WebKeys.reporter := new CustomTestReporter(target.value),
    JsEngineKeys.engineType := JsEngineKeys.EngineType.Node
  )
