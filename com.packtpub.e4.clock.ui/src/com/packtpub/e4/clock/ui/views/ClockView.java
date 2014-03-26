package com.packtpub.e4.clock.ui.views;

import java.util.Date;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;

/**
 * This sample class demonstrates how to plug-in a new workbench view. The view
 * shows data obtained from the model. The sample creates a dummy model on the
 * fly, but a real implementation would connect to the model available either in
 * this or another plug-in (e.g. the workspace). The view is connected to the
 * model using a content provider.
 * <p>
 * The view uses a label provider to define how model objects should be
 * presented in the view. Each view can present the same model objects using
 * different labels and icons, if needed. Alternatively, a single label provider
 * can be shared between views in order to ensure that objects of the same type
 * are presented in the same way everywhere.
 * <p>
 */

public class ClockView extends ViewPart {

	/*
	 * The content provider class is responsible for providing objects to the
	 * view. It can wrap existing objects in adapters or simply return objects
	 * as-is. These objects may be sensitive to the current input of the view,
	 * or ignore it and always show the same content (like Task List, for
	 * example).
	 */

	/**
	 * The constructor.
	 */
	public ClockView() {
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		
		final Canvas clock = new Canvas(parent, SWT.NONE);
		///////////////////////////////
		clock.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				int seconds = (new Date()).getSeconds();
				int arc = (15 - seconds) * 6 % 360;
				Color blue = e.display.getSystemColor(SWT.COLOR_BLUE);
				e.gc.setBackground(blue);
				e.gc.drawArc(e.x, e.y, e.width - 1, e.height - 1, arc - 1, 2);
			}
		});
		//////////////////////////////////////////
		new Thread("TickTock") {
			public void run() {
				while (!clock.isDisposed()) {
					System.out.println("Try");
					//clock.redraw();
					clock.getDisplay().asyncExec(new Runnable() {
						
						@Override
						public void run() {
							if (clock != null && !clock.isDisposed())
								clock.redraw();
						}
					});
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						System.out.println("Ex return");
						return;
					}
				}
			}
		}.start();

	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		// viewer.getControl().setFocus();
	}
}