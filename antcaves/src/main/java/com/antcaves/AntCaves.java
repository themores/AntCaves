package com.antcaves;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liyuan
 * @description
 * @email thisuper@qq.com
 * @date 17/3/9 上午9:09
 */

public class AntCaves {
    private static Map<String, Class> mRouterTable = new HashMap<String, Class>();

    private static Map<String, String> mRouterParamTable = new HashMap<String, String>();

    public static void addRouter(String path, List<String> params, Class clazz) {
        mRouterTable.put(path,clazz);
        String paramStr="";
        for(int i=0;i<params.size();i++){
            if(i<params.size()-1)
            { paramStr+=params.get(i)+"@";}
            else
            { paramStr+=params.get(i);}};
        mRouterParamTable.put(path,paramStr);
    }

    public static void addRouter(String path, Class clazz) {
        mRouterTable.put(path,clazz);
    }

    public static Map<String, Class> getRouters() {
        return mRouterTable;
    }

    public static Map<String, String> getParams() {
        return mRouterParamTable;
    }
}
