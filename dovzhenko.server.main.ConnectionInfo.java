package dovzhenko.server.main;

import java.util.List;

public class TableBilder {
	private StringBuilder html = new StringBuilder();
	  
	public TableBilder addRowToTable(List<String> rowElements) {
	        html.append("<tr>");
	        rowElements.forEach((re) -> html.append("<td>").append(re).append("</td>"));
	        html.append("</tr>");
	        return this;
	}
	
	public void clean(){
		html.delete(0, html.length()-1);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return html.toString();
	}
}
