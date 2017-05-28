package ryanair.flights.domain.model;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import javax.validation.ValidationException;

public class Route implements Serializable {

	private static final long serialVersionUID = -2225527417833739091L;
	private String from;
	private String to;

	public Route(String from, String to) {
		this.from = from;
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}
	
    private static final String keyOf(String from, String to){
        final StringBuilder sb = new StringBuilder("(");
        sb.append(from);
        sb.append("->").append(to);
        sb.append(')');
        return sb.toString();
    }
    
	private static final ConcurrentHashMap<String,Route> ROUTES = new ConcurrentHashMap<>();
	
    public static Route of(String from, String to){
        if(from==null || to==null){
            throw new ValidationException("Route from and to airports MUST NOT be null");
        }
        if(from.equals(to)){
            throw new ValidationException("Route from and to airports MUST be different");
        }
        return ROUTES.computeIfAbsent(keyOf(from,to), k -> new Route(from, to));
    }	
}
