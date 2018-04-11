package com.taobao.diamond.client.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.taobao.diamond.client.BatchHttpResult;
import com.taobao.diamond.common.Constants;
import com.taobao.diamond.manager.DiamondManager;
import com.taobao.diamond.manager.ManagerListener;
import com.taobao.diamond.manager.impl.DefaultDiamondManager;

public class DiamondEnv implements AutoCloseable {
	protected final Log logger = LogFactory.getLog(getClass());
	private Map<String, DiamondManager> dmMap = new HashMap<>();
	private String unitName;
	
	private DiamondManager getDiamondManager(String dataId, String group) {
		String key = dataId + "#_#" + group+"@"+unitName;
		DiamondManager dm = dmMap.get(key);
		if (dm == null) {
			dm = new DefaultDiamondManager(group, dataId, new ArrayList<ManagerListener>());
			dmMap.put(key, dm);
		}
		return dm;
	}
	
	public DiamondEnv(String unitName) {
		this.unitName = unitName;
	}
	
	@Override
	public void close() {
		for (DiamondManager dm : dmMap.values()) {
			dm.close();
		}
		dmMap.clear();
	}

	public String getConfig(String dataId, String group,  long timeout) throws IOException {
		if (logger.isDebugEnabled()) logger.debug("dataId=" + dataId + ", " + group + ", timeout=" + timeout);
		DiamondManager dm = getDiamondManager(dataId, group);
		return dm.getAvailableConfigureInfomation(timeout);
	}

	public void removeListener(String dataId, String group, ManagerListener listener) {
		List<ManagerListener> managerListeners = getDiamondManager(dataId, group).getManagerListeners();
		if (managerListeners != null) {
			managerListeners.remove(listener);
			getDiamondManager(dataId, group).setManagerListeners(managerListeners);
		}
	}

	public void addListeners(String dataId, String group, List<? extends ManagerListener> handlers) {
		List<ManagerListener> managerListeners = getDiamondManager(dataId, group).getManagerListeners();
		ArrayList<ManagerListener> newManagerListeners = new ArrayList<ManagerListener>();
		newManagerListeners.addAll(managerListeners);
		newManagerListeners.addAll(handlers);
		getDiamondManager(dataId, group).setManagerListeners(newManagerListeners);
	}

	public List<ManagerListener> getListeners(String dataId, String group) {
		return getDiamondManager(dataId, group).getManagerListeners();
	}

	public BatchHttpResult batchQuery(List<String> dataIds, String group, int timeout) {
		if (logger.isDebugEnabled()) logger.debug("dataId=" + dataIds + ", " + group + ", timeout=" + timeout);
		BatchHttpResult result = new BatchHttpResult();
		for (String dataId : dataIds) {
			try {
				result.addResult(dataId, group,
						getConfig(dataId, group, timeout));
			} catch (IOException ex) {
				result.setSuccess(false);
				result.setStatusCode(ex.toString());
				break;
			}
		}
		return result;
	}

	private static Map<String, DiamondEnv> envs = new HashMap<>();

	public static final DiamondEnv defaultEnv = getDiamondUnitEnv("default");

	public static DiamondEnv getDiamondUnitEnv(String unitName) {
		if (unitName == null) unitName = "default";
		DiamondEnv env = envs.get(unitName);
		if (env == null) {
			env = new DiamondEnv(unitName);
			envs.put(unitName, env);
		}
		return env;
	}
}
