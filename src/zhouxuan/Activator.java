package zhouxuan;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IPluginRegistry;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.junit.ITestRunListener;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import zhouxuan.JunitAction.Listener;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "zhouxuan"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	private List listeners;
	private static final String listenerId="zhouxuan.listeners";
	
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}
	
	public void addTestListener(ITestRunListener listener) {
		getListeners().add(listener);
		
	}

	public void removeTestListener(ITestRunListener listener) {
		getListeners().remove(listener);
		
	}
	
	public void fireTestsStarted(int count){
		for(Iterator all = getListeners().iterator(); all.hasNext();){
			final ITestRunListener each =(ITestRunListener) all.next();
			ISafeRunnable runnable = new ISafeRunnable(){
				public void handleException(Throwable exception){
					all.remove();
				};
				public void run() throws Exception{	
					each.testRunStarted(count);
				}
			};
			
			Platform.run(runnable);
		}

	}
	
	public void fireTestsFinished(){
		for(Iterator all = getListeners().iterator(); all.hasNext();){
			final ITestRunListener each =(ITestRunListener) all.next();
			ISafeRunnable runnable = new ISafeRunnable(){
				public void handleException(Throwable exception){
					all.remove();
				};
				public void run() throws Exception{
					each.testRunTerminated();
				}
			};
			
			Platform.run(runnable);
		}
		
	}
	public void fireTestStarted(String klass,String methodName){
		for(Iterator all = getListeners().iterator(); all.hasNext();){
			final ITestRunListener each =(ITestRunListener) all.next();
			ISafeRunnable runnable = new ISafeRunnable(){
				public void handleException(Throwable exception){
					all.remove();
				};
				public void run() throws Exception{
					each.testStarted(klass,methodName);	
				}
			};
			
			Platform.run(runnable);
		}
	}
	
	public void fireTestFailed(String klass,String method,String trace){
		for(Iterator all = getListeners().iterator(); all.hasNext();){
			final ITestRunListener each =(ITestRunListener) all.next();
			ISafeRunnable runnable = new ISafeRunnable(){
				public void handleException(Throwable exception){
					all.remove();
				};
				public void run() throws Exception{
					each.testFailed(0,klass,method,trace);
				}
			};
			
			Platform.run(runnable);
		}
		
	}

	private List<ITestRunListener> getListeners() {
		if(listeners == null){
			System.out.println("^^&&&&&&&&");
			listeners = computeListeners();
		}
		return listeners;
	}

	private List computeListeners() {
		IPluginRegistry registry = Platform.getPluginRegistry();
		
		IExtensionPoint extensionPoint =
				registry.getExtensionPoint(listenerId);
		
		IExtension[] extensions = extensionPoint.getExtensions();
		
		List results = new ArrayList();
		
		for(int i = 0;i< extensions.length; i++){
			IConfigurationElement[] elements = 
					extensions[i].getConfigurationElements();
		    for(int j= 0; j < elements.length; j++){
		    	try{
		    		Object listener = elements[j].createExecutableExtension("class");
		    		if(listener instanceof ITestRunListener){
		    			results.add(listener);
		    		}
		    	}
		    	catch(CoreException e){
		    		e.printStackTrace();
		    		
		    	}
		    }
		  
		}
		System.out.println("^^&&&&&&&&");
		System.out.println(results);
		  return results;
	}

	public void run(IType type) {
		new TestRunner().run(type);
		
	}


}
