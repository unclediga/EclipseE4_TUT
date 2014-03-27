package com.packtpub.e4.clock.ui.views;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;
import org.eclipse.swt.SWT;

import com.packtpub.e4.clock.ui.ClockWidget;

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

// Тестовый коммент для теста GIT

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

		// получим число выделенных объектов
		// В обычном режиме с=0, а вот запустившись в debug, включив трассировку
		// на закладке Trace с флажками debug и trace/graphics
		// для плагина org.eclipse.ui, получим увеличивающиеся цифру,
		// увеличивающуюся на 3 после закрытия-открытия нашего View,
		// если не привязать DisposeListener в ClockWidget().
		/*
		 * There are 57 Color instances Application Started: 10281 There are 69
		 * Color instances There are 72 Color instances There are 75 Color
		 * instances
		 */
		/* PacktPub.Eclipse.4.Plug-in.Development.by.Example.Beginner's.Guide.2013.pdf
		 * 
		 * When SWT is running in trace mode, it will keep a list of previously
		 * allocated resources in a global list, which is accessible through the
		 * DeviceDataobject. When the resource is disposed, it will be removed
		 * from the allocated list. This allows the monitoring of the state of
		 * resources at play in the Eclipse workbench and discover leaks,
		 * typically through repeated actions and noting an increase each time
		 * in the resource count.
		 */

		Object[] oo = parent.getDisplay().getDeviceData().objects;
		int c = 0;
		for (Object o : oo) {
			if (o instanceof Color) {
				c++;
			}
		}

		System.err.println("There are " + c + " Color instances");

		RowLayout layout = new RowLayout(SWT.HORIZONTAL);
		parent.setLayout(layout);

		final ClockWidget clock1 = new ClockWidget(parent, SWT.NONE, new RGB(
				255, 0, 0));
		final ClockWidget clock2 = new ClockWidget(parent, SWT.NONE, new RGB(0,
				255, 0));
		final ClockWidget clock3 = new ClockWidget(parent, SWT.NONE, new RGB(0,
				0, 255));

		clock1.setLayoutData(new RowData(100, 100));
		clock3.setLayoutData(new RowData(200, 200));
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		// viewer.getControl().setFocus();
	}
}