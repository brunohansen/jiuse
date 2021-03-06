package br.com.bhansen.handler.select;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.ui.IWorkbenchWindow;

import br.com.bhansen.config.Config;
import br.com.bhansen.config.MoveMethodConfig;
import br.com.bhansen.dialog.MessageDialog;
import br.com.bhansen.dialog.ProgressDialog;
import br.com.bhansen.jdt.Type;
import br.com.bhansen.refactory.EvaluatorFactory;
import br.com.bhansen.refactory.MoveMethodEvaluator;
import br.com.bhansen.view.MoveMethod;

public class SelectMove extends SelectionHandler {
	
	private static Type classFrom; 
	private static String method; 
	private static Type classTo;
	
	static {
		classFrom = null; 
		method = null; 
		classTo = null; 
	}

	@Override
	protected Object execute(IWorkbenchWindow window, ExecutionEvent event, MoveMethodConfig.Metric metric, Config.MetricContext context) throws Exception {
				
		if(classFrom == null) {
			
			try {
				classFrom = getType();
				method = getMethod().getSignature();
				
				MessageDialog.open("Method Selected!", method + "\n\n\n Open the 'To Class' and click on the select to move menu again!");
				
				return null;
			} catch (Exception e) {
				classFrom = null;
				throw e;
			}					

		}
		
		if(classFrom != null) {
			try {
				
				classTo = getType();
				
				MessageDialog.open("Class To Selected!", classTo.getName() + "\n\n\n The result dialog will open in a while!" );
				
				MoveMethodEvaluator evaluator = ProgressDialog.open(window, monitor -> EvaluatorFactory.create(classFrom, method, classTo, monitor));
				
				MoveMethod.show(window, classTo.getProject(), evaluator.toLineString());
				
				MessageDialog.open(evaluator.toString());
				
			} finally {
				classFrom = null;
			}
		}
		
		return null;
	}
	
}
