package com.packtpub.e4.clock.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.SubToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

public class HelloHandler extends AbstractHandler {

	public HelloHandler() {

	}

	@Override
	public Object execute(ExecutionEvent event)
			throws org.eclipse.core.commands.ExecutionException {
		Job job = new Job("About to say Hello") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					SubMonitor subMonitor = SubMonitor.convert(monitor, "Preparing", 5000);
					for (int i = 0; i < 50 && !monitor.isCanceled(); i++) {
						if (i == 10) {
							subMonitor.subTask("Doing something");
						} else if (i == 12) {
							checkDozen(subMonitor.newChild(100));
							continue;
						} else if (i == 25) {
							subMonitor.subTask("Doing something else");
						} else if (i == 40) {
							subMonitor.subTask("Nearly there");
						}
						Thread.sleep(100);
						subMonitor.worked(100);
					}
				} catch (InterruptedException e) {

				} finally {
					monitor.done();
				}

				if (!monitor.isCanceled()) {

					// Exception !!! Invoke UI-function in noUI-thread
					// MessageDialog.openInformation(null, "Hello", "World");
					Display.getDefault().asyncExec(new Runnable() {

						@Override
						public void run() {
							MessageDialog.openInformation(null, "Hello",
									"World");

						}
					});
				}
				return Status.OK_STATUS;

			}


		};
		job.schedule();
		return null;
	}
	
	private void checkDozen(IProgressMonitor monitor) {
		if(monitor == null){
			monitor = new NullProgressMonitor();
		}
		try {
			monitor.beginTask("Check Dozen", 12);
			for (int i = 0; i < 12; i++) {
				Thread.sleep(100);
				monitor.worked(1);
			}
		} catch (InterruptedException e) {
		}finally{
			monitor.done();
		}
	}
}
