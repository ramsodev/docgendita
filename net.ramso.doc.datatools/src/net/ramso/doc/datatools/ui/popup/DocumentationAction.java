/*******************************************************************************
 * Copyright (c) 2001, 2004, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package net.ramso.doc.datatools.ui.popup;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.ramso.doc.datatools.Messages;
import net.ramso.doc.datatools.ui.wizards.DocumentationWizard;

import org.eclipse.datatools.connectivity.sqm.core.ui.explorer.virtual.IVirtualNode;
import org.eclipse.datatools.modelbase.sql.schema.SQLObject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.navigator.CommonViewer;

public class DocumentationAction extends Action {
	private static final String				TEXT		= Messages.DocumentationAction_title;
	private static final ImageDescriptor	descriptor	= null;
	protected SelectionChangedEvent			event;
	protected CommonViewer					viewer;

	public DocumentationAction() {
		this.setImageDescriptor(descriptor);
		this.setDisabledImageDescriptor(descriptor);
		this.setText(TEXT);
		this.setToolTipText(TEXT);
	}

	@SuppressWarnings("unchecked")
	private void addSQLObject(List linkedList, Object selected) {
		if (selected instanceof SQLObject) {
			linkedList.add(selected);
		}
	}

	@SuppressWarnings("unchecked")
	protected List getMultipleSelection() {
		List linkedList = new LinkedList();
		if (event.getSelection() instanceof IStructuredSelection) {
			for (Iterator i = ((IStructuredSelection) event.getSelection())
					.iterator(); i.hasNext();) {
				Object nextSelected = i.next();
				if (nextSelected instanceof IVirtualNode) {
					Object[] children = ((IVirtualNode) nextSelected)
							.getChildrenArray();
					for (int j = 0, n = children.length; j < n; j++) {
						addSQLObject(linkedList, children[j]);
					}
				}
				else {
					addSQLObject(linkedList, nextSelected);
				}
			}
		}
		return linkedList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		try {
			List list = this.getMultipleSelection();
			if (list.size() > 0) {
				Wizard wizard = new DocumentationWizard(list);
				WizardDialog dialog = new WizardDialog(viewer.getControl()
						.getShell(), wizard);
				dialog.create();
				dialog.open();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void selectionChanged(SelectionChangedEvent event) {
		this.event = event;
	}

	public void setCommonViewer(CommonViewer viewer) {
		this.viewer = viewer;
	}
}
