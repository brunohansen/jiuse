package br.com.bhansen.refactory;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.internal.corext.refactoring.structure.MoveInstanceMethodProcessor;
import org.eclipse.jdt.internal.ui.preferences.JavaPreferencesSettings;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.participants.MoveRefactoring;

import br.com.bhansen.metric.AbsMetric;

@SuppressWarnings("restriction")
public class MoveMethodRefactor {
	
	public static Change move(IType classFrom, String method, IType classTo) throws Exception {
		IMethod iMethod = getIMethod(classFrom, method);
		
		MoveInstanceMethodProcessor processor= new MoveInstanceMethodProcessor(iMethod, JavaPreferencesSettings.getCodeGenerationSettings(iMethod.getJavaProject()));
		Refactoring refactoring= new MoveRefactoring(processor);
		
		IProgressMonitor monitor = new NullProgressMonitor();
		refactoring.checkInitialConditions(monitor);
		
		processor.setMethodName(AbsMetric.getMoveMethodName(iMethod.getElementName()));
		processor.setInlineDelegator(true);
		processor.setRemoveDelegator(true);
		processor.setDeprecateDelegates(false);
		
		final IVariableBinding[] targets= processor.getCandidateTargets();
		IVariableBinding target = null;
		for (int index= 0; index < targets.length; index++) {
			if (targets[index].getType().getJavaElement().equals(classTo)) {
				target = targets[index];
				break;
			}
		}
		
		if(target == null)
			throw new Exception("Invalid target!");
		
		processor.setTarget(target);
		refactoring.checkFinalConditions(monitor);
		Change change = refactoring.createChange(monitor);
		Change undo = change.perform(monitor);
		
		return undo;
		
	}
	
//	public void move2(String method) throws Exception {
//	IMethod iMethod = getIMethod(method);
//	
//	RefactoringExecutionStarter.startMoveMethodRefactoring(iMethod, shell);
//	
//	this.newFromValue = new ValueClass(this.classFrom).getValue();
//	this.newToValue = new ValueClass(this.classTo).getValue();
//	
//	this.ValueDifference = (this.newFromValue - this.oldFromValue) + (this.newToValue - this.oldToValue);
//	
//}
	
	private static IMethod getIMethod(IType classFrom, String method) throws Exception {
		String m = AbsMetric.generateInnerSignature(method);

		IMethod[] methods = classFrom.getMethods();

		for (IMethod iMethod : methods) {
			if (AbsMetric.getSignature(iMethod).equals(m)) {
				return iMethod;
			}
		}
		
		m = AbsMetric.generateSignature(method);

		for (IMethod iMethod : methods) {
			if (AbsMetric.getSignature(iMethod).equals(m)) {
				return iMethod;
			}
		}

		throw new Exception("Method " + m + " not found!");
	}

}