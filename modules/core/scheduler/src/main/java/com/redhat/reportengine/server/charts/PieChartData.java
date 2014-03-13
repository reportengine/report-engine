/**
 * 
 */
package com.redhat.reportengine.server.charts;

import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.jfree.chart.labels.PieSectionLabelGenerator;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Feb 27, 2014
 */
public class PieChartData {
	private LinkedList<String> keys = new LinkedList<String>();
	private LinkedHashMap<String, Color> color =  new LinkedHashMap<String, Color>();
	private LinkedHashMap<String, Object> data = new LinkedHashMap<String, Object>();
	private PieSectionLabelGenerator labelGenerator = null;
	private String title=null;
	
	public void addData(String name, Object value, Color color){
		if(!this.keys.contains(name)){
			this.keys.add(name);
		}
		this.color.put(name, color);
		this.data.put(name, value);		
	}
	
	public LinkedList<String> getKeys(){
		return this.keys;
	}
	public Color getColor(String name){
		return this.color.get(name);
	}
	public Object getValue(String name){
		return this.data.get(name);
	}

	public PieSectionLabelGenerator getLabelGenerator() {
		return labelGenerator;
	}

	public void setLabelGenerator(PieSectionLabelGenerator labelGenerator) {
		this.labelGenerator = labelGenerator;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
