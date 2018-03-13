package com.taobao.diamond.client;

import com.taobao.diamond.manager.DiamondManager;
import com.taobao.diamond.manager.ManagerListener;
import com.taobao.diamond.manager.impl.DefaultDiamondManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiamondEnv implements AutoCloseable {

    protected final Log logger = LogFactory.getLog(getClass());

    private Map<String, DiamondManager> dmMap = new HashMap<String, DiamondManager>();

    private DiamondManager getDiamondManager(String dataId, String group) {
        String key = dataId + "#_#" + group;
        DiamondManager dm = dmMap.get(key);
        if (dm == null) {
            dm = new DefaultDiamondManager(group, dataId, new ArrayList<ManagerListener>());
            dmMap.put(key, dm);
        }
        return dm;
    }

    public DiamondEnv(){

    }

    public void close() {
        for (DiamondManager dm : dmMap.values()) {
            dm.close();
        }
        dmMap.clear();
    }

    public String doGetConfig(String dataId, String group, long timeout) throws IOException {
        if (logger.isDebugEnabled()) logger.debug("dataId=" + dataId + ", " + group + ", timeout=" + timeout);
        DiamondManager dm = getDiamondManager(dataId, group);
        return dm.getAvailableConfigureInfomation(timeout);
    }

    public void doRemoveListener(String dataId, String group, ManagerListener listener) {
        List<ManagerListener> managerListeners = getDiamondManager(dataId, group).getManagerListeners();
        if(managerListeners!=null){
            managerListeners.remove(listener);
            getDiamondManager(dataId, group).setManagerListeners(managerListeners);
        }
    }

    public void doAddListeners(String dataId, String group, List<? extends ManagerListener> handlers) {
        List<ManagerListener> managerListeners = getDiamondManager(dataId, group).getManagerListeners();
        ArrayList<ManagerListener> newManagerListeners = new ArrayList<ManagerListener>();
        newManagerListeners.addAll(managerListeners);
        newManagerListeners.addAll(handlers);
        getDiamondManager(dataId, group).setManagerListeners(newManagerListeners);
    }

    private static final DiamondEnv defaultDiamondEnv = new DiamondEnv();

    public static String getConfig(String dataId, String group, long timeout) throws IOException {
        return defaultDiamondEnv.doGetConfig(dataId, group, timeout);
    }

    public static void removeListener(String dataId, String group, ManagerListener listener){
        defaultDiamondEnv.doRemoveListener(dataId, group, listener);
    }

    public static void addListeners(String dataId, String group, List<? extends ManagerListener> handlers){
        defaultDiamondEnv.doAddListeners(dataId, group, handlers);
    }
}