package com.opsc.maven.interfaces;

import java.util.Properties;

public interface Query {
	public Properties getConnectionProps();
	public String getConnUrl();
	public String getCmd();
	public String getQId();
}
