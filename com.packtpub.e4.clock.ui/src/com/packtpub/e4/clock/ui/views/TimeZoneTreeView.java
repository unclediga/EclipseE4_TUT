package com.packtpub.e4.clock.ui.views;

import java.net.URL;
import java.util.TimeZone;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.views.properties.IPropertySource;

import com.packtpub.e4.clock.ui.internal.TimeZoneComparator;
import com.packtpub.e4.clock.ui.internal.TimeZoneDialog;
import com.packtpub.e4.clock.ui.internal.TimeZoneSelectionListener;
import com.packtpub.e4.clock.ui.internal.TimeZoneViewerComparator;
import com.packtpub.e4.clock.ui.internal.TimeZoneViewerFilter;

public class TimeZoneTreeView extends ViewPart {

	private TreeViewer treeViewer;
	private TimeZoneSelectionListener selectionListener;

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
		
		// Sorting and filtering
		treeViewer.setData("REVERSE", Boolean.FALSE);
		treeViewer.setComparator(new TimeZoneViewerComparator());
		treeViewer.addFilter(new TimeZoneViewerFilter("GMT"));
		//To remove the expandable nodes next to the tree items, the TreeViewercan be 
		//configured to expand nodes automatically:
		//treeViewer.setExpandPreCheckFilters(true);
		//treeViewer.expandAll();
		treeViewer.expandToLevel(2);

		// Interaction and properties
		treeViewer.addDoubleClickListener(new IDoubleClickListener() {
			
			@Override
			public void doubleClick(DoubleClickEvent event) {
				Viewer viewer = event.getViewer();
				Shell schell = viewer.getControl().getShell();
				ISelection sel = viewer.getSelection();
				
				Object selectedValue = null;
				if(!(sel instanceof IStructuredSelection)||sel.isEmpty()){
					selectedValue = null;
				}else{
					selectedValue = ((IStructuredSelection)sel).getFirstElement();
					if(selectedValue instanceof TimeZone){
						TimeZone tz = (TimeZone) selectedValue;
						new TimeZoneDialog(schell, tz).open();
					}else{
						MessageDialog.openInformation(schell, "Double click", "Double Click detected");
						
					}
					
				}
			}
		});
		
		// Properties
//		System.out.println("Adapter is " + Platform.getAdapterManager().
//				getAdapter(TimeZone.getDefault(),IPropertySource.class));
		getSite().setSelectionProvider(treeViewer);
		
		selectionListener = new TimeZoneSelectionListener(treeViewer, getSite().getPart());
		getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(selectionListener);
		hookContextMenu(treeViewer);
	}

	private void hookContextMenu(Viewer viewer) {
		MenuManager manager = new MenuManager("#PopupMenu");
		Menu menu = manager.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(manager, viewer);
	}

	@Override
	public void dispose() {
		if(selectionListener != null){
			getSite().getWorkbenchWindow().getSelectionService().removeSelectionListener(selectionListener);
			selectionListener = null;
		}
		super.dispose();
	}

	@Override
	public void setFocus() {
		treeViewer.getControl().setFocus();
	}

}
