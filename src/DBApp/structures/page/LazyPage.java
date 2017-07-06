package DBApp.structures.page;

import DBApp.structures.LazyLoadable;
import DBApp.structures.page.LazyPage;

public class LazyPage extends LazyLoadable<Page> implements Page
{
	private int columnNumber;
	private int maxTuples;
	
	public LazyPage(int noCol, int maxTuples)
	{
		init();
		this.columnNumber = noCol;
		this.maxTuples = maxTuples;
	}
	
	public String[][] getData() 
	{
		return getOriginal().getData();
	}
	public boolean[] getDeleted()
	{
		return getOriginal().getDeleted();
	}
	public int getCurrent() 
	{
		return getOriginal().getCurrent();
	}
	public int getColumnNumber() 
	{
		return getOriginal().getColumnNumber();
	}
	public int getMaxTuples() 
	{
		return getOriginal().getMaxTuples();
	}
	public boolean isFull()
	{
		return getOriginal().isFull();
	}
	public boolean insert(String [] val)
	{
		return getOriginal().insert(val);
	}
	public String[][] getData(int colNum)
	{
		return getOriginal().getData();
	}
	public void setMaxTuples(int maxTuples)
	{
		this.maxTuples = maxTuples;
	}
	public boolean Delete(int rowNum) 
	{
		return getOriginal().Delete(rowNum);
	}
	public boolean Delete(int columnNumber, String value) 
	{
		return getOriginal().Delete(columnNumber, value);
	}
	public int DeleteAll(int columnNumber, String value)
	{
		return getOriginal().DeleteAll(columnNumber, value);
	}
	
	protected RealPage load()
	{
		return new RealPage(columnNumber, maxTuples);
	}
	
}
