package com.packtpub.e4.clock.ui.internal;

import java.util.TimeZone;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class TimeZoneViewerFilter extends ViewerFilter {
	private String pattern;

	//private String pattern;
	public TimeZoneViewerFilter(String pattern) {
		this.pattern = pattern;
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if(element instanceof TimeZone){
			TimeZone tz = (TimeZone) element;
//			if (tz.getDisplayName().contains(pattern)){
//				System.out.println(tz.getDisplayName()+ " contain "+pattern);
//				return true;
//			}else{
//				System.out.println(tz.getDisplayName()+ " NOT contain "+pattern);
//				return false;
//			}
			return tz.getDisplayName().contains(pattern);
		}else
			return true;
	}
	

}
