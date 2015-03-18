import java.io.IOException;
import java.util.ArrayList;

import javax.xml.transform.TransformerException;

import net.ramso.doc.svg.er.ERDiagram;
import net.ramso.doc.svg.er.TableData;

/**
 * 
 */
/**
 * @author ramso
 */
public class TestER {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayList<TableData> objs = new ArrayList<TableData>();
		TableData table = new TableData();
		table.setName("Tabla 1" );
		for(int j = 0; j < 3; j++){
			table.addPrimaryKey("PK: Clave" + j);
		}
		table.addRelation(TableData.ONETOMANY, "Tabla 2");
		table.addRelation(TableData.ONETOMANY, "Tabla 3");
		objs.add(table);
		table = new TableData();
		table.setName("Tabla 2" );
		for(int j = 0; j < 5; j++){
			table.addPrimaryKey("PK: Clave" + j);
		}
		table.addRelation(TableData.ONETOMANY, "Tabla 3");
		objs.add(table);
		table = new TableData();
		table.setName("Tabla 3" );
		for(int j = 0; j < 3; j++){
			table.addPrimaryKey("PK: Clave" + j);
		}
		objs.add(table);
		ERDiagram diagram = new  ERDiagram(objs);
		diagram.run();
		try {
			diagram.save("/home/ramso/temp_files/");
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
	}
}
