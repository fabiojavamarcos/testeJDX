package testeJDX;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Set;

import br.usp.ime.jdx.app.JDX;
import br.usp.ime.jdx.entity.relationship.dependency.DependencyReport;
import br.usp.ime.jdx.entity.relationship.dependency.importdep.MethodImportDependency;
import br.usp.ime.jdx.entity.relationship.dependency.importdep.PackageImportDependency;
import br.usp.ime.jdx.entity.relationship.dependency.m2m.MethodCallDependency;
import br.usp.ime.jdx.entity.relationship.dependency.m2t.ReferenceDependency;
import br.usp.ime.jdx.entity.relationship.dependency.t2t.ClazzInheritanceDependency;
import br.usp.ime.jdx.entity.relationship.dependency.t2t.ImplementsDependency;
import br.usp.ime.jdx.entity.relationship.dependency.t2t.InterfaceInheritanceDependency;
import br.usp.ime.jdx.entity.system.CompUnit;
import br.usp.ime.jdx.entity.system.Method;
import br.usp.ime.jdx.filter.JavaAPIMatcher;

public class TestJDX {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JDX jdx = new JDX();
		
		DependencyReport depReport = null;
		try {
			depReport = jdx.calculateDepsFrom(
			"/Users/fabiomarcosdeabreusantos/Documents/dev/github/jabref/src",
			true, "*.java", new JavaAPIMatcher(), false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//System.out.println(depReport.getJavaProject().getCompUnits());
		
		OutputStream os = null;
		try {
			os = new FileOutputStream("/Users/fabiomarcosdeabreusantos/Documents/JabRefMethods.csv");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Starting----------------");
		
		
		OutputStreamWriter osw = new OutputStreamWriter(os);
	    BufferedWriter bw = new BufferedWriter(osw);
	    try {
	    	bw.write("Method,Class,Return Type,Parameters\n");
			for(Method m : depReport.getJavaProject().getMethods()) {
				System.out.println("Method:"+ m.getName() + "," + m.getClass() +","+m.getParameters()+","+m.getReturnType());
				
					bw.write(m.getName() + "," + m.getClass()+","+m.getParameters()+","+m.getReturnType()+"\n");
				
				
				//System.out.println(m.getParentType().getFQN());
			}
		bw.close();
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("End Methods----------------");
		
		try {
			os = new FileOutputStream("/Users/fabiomarcosdeabreusantos/Documents/JabRefMethodDetails.csv");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		osw = new OutputStreamWriter(os);
	    bw = new BufferedWriter(osw);
	    try {
//	    	bw.write("Class abs,Class Rel,Signature,Parameters,java doc, source, body\n");
	    	bw.write("Class-abs,Class-Rel,Signature,Parameters,source,body\n");
		    
			for (CompUnit u: depReport.getJavaProject().getCompUnits()){
				System.out.println("CompUnit:"+ u.getAbsolutePath() + "," + u.getRelativePath());
				for (Method m : u.getMethods()){
//					System.out.println("Method detail:"+ m.getSignature() + "," + m.getParameters() +","+ m.getJavaDoc() +","+ m.getSourceCode()+","+ m.getBody() );
//					bw.write(u.getAbsolutePath()+","+u.getRelativePath()+","+m.getSignature() + "," + m.getParameters() +","+ m.getJavaDoc() +","+ m.getSourceCode()+","+ m.getBody() +"\n");
					System.out.println("Method detail:"+ m.getSignature() + ","+ ","+ m.getSourceCode() +"," + m.getParameters()  );
					bw.write(u.getAbsolutePath()+","+u.getRelativePath()+","+m.getSignature() + ","+ m.getSourceCode() +","+ m.getParameters()  +"\n");

				}
			}
		
		bw.close();
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("End Method Details----------------");
		
		try {
			os = new FileOutputStream("/Users/fabiomarcosdeabreusantos/Documents/JabRefMethodImportDependency.csv");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		osw = new OutputStreamWriter(os);
	    bw = new BufferedWriter(osw);
	    try {
	    	bw.write("Client,Label,DepType,RelantionshipType,Supplier\n");
	    	for (MethodImportDependency mi : depReport.getMethodImportDependencies()) {
	    		System.out.println("Method Import Dependency:"+ mi.getClient()+","+mi.getLabel()+","+mi.getDependencyType()+","+mi.getRelationshipType()+","+mi.getSupplier());
				
					bw.write(mi.getClient()+","+mi.getLabel()+","+mi.getDependencyType()+","+mi.getRelationshipType()+","+mi.getSupplier()+"\n");
				
				
				//System.out.println(m.getParentType().getFQN());
			}
		bw.close();
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    System.out.println("End MethodImportDependency----------------");
	    try {
			os = new FileOutputStream("/Users/fabiomarcosdeabreusantos/Documents/JabRefPackageImportDependency.csv");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		osw = new OutputStreamWriter(os);
	    bw = new BufferedWriter(osw);
	    try {
	    	bw.write("Client,DepType,RelantionshipType,Supplier\n");
	    	for (PackageImportDependency pid: depReport.getPackageImportDependencies()){
	    		System.out.println("PackageImportDependency:"+ pid.getClient()+","+pid.getDependencyType()+","+pid.getRelationshipType()+","+pid.getSupplier());
				
					bw.write(pid.getClient()+","+pid.getDependencyType()+","+pid.getRelationshipType()+","+pid.getSupplier()+"\n");
				
				
				//System.out.println(m.getParentType().getFQN());
			}
		bw.close();
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    System.out.println("End PackageImportDependency----------------");
	    
	    try {
			os = new FileOutputStream("/Users/fabiomarcosdeabreusantos/Documents/JabRefImplementsDependency.csv");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		osw = new OutputStreamWriter(os);
	    bw = new BufferedWriter(osw);
	    try {
	    	bw.write("Client,Label,DepType,RelantionshipType,Supplier\n");
	    	for (ImplementsDependency id : depReport.getImplementsDependencies()){
	    		System.out.println("ImplementsDependency:"+ id.getClient()+","+id.getLabel()+","+id.getDependencyType()+","+id.getRelationshipType()+","+id.getSupplier());
				
					bw.write(id.getClient()+","+id.getLabel()+","+id.getDependencyType()+","+id.getRelationshipType()+","+id.getSupplier()+"\n");
				
				
				//System.out.println(m.getParentType().getFQN());
			}
		bw.close();
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    System.out.println("End ImplementsDependency----------------");
	    
	    try {
			os = new FileOutputStream("/Users/fabiomarcosdeabreusantos/Documents/JabRefInterfaceInheritanceDependency.csv");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		osw = new OutputStreamWriter(os);
	    bw = new BufferedWriter(osw);
	    try {
	    	bw.write("Client,DepType,RelantionshipType,Supplier\n");
	    	for  (InterfaceInheritanceDependency iid : depReport.getInterfaceInheritanceDependencies()){
				System.out.println("InterfaceInheritanceDependency:"+ iid.getClient()+","+iid.getDependencyType()+","+iid.getRelationshipType()+","+iid.getSupplier());
				
					bw.write(iid.getClient()+","+iid.getDependencyType()+","+iid.getRelationshipType()+","+iid.getSupplier()+"\n");
				
				
				//System.out.println(m.getParentType().getFQN());
			}
		bw.close();
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    System.out.println("End InterfaceInheritanceDependency----------------");
	    
	    try {
			os = new FileOutputStream("/Users/fabiomarcosdeabreusantos/Documents/JabRefMethodCallDepedency.csv");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		osw = new OutputStreamWriter(os);
	    bw = new BufferedWriter(osw);
	    try {
	    	bw.write("Client,Label,DepType,RelantionshipType,Supplier\n");
	    	for (MethodCallDependency mcd : depReport.getMethodCallDependencies()){
				System.out.println("MethodCallDepedency:"+ mcd.getClient()+","+mcd.getLabel()+","+mcd.getDependencyType()+","+mcd.getRelationshipType()+","+mcd.getSupplier());
				
					bw.write(mcd.getClient()+","+mcd.getLabel()+","+mcd.getDependencyType()+","+mcd.getRelationshipType()+","+mcd.getSupplier()+"\n");
				
				
				//System.out.println(m.getParentType().getFQN());
			}
		bw.close();
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    System.out.println("End MethodCallDepedency----------------");
	    
	    try {
			os = new FileOutputStream("/Users/fabiomarcosdeabreusantos/Documents/JabRefReferenceDependency.csv");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		osw = new OutputStreamWriter(os);
	    bw = new BufferedWriter(osw);
	    try {
	    	bw.write("Client,Label,DepType,RelantionshipType,Supplier\n");
	    	for (ReferenceDependency rd : depReport.getReferenceDependencies()){
				System.out.println("ReferenceDependency:"+ rd.getClient()+","+rd.getLabel()+","+rd.getDependencyType()+","+rd.getRelationshipType()+","+rd.getSupplier());
				
					bw.write(rd.getClient()+","+rd.getLabel()+","+rd.getDependencyType()+","+rd.getRelationshipType()+","+rd.getSupplier()+"\n");
				
				
				//System.out.println(m.getParentType().getFQN());
			}
		bw.close();
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    System.out.println("End ReferenceDependency----------------");
	    
	    try {
			os = new FileOutputStream("/Users/fabiomarcosdeabreusantos/Documents/JabRefClazzInheritanceDependency.csv");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		osw = new OutputStreamWriter(os);
	    bw = new BufferedWriter(osw);
	    try {
	    	bw.write("Client,Label,DepType,RelantionshipType,Supplier\n");
	    	for (ClazzInheritanceDependency cid : depReport.getClazzInheritanceDependencies()){
				System.out.println("ClazzInheritanceDependency:"+ cid.getClient()+","+cid.getLabel()+","+cid.getDependencyType()+","+cid.getRelationshipType()+","+cid.getSupplier());
				
					bw.write(cid.getClient()+","+cid.getLabel()+","+cid.getDependencyType()+","+cid.getRelationshipType()+","+cid.getSupplier()+"\n");
				
				
				//System.out.println(m.getParentType().getFQN());
			}
		bw.close();
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    System.out.println("End ClazzInheritanceDependency----------------");
		
	}

}



