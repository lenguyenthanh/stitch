// https://typelevel.org/sbt-typelevel/faq.html#what-is-a-base-version-anyway
ThisBuild / tlBaseVersion := "0.0" // your current series x.y

ThisBuild / organization     := "se.thanh"
ThisBuild / organizationName := "Thanh Le"
ThisBuild / licenses         := Seq(License.Apache2)
ThisBuild / developers := List(
  // your GitHub handle and name
  tlGitHubDev("lenguyenthanh", "Thanh Le")
)

// publish to s01.oss.sonatype.org (set to true to publish to oss.sonatype.org instead)
ThisBuild / tlSonatypeUseLegacyHost := false

// publish website from this branch
ThisBuild / tlSitePublishBranch := Some("main")

ThisBuild / githubWorkflowPublishTargetBranches := Seq()   // Don't publish anywhere
ThisBuild / scalaVersion                        := "3.1.2" // the default Scala

lazy val root = tlCrossRootProject.aggregate(core)

lazy val core = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Pure)
  .in(file("core"))
  .settings(
    name := "stitch",
    libraryDependencies ++= Seq(
      "org.typelevel" %%% "cats-core"           % "2.7.0",
      "org.typelevel" %%% "cats-effect"         % "3.3.12",
      "org.typelevel" %%% "cats-parse"          % "0.3.6",
      "org.scalameta" %%% "munit"               % "0.7.29" % Test,
      "org.typelevel" %%% "munit-cats-effect-3" % "1.0.7"  % Test
    )
  )

lazy val docs = project.in(file("site")).enablePlugins(TypelevelSitePlugin)
