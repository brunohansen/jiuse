package br.com.bhansen.dialog;

import org.eclipse.ui.PlatformUI;

public class MessageDialog {
	
	public static void open(String message) {
		org.eclipse.jface.dialogs.MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "iMove", message);
	}
	
	public static void open(String title, String message) {
		org.eclipse.jface.dialogs.MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), title, message);
	}

}
