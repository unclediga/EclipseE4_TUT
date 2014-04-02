package com.packtpub.e4.clock.ui.internal;

import java.util.Date;
import java.util.TimeZone;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

public class TimeZonePropertySource implements IPropertySource{
	private TimeZone timeZone;
	private Object ID = new Object();
	private Object DAYLIGHT = new Object();
	private Object NAME = new Object();
	public TimeZonePropertySource(TimeZone timeZone) {
		this.timeZone = timeZone;
	}

	@Override
	public Object getEditableValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return new IPropertyDescriptor[]{
				new PropertyDescriptor(ID, "ID"),
				new PropertyDescriptor(DAYLIGHT, "Daylight Saving"),
				new PropertyDescriptor(NAME, "Name")
		};
	}

	@Override
	public Object getPropertyValue(Object id) {
		if(ID.equals(id)){
			return timeZone.getID();
		}else if(id.equals(DAYLIGHT)){
			return timeZone.inDaylightTime(new Date());			
		}else if(id.equals(NAME)){
			return timeZone.getDisplayName();
		}else{
			return null;
		}
	}

	@Override
	public boolean isPropertySet(Object id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void resetPropertyValue(Object id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		// TODO Auto-generated method stub
		
	}

}
