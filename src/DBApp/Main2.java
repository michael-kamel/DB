package DBApp;
import DBApp.persistance.CSVDBWriter;
import DBApp.structures.Table;

public class Main2
{
	public static void main(String []args)
	{
		try
		{
			String tName="Student";
			String [] tColNames={"ID","Name","GPA","Age","Year"};
			String [] tColTypes={"int","String","double","int","int"};
			
			Table t = new Table(tName,tColNames,tColTypes, new CSVDBWriter(DBApp.configuration.Paging.PAGE_DIR));
			
			for(int i=0;i<300;i++)
			{
				String []st={""+i,"Name"+i,"0."+i,"20","3"};
				t.insert(st);
			}
			for(int i=0;i<300;i++)
			{
				String []st={""+(i+300),"Name"+(i+300),"0."+(i+300),"21","4"};
				t.insert(st);
			}
				
			for(int i=0;i<300;i++)
			{
				String []st={""+(i+600),"Name"+(i+600),"0."+(i+600),"21","3"};
				t.insert(st);
			}
			System.out.println(t);
		}
		catch(Exception exc)
		{
			System.out.println(exc.toString());
			exc.printStackTrace();
		}
	}
}
