package com.packtpub.e4.clock.ui.internal;

import java.util.TimeZone;

public class TimeZoneDispayColumn extends TimeZoneColumn {

	public TimeZoneDispayColumn() {
	}

	@Override
	public String getText(Object element) {
		if(element instanceof TimeZone){
			return ((TimeZone)element).getDisplayName();
		}else {
			return "";
		}
	}

	@Override
	public String getTitle() {
		return "Display name";
	}

}
