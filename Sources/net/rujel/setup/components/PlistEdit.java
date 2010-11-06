package net.rujel.setup.components;

import java.util.Enumeration;

import net.rujel.reusables.Various;

import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOComponent;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableDictionary;

public class PlistEdit extends WOComponent {
    public PlistEdit(WOContext context) {
        super(context);
        
    }
    
    public NSDictionary item;
    public NSDictionary item2;
    public NSArray<NSDictionary> array;
    public NSDictionary plist;
    
    public String getTitle() {
		return  (String)item.valueForKey("title");
	}
	public String getDescription() {
		return  (String)item.valueForKey("description");
	}
	
	public boolean isDictionary() {
		return "Dictionary".equals((String)item.valueForKey("type"));
	}
	public boolean isString() {
		return "String".equals((String)item.valueForKey("type")) && ! isFinal();
	}
	public boolean isBoolean() {
		return "Boolean".equals((String)item.valueForKey("type"));
	}
	public boolean isNumber() {
		return "Integer".equals((String)item.valueForKey("type"));
	}
	public boolean isSelect() {
		return item.valueForKey("select") != null;
	}
	public boolean isRequired() {
		return Various.boolForObject(item.valueForKey("required"));
	}
	public boolean isFinal() {
		return Various.boolForObject(item.valueForKey("final"));
	}
	public boolean isAdvanced() {
		Boolean isAdvanced = (Boolean) session().valueForKey("advanced");
		Boolean advancedValue = Various.boolForObject(item.valueForKey("advanced"));
		return !advancedValue || isAdvanced;
	}
	
	public String color() {
		if (Various.boolForObject( item.valueForKey("advanced"))) {
			return "FFE9BF";
		}
		return "e6e6e6";
	}
	public NSMutableDictionary nextplist() {
		return (NSMutableDictionary) plist.valueForKey((String) item.valueForKey("key"));
	}
	public NSArray<NSDictionary> nextarray() {
		return (NSArray<NSDictionary>) item.valueForKey("keys");
	}
	public NSArray<NSDictionary> getSelect() {
		return (NSArray<NSDictionary>)item.valueForKey("select");
	}
	public Object getDefaultValue() {
		return item.valueForKey("defaultValue");
	}
	
	public String getStrValue() {
		Object result = plist.valueForKey((String)item.valueForKey("key"));
		if (result == null)
			return null;
		return result.toString();
	}
	public Object getNumValue() {
		Object value = plist.valueForKey((String)item.valueForKey("key"));
		if (item.valueForKey("exeption") == null) {
			if(value == null || value instanceof Number)
				return (Number)value;
			return new Integer(value.toString());
		}
		return value; 
	}
	public Boolean getBoolValue() {
		Object value = plist.valueForKey((String)item.valueForKey("key"));
		return Various.boolForObject(value);
	}
	public NSDictionary getSelValue() {
		NSDictionary dict = null;
		Object val = plist.valueForKey((String)item.valueForKey("key")); //val - real value of this key
		Object def = item.valueForKey("defaultValue");
		NSArray<NSDictionary> arr = (NSArray<NSDictionary>) item.valueForKey("select");
		Enumeration en = arr.objectEnumerator();
		while (en.hasMoreElements()) {
			NSDictionary metaElement = (NSDictionary) en.nextElement();
			Object value = metaElement.valueForKey("value"); //value - check this value
			if ((value != null)? value.equals((val != null)?val:def) : val == null) {
				dict = metaElement;
				break;
			}
			
		}
		return dict;
	}
	
	public Object getFinValue() {
		return plist.valueForKey((String)item.valueForKey("key"));
	}

	public void setStrValue(String it) {
		session().takeValueForKey(null, "message");
		item.takeValueForKey(null, "exeption");
		plist.takeValueForKey( it, (String)item.valueForKey("key"));
	}
	public void setBoolValue(Boolean it) {
		plist.takeValueForKey(it, (String)item.valueForKey("key"));
	}
	public void setNumValue(String it) {
		session().takeValueForKey(null, "message");
		item.takeValueForKey(null, "exeption");
		String key = (String)item.valueForKey("key");
		try {
			plist.takeValueForKey((it == null)?null:new Integer(it), key);
		}
		catch (Exception e) {
			session().takeValueForKey("Error", "message");
			plist.takeValueForKey(it, key);
		}
	}
	public void setSelValue(NSDictionary it) {
		Object value = it.valueForKey("value");
		plist.takeValueForKey(value, (String)item.valueForKey("key"));
	}
}