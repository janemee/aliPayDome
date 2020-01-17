package util;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

/**
 * Description: //TODO
 * Created by 陌上人 on 2017/1/17 15:18.
 */
@Slf4j
public class LogUtil {

    private static Class clazz = LogUtil.class;

    public static void error(String str, Exception e,Class clazz){
        LogUtil.clazz = clazz;
        StackTraceElement element = e.getStackTrace()[0];
        LinkedList<Object> args = new LinkedList<>();
        args.add(e.getMessage());
        args.add(element.getLineNumber());
        args.add(element.getMethodName());
        args.add(element.getClassName());
        log.error(str + " : {} ; LineNumber : {} ; MethodName : {} ; ClassName : {} ", args.toArray());
    }

    public static void info(String str, Exception e,Class clazz){
        LogUtil.clazz = clazz;
        StackTraceElement element = e.getStackTrace()[0];
        LinkedList<Object> args = new LinkedList<>();
        args.add(e.getMessage());
        args.add(element.getLineNumber());
        args.add(element.getMethodName());
        args.add(element.getClassName());
        log.info(str + " : {} ; LineNumber : {} ; MethodName : {} ; ClassName : {} ", args.toArray());
    }

    public static void debug(String str, Exception e,Class clazz){
        LogUtil.clazz = clazz;
        StackTraceElement element = e.getStackTrace()[0];
        LinkedList<Object> args = new LinkedList<>();
        args.add(e.getMessage());
        args.add(element.getLineNumber());
        args.add(element.getMethodName());
        args.add(element.getClassName());
        log.info(str + ": {} ; LineNumber : {} ; MethodName : {} ; ClassName : {} ", args.toArray());
    }
}
