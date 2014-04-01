package com.packtpub.e4.clock.ui.views;

import java.net.URL;

import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.packtpub.e4.clock.ui.internal.TimeZoneComparator;

public class TimeZoneTreeView extends ViewPart {

	private TreeViewer treeViewer;

	public TimeZoneTreeView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		
		ResourceManager rm = JFaceResources.getResources();
		LocalResourceManager lrm = new LocalResourceManager(rm);
		ImageRegistry ir = new ImageRegistry(lrm);
		FontRegistry fr = JFaceResources.getFontRegistry();
		URL sample = getClass().getResource("/icons/sample.gif");
		ir.put("sample", ImageDescriptor.createFromURL(sample));
		
		treeViewer = new TreeViewer(parent, SWT.V_SCROLL|SWT.H_SCROLL|SWT.MULTI);
		treeViewer.setLabelProvider(
				new DelegatingStyledCellLabelProvider(
						new TimeZoneLabelProvider(ir,fr)));
		treeViewer.setContentProvider(new TimeZoneContentProvider());
		treeViewer.setInput(new Object[]{TimeZoneComparator.getTimeZones()});
	}

	@Override
	public void setFocus() {
		treeViewer.getControl().setFocus();
	}

}
