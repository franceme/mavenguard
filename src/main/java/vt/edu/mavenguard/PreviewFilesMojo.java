package vt.edu.mavenguard;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

import static vt.edu.mavenguard.core.RetrieveFiles.retrieveDepFiles;
import static vt.edu.mavenguard.core.RetrieveFiles.retrieveSourceFiles;

/**
 * The previewFiles goal of MavenGuard.
 *
 * @author franceme
 * @version 00.00.00
 * @since V00.00.00
 */
@Mojo(name = "previewFiles")
public class PreviewFilesMojo extends AbstractMojo {

    @Parameter(readonly = true, defaultValue = "${project.build.directory}/classes")
    private File buildOutput;

    @Parameter(readonly = true, defaultValue = "${project.dependencyArtifacts}")
    private Set<Artifact> artifactSet;

    public void execute() throws MojoExecutionException {
        getLog().info(vt.edu.mavenguard.core.Utils.cmdSplit);

        ArrayList<String> sourceFiles = retrieveSourceFiles(buildOutput);
        ArrayList<String> dependencyFiles = retrieveDepFiles(artifactSet);

        System.out.println("Found the files");
        sourceFiles.stream().forEach(System.out::println);

        System.out.println();

        System.out.println("Found the dependencies");
        dependencyFiles.stream().forEach(System.out::println);
        getLog().info(vt.edu.mavenguard.core.Utils.cmdSplit);

    }
}
