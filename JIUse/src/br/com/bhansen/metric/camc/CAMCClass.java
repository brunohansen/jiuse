package br.com.bhansen.metric.camc;

import java.util.Set;

import br.com.bhansen.metric.DeclarationMetricClass;
import br.com.bhansen.utils.Type;

public class CAMCClass extends DeclarationMetricClass {
		
	public CAMCClass(Type type, String method, String parameter) throws Exception {
		super(type, method, parameter);
	}

	@Override
	public double getMetric() throws Exception {
		double camc = 0;
		double numParams = getParams().size();
		double numMethods = getMethods().size();
		
		if((numMethods == 0) || (numParams == 0)) {
			return 0.0f;
		}
		
		for (Set<String> ps : getMethods().values()) {
			camc += ps.size() / numParams;
		}
		
		camc = camc / numMethods;
		
		return camc;
	}

}
