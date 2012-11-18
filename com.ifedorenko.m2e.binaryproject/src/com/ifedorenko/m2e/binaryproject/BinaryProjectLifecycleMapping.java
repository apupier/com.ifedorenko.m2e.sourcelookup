package com.ifedorenko.m2e.binaryproject;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.m2e.core.MavenPlugin;
import org.eclipse.m2e.core.internal.MavenPluginActivator;
import org.eclipse.m2e.core.project.IMavenProjectFacade;
import org.eclipse.m2e.core.project.configurator.AbstractBuildParticipant;
import org.eclipse.m2e.core.project.configurator.AbstractLifecycleMapping;
import org.eclipse.m2e.core.project.configurator.AbstractProjectConfigurator;
import org.eclipse.m2e.core.project.configurator.ILifecycleMappingConfiguration;
import org.eclipse.m2e.core.project.configurator.MojoExecutionKey;
import org.eclipse.m2e.core.project.configurator.ProjectConfigurationRequest;

@SuppressWarnings( "restriction" )
public class BinaryProjectLifecycleMapping
    extends AbstractLifecycleMapping
{
    private final ClasspathConfigurator configurator;

    public BinaryProjectLifecycleMapping()
    {
        ClasspathConfigurator configurator = new ClasspathConfigurator();
        configurator.setProjectManager( MavenPlugin.getMavenProjectRegistry() );
        configurator.setMavenConfiguration( MavenPlugin.getMavenConfiguration() );
        configurator.setMarkerManager( MavenPluginActivator.getDefault().getMavenMarkerManager() );

        this.configurator = configurator;
    }

    @Override
    public Map<MojoExecutionKey, List<AbstractBuildParticipant>> getBuildParticipants( IMavenProjectFacade project,
                                                                                       IProgressMonitor monitor )
        throws CoreException
    {
        return Collections.emptyMap();
    }

    @Override
    public List<AbstractProjectConfigurator> getProjectConfigurators( IMavenProjectFacade project,
                                                                      IProgressMonitor monitor )
        throws CoreException
    {
        return Collections.emptyList();
    }

    @Override
    public boolean hasLifecycleMappingChanged( IMavenProjectFacade newFacade,
                                               ILifecycleMappingConfiguration oldConfiguration, IProgressMonitor monitor )
    {
        return false;
    }

    public void configure( ProjectConfigurationRequest request, IProgressMonitor monitor )
        throws CoreException
    {
        configurator.configure( request, monitor );
    }

    public void unconfigure( ProjectConfigurationRequest request, IProgressMonitor monitor )
    {
    }
}
