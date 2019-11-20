package vt.edu.mavenguard;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import vt.edu.mavenguard.core.Utils;

/**
 * The Versions goal of MavenGuard.
 *
 * @author franceme
 * @version 00.00.00
 * @since V00.00.00
 */
@Mojo(name = "version")
public class VersionMojo extends AbstractMojo {
    public void execute() throws MojoExecutionException {
        getLog().info(Utils.cmdSplit);
        getLog().info("MavenGuard Version: " + Utils.projectVersion + ".");
        getLog().info("CryptoGuard Version: " + util.Utils.projectVersion + ".");
        getLog().info(Utils.cmdSplit);
    }
}
