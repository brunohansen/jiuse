package br.com.bhansen.metric.ic;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiFunction;

import org.eclipse.core.runtime.IProgressMonitor;

import br.com.bhansen.jdt.Type;
import br.com.bhansen.metric.AbsMetric;
import br.com.bhansen.metric.DeclarationMetricClass;
import br.com.bhansen.utils.Jaccard;

public class ICClass extends DeclarationMetricClass {
	
	public ICClass(Type type, IProgressMonitor monitor) throws Exception {
		super(type, monitor);
	}
	
	@Override
	final public double getMetric() throws Exception {
		return icClass(getMethods(), createWeight());
	}
	
	protected BiFunction<Set<String>, Set<String>, Double> createWeight() {
		return Jaccard.NO_WEIGHT;
	}
	
	public static double icClass(Map<String, Set<String>> methods, BiFunction<Set<String>, Set<String>, Double> weight) {
		double r = 0;
		
		if (methods.size() < 2)
			return 0;

		for (Entry<String, Set<String>> method : methods.entrySet()) {
			Map<String, Set<String>> mtdsCopy = new HashMap<>(methods);
			mtdsCopy.remove(method.getKey());
			
			r = r + icMethod(method.getValue(), mtdsCopy, weight);
		}

		return r / methods.size();
	}
	
	public static double icMethod(Set<String> method, Map<String, Set<String>> methods, BiFunction<Set<String>, Set<String>, Double> weight) {
		double mm = mm(method, methods, weight);
		
		if(mm == 0) {
			return 0;
		}
		
		double pp = pp(method, methods, weight);
		
		return ((mm + pp) / 2);
	}
	
	public static double mm(Map<String, Set<String>> methods, BiFunction<Set<String>, Set<String>, Double> weight) {
		double r = 0;
		
		if (methods.size() < 2)
			return 0;

		for (Entry<String, Set<String>> method : methods.entrySet()) {
			Map<String, Set<String>> mtdsCopy = new HashMap<>(methods);
			mtdsCopy.remove(method.getKey());
			
			r = r + mm(method.getValue(), mtdsCopy, weight);
		}

		return r / methods.size();
	}
	
	public static double mm(Set<String> method, Map<String, Set<String>> methods, BiFunction<Set<String>, Set<String>, Double> weight) {
		return Jaccard.similarity(method, methods.values(), weight);
	}
	
	public static double pp(Map<String, Set<String>> methods, BiFunction<Set<String>, Set<String>, Double> weight) {
		double r = 0;
		
		if (methods.size() < 2)
			return 0;

		for (Entry<String, Set<String>> method : methods.entrySet()) {
			Map<String, Set<String>> mtdsCopy = new HashMap<>(methods);
			mtdsCopy.remove(method.getKey());
			
			r = r + pp(method.getValue(), mtdsCopy, weight);
		}

		return r / methods.size();
	}

	public static double pp(Set<String> method, Map<String, Set<String>> methods, BiFunction<Set<String>, Set<String>, Double> weight) {
		
		if(method.size() == 0)
			return 0;
		
		double pp = 0;		
		
		Map<String, Set<String>> mP = AbsMetric.invert(methods);
		
		for (String k : method) {
			Map<String, Set<String>> mPC = new HashMap<>(mP);
			Set<String> p = mPC.remove(k);

			if (p != null) {
				pp += Jaccard.similarity(p, mPC.values(), weight);
			}

		}
		
		return pp / method.size();
	}

}
