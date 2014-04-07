package com.packtpub.e4.clock.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.progress.IProgressConstants2;
import org.eclipse.ui.statushandlers.StatusManager;

import com.packtpub.e4.clock.ui.Activator;

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
					subMonitor = null; // The bug
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
				} catch (NullPointerException e) {
					/* VARIANT #1
					return new Status(IStatus.ERROR,
							Activator.PLUGIN_ID, "Programming bug?", e);
					*/
					/* Variant #2 */
					StatusManager statusManager = StatusManager.getManager();
					Status status = new Status(IStatus.ERROR,
							Activator.PLUGIN_ID, "Programming bug?", e);
					//statusManager.handle(status, StatusManager.LOG); //visible in log only
					statusManager.handle(status, StatusManager.LOG|StatusManager.SHOW); //in log and you will see dialog win
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
		
		
		ICommandService service = (ICommandService) PlatformUI.getWorkbench()
				.getService(ICommandService.class);
		Command command = service == null ? null : service
				.getCommand("com.packtpub.e4.clock.ui.command.hello");
		if (command != null) {
			job.setProperty(IProgressConstants2.COMMAND_PROPERTY,
					ParameterizedCommand.generateCommand(command, null));
		}

		job.setProperty(IProgressConstants2.ICON_PROPERTY, ImageDescriptor
				.createFromURL(HelloHandler.class
						.getResource("/icons/sample.gif")));

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
