package com.dxc.pojos;

import java.util.Comparator;

import com.dxc.pojos.trans;

public class comparetoken implements Comparator<trans> {

public int compare(trans t1,trans t2)
{
	if(t1.timestamp>t2.timestamp)
		return -1;
	else if(t1.timestamp<t2.timestamp)
		return 1;
	else
		return 0;
}
}
