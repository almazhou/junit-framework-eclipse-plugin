package zhouxuan;

import org.eclipse.jdt.core.IType;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class JunitAction implements IObjectActionDelegate {
	ISelection selection;
	

	@Override
	public void run(IAction action) {
		System.out.println("I am here now!!!!!!!!!!!!");
		 if (! (selection instanceof IStructuredSelection)) return;
		 System.out.println("After If now!@@@@@@@@@@@@@@");
			  IStructuredSelection structured=
			      (IStructuredSelection) selection;
			  IType type= (IType) structured.getFirstElement();
	     System.out.println("After Type #################"+type);
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
