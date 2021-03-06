package br.com.bhansen.metric.iscomi;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;

import br.com.bhansen.jdt.Type;
import br.com.bhansen.metric.DeclarationMetricClass;

public class ISCOMiClass extends DeclarationMetricClass {
	
	public ISCOMiClass(Type type, String method, String parameter, IProgressMonitor monitor) throws Exception {
		super(type, method, parameter, monitor);
	}

	@Override
	public double getMetricValue() throws Exception {
		return iscomClass(getMethods());
	}
	
	public static double iscomClass(Map<String, Set<String>> methods) {
		double r = 0;
		
		if (methods.size() < 2)
			return 0;
		
		double numValues = uniqueValues(methods).size();

		for (Entry<String, Set<String>> method : methods.entrySet()) {
			Map<String, Set<String>> mtdsCopy = new HashMap<>(methods);
			mtdsCopy.remove(method.getKey());
			r = r + iscomMethod(method.getValue(), mtdsCopy, numValues);
		}

		return r / methods.size();
	}

	protected static double iscomMethod(Set<String> mi, Map<String, Set<String>> methods, double a) {
		double r = 0;
		
		if(methods.size() == 0)
			return 0;
				
		for (Entry<String, Set<String>> mj : methods.entrySet()) {
			r = r + (c(mi, mj.getValue()) * w(mi, mj.getValue(), a));
		}
		
		return r / (double) methods.size();
	}
	
	private static double c(Set<String> i, Set<String> j) {
		Set<String> intersection = new HashSet<>(i);
		intersection.retainAll(j);
		
		if(i.size() + j.size() == 0)
			return 0;
		
		return (2.0 * (double) intersection.size()) / ((double) i.size() + (double) j.size());
	}
	
	private static double w(Set<String> i, Set<String> j, double a) {
		if(a == 0)
			return 0;
		
		Set<String> union = new HashSet<>(i);
		union.addAll(j);
		
		return (double) union.size() / a;
	}

}
