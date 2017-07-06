package DBApp.persistance;

import java.io.IOException;
import java.util.LinkedList;

import DBApp.structures.Table;
import DBApp.structures.Table.Meta;
import DBApp.structures.page.Page;
import DBApp.tools.CSVIO;

public class CSVDBWriter implements DBWriter
{
	private String rootDir;
	
	public CSVDBWriter(String rootDir) 
	{
		this.rootDir = rootDir;
	}
	@Override
	public void writePage(Page page, Table owner) 
	{
		try {
		LinkedList<String> ret = new LinkedList<String>();
		for(int i = 0; i < page.getData().length; i++)
		{
			String str = "";
			if(page.getDeleted()[i])
			{
				for(int x = 0; x < page.getColumnNumber() - 1; x++)
					str += DBApp.configuration.Paging.DELETION_TOKEN + ",";
				str += DBApp.configuration.Paging.DELETION_TOKEN;
			}
			else
			{
				for(int j = 0; j < page.getData()[i].length - 1; j++)
					str += page.getData()[i][j] + ",";
				str += page.getData()[i][page.getData()[i].length - 1];
			}
			ret.add(str);
		}
			
		CSVIO.writeCSV(rootDir + owner.getName() + "/", "page_" + owner.getNumPages(), ret);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	@Override
	public void writeTable(Table table) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeTableMeta(Meta meta) 
	{
		try {
			CSVIO.writeCSV(rootDir + meta.getTableName() + "/", "tablemeta", meta.getSerializable());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
}
