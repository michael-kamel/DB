package DBApp.structures;

//import java.io.File;
//import java.io.FileOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.stream.Collectors;

import DBApp.exceptions.DBException;
import DBApp.persistance.DBWriter;
import DBApp.structures.page.LazyPage;
import DBApp.structures.page.Page;
import DBApp.tools.Reflection;

public class Table implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private int numPages;
	private String name;
	private transient Page last;
	private transient boolean open;
	private transient Meta metaData;
	private transient DBWriter writer;
	
	//First Step: Constructing a table - You should initialize the variables given above -
	public Table(String name, String [] colNames,String [] colTypes, DBWriter writer) throws ClassNotFoundException, DBException, FileNotFoundException, IOException
	{
		this.name = name;
		if(!new File(DBApp.configuration.Paging.PAGE_DIR + name).mkdirs())
			throw new DBException("Table already exists");
		this.metaData = new Meta(name, colNames, colTypes);
		addPage(new LazyPage(colNames.length, DBApp.configuration.Paging.PAGE_SIZE));
		open = true;
		this.writer = writer;
		saveMeta();
	}
	
	public Meta getMetaData()
	{
		return metaData;
	}
	public int getNumPages() 
	{
		return numPages;
	}
	public String getName() 
	{
		return name;
	}
	public Page getLast()
	{
		return last;
	}
	public boolean isOpen() 
	{
		return open;
	}
	
	public int deleteAllEntries(String columnName, String val) throws DBException, FileNotFoundException, IOException
	{
		int count = 0;
		//check val by validation
		boolean exists = false;
		int i = 0;
		for(; i < metaData.entries.size(); i++)
		{
			if(metaData.entries.get(i).name == columnName)
			{
				exists = true;
				break;
			}
		}
		if(!exists)
			throw new DBException("Column does not exist");
		count += last.DeleteAll(i,  val);
		for(int x = 1; x < numPages - 1; x++)
		{
			LazyPage page = new LazyPage(last.getColumnNumber(), last.getMaxTuples());
			count += page.DeleteAll(i, val);
			writer.writePage(page, this);
		}
		return count;
	}
	public boolean deleteEntry(String columnName, String val) throws DBException, FileNotFoundException, IOException
	{
		boolean found = false;
		//check val by validation
		boolean exists = false;
		int i = 0;
		for(; i < metaData.entries.size(); i++)
		{
			if(metaData.entries.get(i).name == columnName)
			{
				exists = true;
				break;
			}
		}
		if(!exists)
			throw new DBException("Column does not exist");
		
		if(!last.Delete(i,  val))
		{
			for(int x = 1; x < numPages - 1; x++)
			{
				LazyPage page = new LazyPage(last.getColumnNumber(), last.getMaxTuples());
				if(page.Delete(i, val))
				{
					found = true;
					writer.writePage(page, this);
					break;
				}
			}
		}
		return found;
	}
	private void saveMeta() throws FileNotFoundException, IOException
	{
		writer.writeTableMeta(metaData);
	}

	//Function1: A function that creates a "Tables" folder in which the pages of a table will be created 
	//and adds a page into that folder
	public void addPage(LazyPage p) throws IOException
	{
		open = true;
		numPages++;
		//File file = new File(DBApp.configuration.Paging.PAGE_DIR + name + "/page_" + ++numPages);
		//file.getParentFile().mkdirs(); 
		//file.createNewFile();
		last = p;
	}
	
	//Function2: A function that inserts a record of strings into the last page of the table if it is not full,
	//otherwise, it should add a new page into the folder and insert the record into it
	public void insert(String[] record) throws ClassNotFoundException, IOException, DBException
	{        
		//if(!validateRecord(record))
			//throw new DBException("Invalid record format");
		open = true;
		if(!last.insert(record))
		{
			writer.writePage(last, this);
			addPage(new LazyPage(last.getColumnNumber(), last.getMaxTuples()));
			last.insert(record);
		}
	}
		
	@SuppressWarnings("unused")
	private boolean validateRecord(String[] record)
	{
		throw new UnsupportedOperationException();
	}

	public static class Meta
	{
		private String tableName;
		private ArrayList<Entry> entries;
		
		private Meta(String name)
		{
			this.tableName = name;
		}
		public Meta(String name, String[] colNames, String[] colTypes) throws ClassNotFoundException, DBException 
		{
			this(name);
			this.entries = new ArrayList<Table.Meta.Entry>();
			addEntries(colNames, colTypes, null, null);
		}
		
		public String getTableName() 
		{
			return tableName;
		}
		public ArrayList<Entry> getEntries() 
		{
			return entries;
		}
		
		private void addEntry(Entry entry)
		{
			this.entries.add(entry);
		}
		@SuppressWarnings("unused")
		private void addEntries(Entry[] entries)
		{
			for(Entry entry : entries)
				addEntry(entry);
		}
		private void addEntries(String[] names, String[] types, boolean[] keys, boolean[] indexed) throws ClassNotFoundException, DBException
		{
			if(names.length != types.length)
				throw new DBException("Invalid table meta structure");
			for(int i = 0; i < names.length; i++)
				addEntry(new Entry(names[i], Class.forName(Reflection.simpleNameToFullName(types[i])), (keys == null ? false : keys[i]), (indexed == null ? false : keys[i])));
		}
		@SuppressWarnings("unused")
		private void addEntries(LinkedList<String[]> entries)
		{
			entries.forEach(this::addEntry);
		}
		private void addEntry(String[] entry)
		{
			try {
				Entry newEntry;
				newEntry = new Entry(entry[1], entry[2], entry[3], entry[4]);
				entries.add(newEntry);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
		public LinkedList<String> getSerializable()
		{	
			return entries.stream().map(Entry::toString).map(StringBuilder::new).map(sb -> sb.insert(0, tableName + ",")).map(StringBuilder::toString).collect(Collectors.toCollection(LinkedList::new));
		}
		private static class Entry
		{
			public final String name;
			public final Class<?> type;
			public final boolean isKey;
			public boolean isIndexed;
			
			private Entry(String name, Class<?> type, boolean isKey, boolean isIndexed)
			{
				this.name = name;
				this.type = type;
				this.isKey = isKey;
				this.isIndexed = isIndexed;
			}
			private Entry(String name, String type, String isKey, String isIndexed) throws ClassNotFoundException
			{
				this.name = name;
				this.type = Class.forName(type);
				this.isKey = Boolean.parseBoolean(isKey);
				this.isIndexed = Boolean.parseBoolean(isIndexed);
			}
			
			public String toString()
			{
				return name + "," + type.getName() + "," + isKey + "," + isIndexed;
			}
		}
	}
}