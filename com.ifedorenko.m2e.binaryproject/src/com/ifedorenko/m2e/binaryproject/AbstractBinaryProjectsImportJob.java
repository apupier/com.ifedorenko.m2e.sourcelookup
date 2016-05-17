/*******************************************************************************
 * Copyright (c) 2012 Igor Fedorenko
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Igor Fedorenko - initial API and implementation
 *******************************************************************************/
package com.ifedorenko.m2e.binaryproject;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.m2e.core.embedder.ArtifactKey;

public abstract class AbstractBinaryProjectsImportJob extends Job {

  public AbstractBinaryProjectsImportJob() {
    super("Import binary projects");
  }

  @Override
  protected IStatus run(IProgressMonitor monitor) {
    List<ArtifactKey> artifacts;
    try {
      artifacts = getArtifactKeys();
    } catch (CoreException e1) {
      return e1.getStatus();
    }

    if (!artifacts.isEmpty()) {
      List<IStatus> errors = new ArrayList<IStatus>();

      for (ArtifactKey artifact : artifacts) {
        try {
          BinaryProjectPlugin.getInstance().create(artifact.getGroupId(), artifact.getArtifactId(),
              artifact.getVersion(), null, monitor);
        } catch (CoreException e) {
          errors.add(e.getStatus());
        }
      }
    }
    return Status.OK_STATUS;
  }

  protected abstract List<ArtifactKey> getArtifactKeys() throws CoreException;

}
