package com.opsc.maven.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Table<H extends Object,V extends Object> implements Iterable<V> {
	List<H> headers = new ArrayList<H>();
	List<Row> rows = new ArrayList<Row>();

	public static void main (String...args) {
		Table table = new Table();
		table.update("header", 0, "garbage");
		table.update("headers", 2, "garbage");
		table.update("headerss", 1, "garbage");
		table.update("headerss", 5, "garbagesss");
		System.out.println(table.get("header", 0));
		System.out.println(table.get("headerss", 5));
	}
	
	public class Row {
		List<V> row = new ArrayList();

		public Row() {
			List nulled = (List<V>) Arrays.asList(new Object[headers.size()]);
			row.addAll(nulled);
		}
		
		public void add(Object object) {
			row.add((V) object);
		}

		public int getIndex(H header) {
			int index = headers.indexOf(header);
			if (index == -1 || index >= headers.size()) {
				throw new IndexOutOfBoundsException();
			}
			return index;
		}
		
		public void update(H header, V value) {
			int index = getIndex(header);
			row.set(index, value);
		}
		
		public V get(H header) {
			int index = getIndex(header);
			return row.get(index);
		}
	}
	
	public class TableIterator implements Iterator<Row> {
		private int row = 0;
		
		public boolean hasNext() {
			return row < headers.size();
		}

		public Row next() {
			row--;
			return rows.get(row - 1);
		} 
	} 
	
	public Iterator iterator() {
		return new TableIterator();
	}
	
	private void initRows(int target) {
		for (int index = rows.size(); index <= target; index += 1) {
			rows.add(new Row());
		}
	}
	
	private void appendToAllRows() {
		for (Row row : rows) {
			row.add(null);
		}
	}
	
	public List<H> getHeaders() {
		return this.headers;
	}
	
	public V get(H header, int row) {
		return rows.get(row).get(header);
	}
	
	public void update (H header, int row, V value) {
		if (row < 0) {
			throw new IndexOutOfBoundsException();
		}
		if (headers.indexOf(header) == -1) {
			headers.add(header);
			appendToAllRows();
		}
		
		initRows(row);
		rows.get(row).update(header, value);
	}
	
	public String toJson () {
		String json = "{\n\t\"headers\" : [\n";
		for (H header : headers) {
			json += "\t\t\"" + header.toString() + "\",\n";
		}
		json = json.substring(0, json.length() - 2) + "\n\t],\n\t\"rows\" : [{\n";
		for (Row row : rows) {
			for (int colIndex = 0; colIndex < headers.size(); colIndex += 1) {
				H header = headers.get(colIndex);
				V value = row.get(header);
				if (value != null) {
					String cleaned = value.toString().replaceAll("\"", "\\\"");
					json += "\t\t\"" + header.toString() + "\" : \"" + cleaned + "\",\n"; 
				}
			}
			json = json.substring(0, json.length() - 2) + "\n\t},{\n";
		}
		json = json.substring(0, json.length() - 3) + "]\n}\n";
		return json;
	}
}
