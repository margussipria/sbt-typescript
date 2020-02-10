sys.props.get("plugin.version") match {
  case Some(version) =>
    addSbtPlugin("eu.sipria.sbt" % "sbt-typescript" % version)
  case _ =>
    sys.error(
      """|The system property 'plugin.version' is not defined.
         |Specify this property using the scriptedLaunchOpts -D.""".stripMargin
    )
}
