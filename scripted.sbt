scriptedLaunchOpts ++= Seq(
  "-Xmx1024M",
  "-XX:MaxPermSize=256M",
  s"-Dplugin.version=${(ThisBuild / version).value}"
)

scriptedBufferLog := false
