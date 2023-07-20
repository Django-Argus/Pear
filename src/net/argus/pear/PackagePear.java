package net.argus.pear;

import net.argus.beta.com.pack.PackageBuilder;

public class PackagePear {
	
	public static String getPingPackage() {
		return "PING";
	}
	
	public static String getParametersPackage(Object ... objs) {
		PackageBuilder builder = new PackageBuilder();
		if(objs.length % 2 != 0)
			return null;
		
		for(int i = 0; i < objs.length; i += 2) {
			if(!(objs[i] instanceof String))
				continue;
			
			String path = ".";
			String name = (String) objs[i];
			Object obj = objs[i+1];
			
			if(name.lastIndexOf(".") != -1) {
				path = name.substring(0, name.lastIndexOf("."));
				name = name.substring(name.lastIndexOf(".") + 1);
				
			}
			if(obj instanceof Integer)
				builder.addInt(path, name, Integer.valueOf(String.valueOf(obj)));
 			else if(obj instanceof Boolean)
 				builder.addBoolean(path, name, Boolean.valueOf(String.valueOf(obj)));
 			else if(obj == null)
 				builder.addNull(path, name);
 			else
 				builder.addString(path, name, String.valueOf(obj));
 				
				
		}
		
		return builder.getMainObject().toString();
		
	}
	
}
