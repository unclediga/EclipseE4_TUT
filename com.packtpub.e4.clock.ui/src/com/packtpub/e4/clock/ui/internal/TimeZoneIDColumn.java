package com.packtpub.e4.clock.ui.internal;

import java.util.TimeZone;

public class TimeZoneIDColumn extends TimeZoneColumn {

	public TimeZoneIDColumn() {
	}

	@Override
	public String getText(Object element) {
		if(element instanceof TimeZone){
			return ((TimeZone)element).getID();
		}else {
			return "";
		}
	}

	@Override
	public String getTitle() {
		return "ID";
	}

}
