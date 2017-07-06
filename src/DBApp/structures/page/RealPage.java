package DBApp.structures.page;

public class RealPage implements Page
{
	private static final Integer DEFAULT_CURRENT = -1;
	private String [][] data;
	private boolean [] deleted;
	private int current = DEFAULT_CURRENT;
	private int columnNumber;
	private int maxTuples;
	
	public RealPage(int columnNumber, int maxTuples) 
	{
		this.columnNumber = columnNumber;
		this.maxTuples = maxTuples;
		this.data = new String[maxTuples][columnNumber];
		this.deleted = new boolean[maxTuples];
		for(int i = 0; i < maxTuples; i++)
			deleted[i] = false;
	}
	
	public int getColumnNumber()
	{
		return columnNumber;
	}
	public int getMaxTuples() 
	{
		return maxTuples;
	}
	public String[][] getData() 
	{
		return data;
	}
	public boolean[] getDeleted() 
	{
		return deleted;
	}
	public int getCurrent() 
	{
		return current;
	}

	public void setMaxTuples(int maxTuples) 
	{
		this.maxTuples = maxTuples;
	}
	//Function1: A function that checks if the page is full
	public boolean isFull()
	{
		return(current >= DBApp.configuration.Paging.PAGE_SIZE - 1);
	}
	
	//Function2: Inserting a record into the page
	public boolean insert(String [] val)
	{
		if(isFull())
			return false;
		
		data[++current] = val;
		return true;
	}
	public boolean Delete(int rowNum)
	{
		if(rowNum > current)
			return false;
		deleted[rowNum] = true;
		return true;
	}
	public boolean Delete(int columnNumber, String value)
	{
		for(int i = 0; i <= current; i++)
		{
			if(data[i][columnNumber].equals(value))
			{
				deleted[i] = true;
				return true;
			}
		}
		return false;
	}
	public int DeleteAll(int columnNumber, String value)
	{
		int count = 0;
		for(int i = 0; i <= current; i++)
		{
			if(data[i][columnNumber].equals(value))
			{
				deleted[i] = true;
				count++;
			}
		}
		return count;
	}
	//Function3: Inserting a set of records into a page - It will use Function2-
	public LazyPage getData(int [] colNum)
	{
		throw new UnsupportedOperationException();
	}
}