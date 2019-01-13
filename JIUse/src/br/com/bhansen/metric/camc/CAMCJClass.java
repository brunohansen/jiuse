package br.com.bhansen.metric.camc;

import java.util.Map.Entry;
import java.util.Set;

import br.com.bhansen.metric.AbsMetric;
import br.com.bhansen.metric.DeclarationMetricClass;
import br.com.bhansen.utils.Type;

public class CAMCJClass extends DeclarationMetricClass {

	public CAMCJClass(Type type, String method, String parameter) throws Exception {
		super(type, method, parameter);
	}
		
	@Override
	@SuppressWarnings("unchecked")
	public double getMetric() throws Exception {
		
		if(getMethods().size() < 2)
			return 0;
		
		double metric = 0;
		
		Entry<String, Set<String>> [] mxs = getMethods().entrySet().toArray(new Entry [1]);
		
		for (int x = 0; x < mxs.length - 1; x++) {
			Entry<String, Set<String>> mx = mxs[x];
						
			Entry<String, Set<String>> [] mys = getMethods().entrySet().toArray(new Entry [1]);
			
			for (int y = x + 1; y < mys.length; y++) {
				Entry<String, Set<String>> my = mys[y];
				
				metric += AbsMetric.howMuchIntersect(mx.getValue(), my.getValue());
								
			}
		}
				
		metric = metric / comb(getMethods().size());

		return metric;
	}
	
}
