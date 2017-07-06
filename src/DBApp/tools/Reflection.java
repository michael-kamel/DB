package DBApp.tools;

public class Reflection 
{
	public static String simpleNameToFullName(String name)
	{
		String ret = "java.lang.";
		switch (name) 
		{
			case "Integer" :
			case "int" : return (ret += "Integer");
			case "string" :
			case "String" : return (ret += "String");
			case "Byte" :
			case "byte" : return (ret += "Byte");
			case "Boolean" :
			case "boolean" : return (ret += "Boolean");
			case "char" :
			case "Character" : return (ret += "Character");
			case "Double" :
			case "double": return (ret += "Double");
			case "Float" :
			case "float" : return (ret += "Float");
			case "Long" :
			case "long" : return (ret += "Long");
			case "short" :
			case "Short" : return (ret += "Short");
			default: return name;
		}
		
	}
}
