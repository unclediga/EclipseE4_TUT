package com.packtpub.e4.clock.ui.internal;

import java.util.TimeZone;

public class TimeZoneOffsetColumn extends TimeZoneColumn {

	public TimeZoneOffsetColumn() {
	}

	@Override
	public String getText(Object element) {
		if(element instanceof TimeZone){
			return String.valueOf(((TimeZone)element).getOffset(System.currentTimeMillis())/ 3600000);
		}else {
			return "";
		}
	}

	@Override
	public String getTitle() {
		return "Offset";
	}

	@Override
	public int getWidth() {
		return 50;
	}
	
	
	

}
