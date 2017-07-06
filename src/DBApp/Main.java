package DBApp;

import java.util.Hashtable;

import DBApp.exceptions.DBException;

public class Main
{
	
	public void init( )
	{
	
	}

	/**
	* 	A method to create a table/relation.
	*	@param	strTableName: name of table to be created
	*	@param	htblColNameType: a hashtable of column names and column types , for example ("ID", "java.lang.Integer") where ID is key and the value
	*							 is java.lang.Integer
	*	@param	strKeyColName: the name of the key column, for example "ID"
	*/
	public void createTable(String strTableName, Hashtable<String,String> htblColNameType, String strKeyColName)  throws DBException
	{
	
	}
	
	/** 
	*	A method to insert a tuple into an existing table
	*	@param	strTableName: name of table to insert into
	*	@param	htblColNameValue: a hashtable of column names and values for each column, for example, ("ID", "50011") where ID is 
	*								the name of the column and 50011 is the value to be inserted.
	*/
	public void insertIntoTable(String strTableName, Hashtable<String,String> htblColNameValue)  throws DBException
	{
	
	}
	
	/** 
	*	A method to delete one or more tuple(s) from an existing table
	*	@param	strTableName: name of table to delete from
	*	@param	htblColNameValue: a hashtable of column names and values for each column, for example, ("ID", "50011") where ID is 
	*								the name of the column and 50011 is the value to be used for identifying row to be deleted.
	*	@param	strOperator: possible values are AND or OR to combine the keys in htblColNameValue
	*/
	public void deleteFromTable(String strTableName, Hashtable<String,String> htblColNameValue, String strOperator) throws DBException 
	{
	
	}
		
	public static void main( String[] strArgs )
	{
		
	}
}
