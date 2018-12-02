package com.ebb.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtils {
    // logger for demo
    // 生产中不能这么用，因为需要根据logger的命名结构来个性化设置级别、记录器...
    public static final Logger LOGGER = LoggerFactory.getLogger(LoggerUtils.class);
}
