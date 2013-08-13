package com.prl.cvto.ui.fragment.helper;

import com.prl.cvto.CloudVirtualTryOn.ConfigPool;
import com.prl.cvto.config.ServiceContext;

public interface FragmentBase {
	/**
	 * Retrieve the service context which initialized by the parent activity
	 * @return service context which consists of lots of services. Return null if the
	 * activity has no service context, so the fragment needs to handle this case.
	 */
    ServiceContext getServiceContext();
    
    /**
     * Retrieve the application configures
     * @return configure pool which consists of many configures 
     */
    ConfigPool getConfigPool();
}