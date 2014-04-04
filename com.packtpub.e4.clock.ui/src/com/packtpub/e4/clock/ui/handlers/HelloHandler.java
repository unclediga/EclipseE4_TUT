package com.packtpub.e4.clock.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.dialogs.MessageDialog;

public class HelloHandler extends AbstractHandler {

	public HelloHandler() {
		
	}
	@Override
	public Object execute(ExecutionEvent event)
			throws org.eclipse.core.commands.ExecutionException {
		MessageDialog.openInformation(null, "Hello", "World");
		return null;
	}
}
