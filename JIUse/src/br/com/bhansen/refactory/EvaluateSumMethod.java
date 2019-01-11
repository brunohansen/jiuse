package br.com.bhansen.refactory;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IType;
import org.eclipse.ltk.core.refactoring.Change;

import br.com.bhansen.metric.Metric;
import br.com.bhansen.metric.MetricFactory;
import br.com.bhansen.utils.MethodHelper;
import br.com.bhansen.utils.TypeHelper;

public class EvaluateSumMethod extends MoveMethodEvaluator  {

	private double oldValue;
	private double newValue;
	private boolean skipIUC;
	
	public EvaluateSumMethod(IType classFrom, String method, IType classTo, MetricFactory factory, double threshold) throws Exception {
		super(classFrom, method, classTo, factory, threshold);
		
		Metric m = factory.create(classFrom, method);
		
//		if((! m.isPublicMethod()) || (m.isCalledOnlyBy(classTo)) || (m.hasNoCaller()) || (m.isCalledOnlyBy(classFrom))) {
//			this.skipIUC = true;
//		} else {
			this.skipIUC = false;
//		}
		
		this.oldValue = m.getMetric(this.skipIUC);
		
		this.move();
	}
	
	private void move() throws Exception {
		MoveMethodRefactor refactor = new MoveMethodRefactor();
		
		Change undo = refactor.move(this.classFrom, this.iMethod, this.classTo);
		
		try {
			this.newValue = factory.create(this.classTo, MethodHelper.getMoveMethodName(MethodHelper.getMethodName(this.mSig)), refactor.getTypeNotUsed()).getMetric(this.skipIUC);
			
			this.valueDifference = (this.newValue - this.oldValue);
			
//			if(this.skipIUC)
//				this.valueDifference += 0.1;
		} finally {
			undo.perform(new NullProgressMonitor());
		}
		
	}

	@Override
	public String toString() {
		StringBuilder txt = new StringBuilder();

		txt.append(TypeHelper.getClassName(this.classFrom)).append(" ").append(this.oldValue).append("\n");
		txt.append(TypeHelper.getClassName(this.classTo)).append(" ").append(this.newValue).append("\n");
		txt.append("Skip IUC: ").append(this.skipIUC).append("\n");
		txt.append("Value difference: ").append(this.valueDifference).append("\n\n");

		return txt.toString();
	}

}
