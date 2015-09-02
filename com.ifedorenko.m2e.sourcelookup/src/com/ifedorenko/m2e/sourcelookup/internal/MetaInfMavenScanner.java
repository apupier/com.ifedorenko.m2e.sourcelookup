/*******************************************************************************
 * Copyright (c) 2011-2012 Igor Fedorenko
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Igor Fedorenko - initial API and implementation
 *******************************************************************************/
package com.ifedorenko.m2e.sourcelookup.internal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Scans given location for META-INF/maven pom.properties and extracts IProject, IMavenProjectFacade or GAV.
 */
public abstract class MetaInfMavenScanner<T>
{

    private static final String META_INF_MAVEN = "META-INF/maven";

    public List<T> scan( File file, String filename )
    {
        List<T> result = new ArrayList<T>();
        if ( file != null )
        {
            if ( file.isDirectory() )
            {
                scanFilesystem( new File( file, META_INF_MAVEN ), filename, result );
            }
            else if ( file.isFile() )
            {
                try
                {
                    try (JarFile jar = new JarFile( file ))
                    {
                        scanJar( jar, filename, result );
                    }
                }
                catch ( IOException e )
                {
                    // fall through
                }
            }
        }
        return result;
    }

    private void scanJar( JarFile jar, String filename, List<T> result )
        throws IOException
    {
        Enumeration<JarEntry> entries = jar.entries();
        while ( entries.hasMoreElements() )
        {
            JarEntry entry = entries.nextElement();
            if ( !entry.isDirectory() )
            {
                String name = entry.getName();
                if ( name.startsWith( META_INF_MAVEN ) && name.endsWith( filename ) )
                {
                    try
                    {
                        T t = visitJarEntry( jar, entry );
                        if ( t != null )
                        {
                            result.add( t );
                        }
                    }
                    catch ( IOException e )
                    {
                        // ignore
                    }
                }
            }
        }
    }

    private void scanFilesystem( File dir, String filename, List<T> result )
    {
        File[] files = dir.listFiles();
        if ( files == null )
        {
            return;
        }
        for ( File file : files )
        {
            if ( file.isDirectory() )
            {
                scanFilesystem( file, filename, result );
            }
            else if ( file.isFile() && filename.equals( file.getName() ) )
            {
                try
                {
                    T t = visitFile( file );
                    if ( t != null )
                    {
                        result.add( t );
                    }
                }
                catch ( IOException e )
                {
                    // ignore
                }
            }
        }
    }

    protected abstract T visitFile( File file )
        throws IOException;

    protected abstract T visitJarEntry( JarFile jar, JarEntry entry )
        throws IOException;
}
