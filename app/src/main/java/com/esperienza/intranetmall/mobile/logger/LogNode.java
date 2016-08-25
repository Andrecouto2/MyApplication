package com.esperienza.intranetmall.mobile.logger;

public interface LogNode {


    public void println(int priority, String tag, String msg, Throwable tr);

}
