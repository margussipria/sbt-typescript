resolvers ++= Seq(
  Resolver.publishMavenLocal,
  Resolver.typesafeRepo("releases")
)

addSbtPlugin("eu.sipria.sbt" % "sbt-typescript" % "0.6.0")

addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.13")

//addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "3.8.1")

addSbtPlugin("com.jsuereth" % "sbt-pgp" % "2.0.1")

