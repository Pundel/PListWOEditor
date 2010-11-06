// Generated by the WOLips Templateengine Plug-in at Jul 10, 2010 1:13:25 PM
package net.rujel.setup;

import java.io.File;

import net.rujel.reusables.Various;

import com.webobjects.appserver.WOSession;

public class Session extends WOSession {
	private static final long serialVersionUID = 1L;

	public Session() {
		
	}
	
	public String message;
	public Boolean advanced = new Boolean(false);
	
	public File targetfolder = new File(Various.convertFilePath(System.getProperty("targetFolder", "CONFIGDIR/rujel")));
	
	public String folder() {
		return targetfolder.getAbsolutePath();
	}
	
	public void setFolder(String path) {
		targetfolder = new File(path);
	}
}