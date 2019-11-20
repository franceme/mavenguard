package vt.edu.mavenguard;

import com.google.common.cache.CacheBuilder;
import frontEnd.Interface.outputRouting.ExceptionHandler;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import vt.edu.mavenguard.core.Base;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

import static vt.edu.mavenguard.core.RetrieveFiles.retrieveDepFiles;
import static vt.edu.mavenguard.core.RetrieveFiles.retrieveSourceFiles;

/**
 * The ScanFiles goal of MavenGuard.
 *
 * @author franceme
 * @version 00.00.00
 * @since V00.00.00
 */
@Mojo(name = "scanFiles")
public class ScanFilesMojo extends AbstractMojo {

    @Parameter(readonly = true, defaultValue = "${project.build.directory}")
    private File buildDir;

    @Parameter(readonly = true, defaultValue = "${project.build.directory}/classes")
    private File buildOutput;

    @Parameter(readonly = true, defaultValue = "${project.dependencyArtifacts}")
    private Set<Artifact> artifactSet;

    public void execute() throws MojoExecutionException {
        getLog().info(vt.edu.mavenguard.core.Utils.cmdSplit);

        ArrayList<String> sourceFiles = retrieveSourceFiles(buildOutput);
        ArrayList<String> dependencyFiles = retrieveDepFiles(artifactSet);

        File outputFile = new File(buildDir, "_cryptoguard.json");
        if (outputFile.exists()) {
            outputFile.delete();
        }

        CacheBuilder.newBuilder().maximumSize(0);

        getLog().info("Starting Scan");
        try {
            getLog().info("Output File at " +
                    Base.entryPoint(sourceFiles, dependencyFiles, outputFile.getAbsolutePath(), null, 3));
        } catch(ExceptionHandler e) {
            getLog().error(e.toString());
        }
        getLog().info(vt.edu.mavenguard.core.Utils.cmdSplit);
    }
}
