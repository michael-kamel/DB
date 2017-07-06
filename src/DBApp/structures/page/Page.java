package DBApp.structures.page;

public interface Page 
{
	public int getColumnNumber();
	public int getMaxTuples() ;
	public String[][] getData() ;
	public boolean[] getDeleted() ;
	public int getCurrent() ;
	public void setMaxTuples(int maxTuples) ;
	public boolean isFull();
	public boolean insert(String [] val);
	public boolean Delete(int rowNum);
	public boolean Delete(int columnNumber, String value);
	public int DeleteAll(int columnNumber, String value);
}
