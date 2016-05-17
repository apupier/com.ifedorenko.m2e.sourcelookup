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
package com.ifedorenko.m2e.binaryproject.ui.internal;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;

public class BinaryProjectImportAction implements IObjectActionDelegate {

  private IStructuredSelection selection;

  private IWorkbench workbench;

  private IWorkbenchPartSite site;

  @Override
  public void run(IAction action) {
    BinaryProjectImportWizard wizard = new BinaryProjectImportWizard();
    wizard.init(workbench, selection);
    if (!wizard.getInitialDependencies().isEmpty()) {
      WizardDialog dialog = new WizardDialog(site.getShell(), wizard);
      dialog.open();
    } else {
      wizard.dispose();
    }
  }

  @Override
  public void selectionChanged(IAction action, ISelection selection) {
    if (selection instanceof IStructuredSelection) {
      this.selection = (IStructuredSelection) selection;
    } else {
      selection = null;
    }
  }

  @Override
  public void setActivePart(IAction action, IWorkbenchPart targetPart) {
    site = targetPart.getSite();
    workbench = site.getWorkbenchWindow().getWorkbench();
  }

}
