package com.packtpub.e4.clock.ui;

import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class ClockWidget extends Canvas {

	public ClockWidget(Composite parent, int style) {
		super(parent, style);
		addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				ClockWidget.this.paintControl(e);
			}
		});

		new Thread("TickTock") {
			public void run() {
				while (!ClockWidget.this.isDisposed()) {
					// clock.redraw();
					ClockWidget.this.getDisplay().asyncExec(new Runnable() {

						@Override
						public void run() {
							if (ClockWidget.this != null
									&& !ClockWidget.this.isDisposed())
								ClockWidget.this.redraw();
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

	public Point computeSize(int w, int h, boolean changed) {
		int size;
		if (w == SWT.DEFAULT) {
			size = h;
		} else if (h == SWT.DEFAULT) {
			size = w;
		} else {
			size = Math.min(w, h);
		}
		if (size == SWT.DEFAULT)
			size = 50;
		return new Point(size, size);
	}

	// Реализация для Листенера
	public void paintControl(PaintEvent e) {
		int seconds = (new Date()).getSeconds();
		int arc = (15 - seconds) * 6 % 360;
		Color blue = e.display.getSystemColor(SWT.COLOR_BLUE);
		e.gc.drawArc(e.x, e.y, e.width - 1, e.height - 1, 0, 360);
		e.gc.setBackground(blue);
		e.gc.fillArc(e.x, e.y, e.width - 1, e.height - 1, arc - 1, 2);
	}

}
