package application;

import java.util.ArrayList;

public class LinearRegression {
	private ArrayList<Double> IDList;
	private ArrayList<Long> MTList;
	long a;    // y = ax + b
	long b;
	
	public LinearRegression() {
		IDList = new ArrayList<Double>();
		MTList = new ArrayList<Long>();
	}
	
	public void addPoint(double ID, long MT) {
		IDList.add(ID);
		MTList.add(MT);
	}
	
	public void calculateParameters() {
		//mean x
		long sumx = 0;
		for(int i = 0; i < IDList.size(); i++) {
			sumx += IDList.get(i).longValue();
			//System.out.println("x: " + IDList.get(i));
		}
		long mean_x = sumx/IDList.size();
		//mean y
		long sumy = 0;
		for(int i = 0; i < MTList.size(); i++) {
			sumy += MTList.get(i);
		}
		long mean_y = sumy/MTList.size();
		
		long numeratorSum = 0;
		for(int i = 0; i < IDList.size(); i ++) {
			numeratorSum += (IDList.get(i) - mean_x) * (MTList.get(i) - mean_y);
		}
		long denominatorSum = 0;
		for(int i = 0; i < IDList.size(); i++) {
			denominatorSum += (IDList.get(i) - mean_x) * (IDList.get(i) - mean_x);
		}
		if(denominatorSum != 0) {
			a = numeratorSum/denominatorSum;
		}
		else {
			a = Long.MAX_VALUE;
		}
		b = mean_y - a * (long)mean_x;
	}

	public long getA() {
		return a;
	}

	public long getB() {
		return b;
	}
	
	public double getFirstPointX() {
		int index = 0;
		for (int i = 0; i < IDList.size(); i++) {
			if (IDList.get(i) < IDList.get(index)) {
				index = i;
			}
		}
		return IDList.get(index);
	}
	
	public long getFirstPointY() {
		int index = 0;
		for (int i = 0; i < IDList.size(); i++) {
			if (IDList.get(i) < IDList.get(index)) {
				index = i;
			}
		}
		return (a * IDList.get(index).longValue() + b);
	}
	
	public double getLastPointX() {
		int index = 0;
		for (int i = 0; i < IDList.size(); i++) {
			if (IDList.get(i) > IDList.get(index)) {
				index = i;
			}
		}
		return IDList.get(index);
	}
	
	public long getLastPointY() {
		int index = 0;
		for (int i = 0; i < IDList.size(); i++) {
			if (IDList.get(i) > IDList.get(index)) {
				index = i;
			}
		}
		return (a * IDList.get(index).longValue() + b);
	}
	public int getSize() {
		return IDList.size();
	}
	
}
