package com.packtpub.e4.clock.ui.views;

import java.util.TimeZone;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.part.ViewPart;

import com.packtpub.e4.clock.ui.internal.TimeZoneDispayColumn;
import com.packtpub.e4.clock.ui.internal.TimeZoneIDColumn;
import com.packtpub.e4.clock.ui.internal.TimeZoneOffsetColumn;
import com.packtpub.e4.clock.ui.internal.TimeZoneSelectionListener;

public class TimeZoneTableView extends ViewPart {

	TableViewer tableViewer;
	private TimeZoneSelectionListener selectionListener;
	public TimeZoneTableView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		tableViewer = new TableViewer(parent,SWT.H_SCROLL|SWT.V_SCROLL);
		tableViewer.getTable().setHeaderVisible(true);
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
//		tableViewer.setInput(TimeZone.getAvailableIDs());
		String[] ids = TimeZone.getAvailableIDs();
		TimeZone[] timeZones = new TimeZone[ids.length];
		for (int i = 0; i < ids.length; i++) {
			timeZones[i] = TimeZone.getTimeZone(ids[i]);
		}
		new TimeZoneIDColumn().addColumnTo(tableViewer);
		new TimeZoneOffsetColumn().addColumnTo(tableViewer);
		new TimeZoneDispayColumn().addColumnTo(tableViewer);
		tableViewer.setInput(timeZones);
		getSite().setSelectionProvider(tableViewer);
		
		
		selectionListener = new TimeZoneSelectionListener(tableViewer,
				getSite().getPart());
		getSite().getWorkbenchWindow().getSelectionService()
				.addSelectionListener(selectionListener);
		
		hookContextMenu(tableViewer);
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
		tableViewer.getControl().setFocus();
	}

}
