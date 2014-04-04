package com.packtpub.e4.clock.ui.handlers;

import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.commands.AbstractHandler;
import org.eclipse.ui.commands.ExecutionException;

public class HelloHandler extends AbstractHandler {

	public HelloHandler() {
		
	}

	@Override
	public Object execute(Map parameterValuesByName) throws ExecutionException {
		MessageDialog.openInformation(null, "Hello", "World");
		return null;
	}

}
