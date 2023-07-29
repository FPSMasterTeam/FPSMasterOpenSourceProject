package org.objectweb.asm.tree;

import org.objectweb.asm.ClassVisitor;

public class InnerClassNode {
  public String name;
  
  public String outerName;
  
  public String innerName;
  
  public int access;
  
  public InnerClassNode(String name, String outerName, String innerName, int access) {
    this.name = name;
    this.outerName = outerName;
    this.innerName = innerName;
    this.access = access;
  }
  
  public void accept(ClassVisitor classVisitor) { classVisitor.visitInnerClass(this.name, this.outerName, this.innerName, this.access); }
}
