package zhouxuan;

import java.util.List;

import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.junit.ITestRunListener;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class JunitAction implements IObjectActionDelegate {
	public static class Listener implements ITestRunListener {
		  private boolean passed= true;
		@Override
		public void testRunStarted(int testCount) {
			
		}

		@Override
		public void testRunEnded(long elapsedTime) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void testRunStopped(long elapsedTime) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void testEnded(String testId, String testName) {
			 String message= passed ? "Pass" : "Fail";
			    MessageDialog.openInformation(null, "Test Results", message);
			
		}
		@Override
		public void testFailed(int status, String testId, String testName,
				String trace) {
			passed= false;
			
		}
		@Override
		public void testRunTerminated() {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void testReran(String testId, String testClass, String testName,
				int status, String trace) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void testStarted(String testId, String testName) {
			// TODO Auto-generated method stub
			
		}
		}
	ISelection selection;
	

	@Override
	public void run(IAction action) {
		 if (! (selection instanceof IStructuredSelection)) return;
			  IStructuredSelection structured=
			      (IStructuredSelection) selection;
			  IType type= (IType) structured.getFirstElement();
			  ITestRunListener listener= new Listener();
			  Activator instance= Activator.getDefault();
			  System.out.println("$$$$$$$$$$$$$$$");
			  System.out.println(instance);
			  System.out.println("$$$$$$$$$$$$$$$");
			  instance.addTestListener(listener);
			  instance.run(type);
			  instance.removeTestListener(listener);
			  System.out.println("########################");
//			  ITestRunListener listener = new Listener();
			  
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection= selection;
		
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// TODO Auto-generated method stub
		
	}

}
