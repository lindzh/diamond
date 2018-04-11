package com.taobao.diamond.maven;

import com.taobao.diamond.client.impl.DiamondEnv;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Mojo(name = "diamondConfig", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class DiamondPluginMojo extends AbstractMojo {
    @Parameter(defaultValue = "${project.basedir}")
    private File outputDirectory;
    @Parameter(required = true)
    private String fileName;
    @Parameter(required = true)
    private String group;
    @Parameter(required = true)
    private String dataId;
    @Parameter(defaultValue = "${maven.config.plugin}")
    private boolean skip;
    @Parameter(defaultValue = "false")
    private boolean hasSubModule;
    @Parameter(defaultValue = "5000")
    private int timeout;
    @Parameter(defaultValue = "default")
    private String unitName;
    @Parameter(defaultValue = "false")
    private boolean overwriteOnExist;
    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    protected MavenProject project;

    private FileWriter w = null;


    public void makeFile(String content) throws IOException {
        File f = outputDirectory;
        if (!f.exists()) {
            f.mkdirs();
        }
        File conf = new File(f, fileName);
        if(conf.exists()){
            if(overwriteOnExist){
              conf.delete();
            };
        }
        w = new FileWriter(conf);
        w.write(content + "\r\n");
        w.close();
    }

    @Override
    public void execute() throws MojoExecutionException {
        if (skip) {
            getLog().info("Skip init config file.");
            return;
        }
        if (!project.isExecutionRoot()) {
            if (hasSubModule)
                return;
        }
        try {
            String config = DiamondEnv.getDiamondUnitEnv(unitName).getConfig(dataId, group, timeout);
            makeFile(config);
            getLog().info("Create config file "+fileName+"[SUCCESS].");
        } catch (IOException e) {
            throw new MojoExecutionException("GetConfig from diamond failed,unitName:" + unitName + " dataId:" + dataId + " group:" + group, e);
        }
    }
}
