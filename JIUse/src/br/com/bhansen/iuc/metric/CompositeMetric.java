package br.com.bhansen.iuc.metric;

import org.eclipse.jdt.core.IType;

public class CompositeMetric implements Metric {
	
	private CAMCJClass camc;
	private IUCClass iuc;
	
	public CompositeMetric(IType type) throws Exception {
		camc = new CAMCJClass(type, false);
		iuc = new IUCClass(type);
	}

	@Override
	public double getMetric() throws Exception {
		return (camc.getMetric() + iuc.getMetric()) / 2;
	}

	@Override
	public double getMetric(String fakeDelegate, String fakeParameter) throws Exception {
		return (camc.getMetric(fakeDelegate, fakeParameter) + iuc.getMetric(fakeDelegate, fakeParameter)) / 2;
	}

}
