package DBApp.persistance;

import DBApp.structures.Table;
import DBApp.structures.page.Page;

public interface DBWriter 
{
	void writePage(Page page, Table owner);
	void writeTable(Table table);
	void writeTableMeta(Table.Meta meta);
}
