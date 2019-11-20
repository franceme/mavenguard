package vt.edu.mavenguard.core;

import org.apache.maven.artifact.Artifact;
import util.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

import static vt.edu.mavenguard.core.Utils.mavenRepo;

/**
 * <p>RetrieveFiles class.</p>
 * The router that retrieves the source/dependency files.
 *
 * @author franceme
 * @version 00.00.00
 * @since V00.00.00
 */
public class RetrieveFiles {
    public static ArrayList<String> retrieveSourceFiles(File targetDir) {
        ArrayList<String> output = new ArrayList<>();

        try {
            Files.walkFileTree(Paths.get(targetDir.toURI()), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                        throws IOException
                {
                    Objects.requireNonNull(file);
                    Objects.requireNonNull(attrs);

                    if (file.toAbsolutePath().toString().endsWith(".class"))
                        output.add(file.toFile().getAbsolutePath());

                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            //TODO - Add here
        }

        return output;
    }

    public static ArrayList<String> retrieveDepFiles(Set<Artifact> artifactSet) {
        ArrayList<String> output = new ArrayList<>();

        for (Artifact art:artifactSet)
        {
            //dependencyFiles.add(new depKlass(art).getFilePath(filePath));
            if (art.getFile() != null)
                output.add(art.getFile().getAbsolutePath());
            else
            {
                String jarName = art.getArtifactId() + "-" + art.getVersion() + ".jar";
                StringBuilder group = new StringBuilder();

                //TODO - Replace this eventually, the groupId cannot be manipulated as a string for some reason?
                for (Character kar:art.getGroupId().toCharArray())
                    if (String.valueOf(kar).equals("."))
                        group.append(Utils.fileSep);
                    else
                        group.append(kar);

                output.add(util.Utils.osPathJoin(mavenRepo, group.toString(), art.getArtifactId(), art.getVersion(), jarName));
            }
        }

        return output;
    }
}
