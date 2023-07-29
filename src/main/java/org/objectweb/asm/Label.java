package org.objectweb.asm;

public class Label {
  static final int FLAG_DEBUG_ONLY = 1;
  
  static final int FLAG_JUMP_TARGET = 2;
  
  static final int FLAG_RESOLVED = 4;
  
  static final int FLAG_REACHABLE = 8;
  
  static final int FLAG_SUBROUTINE_CALLER = 16;
  
  static final int FLAG_SUBROUTINE_START = 32;
  
  static final int FLAG_SUBROUTINE_END = 64;
  
  static final int LINE_NUMBERS_CAPACITY_INCREMENT = 4;
  
  static final int FORWARD_REFERENCES_CAPACITY_INCREMENT = 6;
  
  static final int FORWARD_REFERENCE_TYPE_MASK = -268435456;
  
  static final int FORWARD_REFERENCE_TYPE_SHORT = 268435456;
  
  static final int FORWARD_REFERENCE_TYPE_WIDE = 536870912;
  
  static final int FORWARD_REFERENCE_HANDLE_MASK = 268435455;
  
  static final Label EMPTY_LIST = new Label();
  
  public Object info;
  
  short flags;
  
  private short lineNumber;
  
  private int[] otherLineNumbers;
  
  int bytecodeOffset;
  
  private int[] forwardReferences;
  
  short inputStackSize;
  
  short outputStackSize;
  
  short outputStackMax;
  
  short subroutineId;
  
  Frame frame;
  
  Label nextBasicBlock;
  
  Edge outgoingEdges;
  
  Label nextListElement;
  
  public int getOffset() {
    if ((this.flags & 0x4) == 0)
      throw new IllegalStateException("Label offset position has not been resolved yet"); 
    return this.bytecodeOffset;
  }
  
  final Label getCanonicalInstance() { return (this.frame == null) ? this : this.frame.owner; }
  
  final void addLineNumber(int lineNumber) {
    if (this.lineNumber == 0) {
      this.lineNumber = (short)lineNumber;
    } else {
      if (this.otherLineNumbers == null)
        this.otherLineNumbers = new int[4]; 
      int otherLineNumberIndex = this.otherLineNumbers[0] = this.otherLineNumbers[0] + 1;
      if (otherLineNumberIndex >= this.otherLineNumbers.length) {
        int[] newLineNumbers = new int[this.otherLineNumbers.length + 4];
        System.arraycopy(this.otherLineNumbers, 0, newLineNumbers, 0, this.otherLineNumbers.length);
        this.otherLineNumbers = newLineNumbers;
      } 
      this.otherLineNumbers[otherLineNumberIndex] = lineNumber;
    } 
  }
  
  final void accept(MethodVisitor methodVisitor, boolean visitLineNumbers) {
    methodVisitor.visitLabel(this);
    if (visitLineNumbers && this.lineNumber != 0) {
      methodVisitor.visitLineNumber(this.lineNumber & 0xFFFF, this);
      if (this.otherLineNumbers != null)
        for (int i = 1; i <= this.otherLineNumbers[0]; i++)
          methodVisitor.visitLineNumber(this.otherLineNumbers[i], this);  
    } 
  }
  
  final void put(ByteVector code, int sourceInsnBytecodeOffset, boolean wideReference) {
    if ((this.flags & 0x4) == 0) {
      if (wideReference) {
        addForwardReference(sourceInsnBytecodeOffset, 536870912, code.length);
        code.putInt(-1);
      } else {
        addForwardReference(sourceInsnBytecodeOffset, 268435456, code.length);
        code.putShort(-1);
      } 
    } else if (wideReference) {
      code.putInt(this.bytecodeOffset - sourceInsnBytecodeOffset);
    } else {
      code.putShort(this.bytecodeOffset - sourceInsnBytecodeOffset);
    } 
  }
  
  private void addForwardReference(int sourceInsnBytecodeOffset, int referenceType, int referenceHandle) {
    if (this.forwardReferences == null)
      this.forwardReferences = new int[6]; 
    int lastElementIndex = this.forwardReferences[0];
    if (lastElementIndex + 2 >= this.forwardReferences.length) {
      int[] newValues = new int[this.forwardReferences.length + 6];
      System.arraycopy(this.forwardReferences, 0, newValues, 0, this.forwardReferences.length);
      this.forwardReferences = newValues;
    } 
    this.forwardReferences[++lastElementIndex] = sourceInsnBytecodeOffset;
    this.forwardReferences[++lastElementIndex] = referenceType | referenceHandle;
    this.forwardReferences[0] = lastElementIndex;
  }
  
  final boolean resolve(byte[] code, int bytecodeOffset) {
    this.flags = (short)(this.flags | 0x4);
    this.bytecodeOffset = bytecodeOffset;
    if (this.forwardReferences == null)
      return false; 
    boolean hasAsmInstructions = false;
    for (int i = this.forwardReferences[0]; i > 0; i -= 2) {
      int sourceInsnBytecodeOffset = this.forwardReferences[i - 1];
      int reference = this.forwardReferences[i];
      int relativeOffset = bytecodeOffset - sourceInsnBytecodeOffset;
      int handle = reference & 0xFFFFFFF;
      if ((reference & 0xF0000000) == 268435456) {
        if (relativeOffset < -32768 || relativeOffset > 32767) {
          int opcode = code[sourceInsnBytecodeOffset] & 0xFF;
          if (opcode < 198) {
            code[sourceInsnBytecodeOffset] = (byte)(opcode + 49);
          } else {
            code[sourceInsnBytecodeOffset] = (byte)(opcode + 20);
          } 
          hasAsmInstructions = true;
        } 
        code[handle++] = (byte)(relativeOffset >>> 8);
        code[handle] = (byte)relativeOffset;
      } else {
        code[handle++] = (byte)(relativeOffset >>> 24);
        code[handle++] = (byte)(relativeOffset >>> 16);
        code[handle++] = (byte)(relativeOffset >>> 8);
        code[handle] = (byte)relativeOffset;
      } 
    } 
    return hasAsmInstructions;
  }
  
  final void markSubroutine(short subroutineId) {
    Label listOfBlocksToProcess = this;
    listOfBlocksToProcess.nextListElement = EMPTY_LIST;
    while (listOfBlocksToProcess != EMPTY_LIST) {
      Label basicBlock = listOfBlocksToProcess;
      listOfBlocksToProcess = listOfBlocksToProcess.nextListElement;
      basicBlock.nextListElement = null;
      if (basicBlock.subroutineId == 0) {
        basicBlock.subroutineId = subroutineId;
        listOfBlocksToProcess = basicBlock.pushSuccessors(listOfBlocksToProcess);
      } 
    } 
  }
  
  final void addSubroutineRetSuccessors(Label subroutineCaller) {
    Label listOfProcessedBlocks = EMPTY_LIST;
    Label listOfBlocksToProcess = this;
    listOfBlocksToProcess.nextListElement = EMPTY_LIST;
    while (listOfBlocksToProcess != EMPTY_LIST) {
      Label basicBlock = listOfBlocksToProcess;
      listOfBlocksToProcess = basicBlock.nextListElement;
      basicBlock.nextListElement = listOfProcessedBlocks;
      listOfProcessedBlocks = basicBlock;
      if ((basicBlock.flags & 0x40) != 0 && basicBlock.subroutineId != subroutineCaller.subroutineId)
        basicBlock.outgoingEdges = new Edge(basicBlock.outputStackSize, subroutineCaller.outgoingEdges.successor, basicBlock.outgoingEdges); 
      listOfBlocksToProcess = basicBlock.pushSuccessors(listOfBlocksToProcess);
    } 
    while (listOfProcessedBlocks != EMPTY_LIST) {
      Label newListOfProcessedBlocks = listOfProcessedBlocks.nextListElement;
      listOfProcessedBlocks.nextListElement = null;
      listOfProcessedBlocks = newListOfProcessedBlocks;
    } 
  }
  
  private Label pushSuccessors(Label listOfLabelsToProcess) {
    Label newListOfLabelsToProcess = listOfLabelsToProcess;
    Edge outgoingEdge = this.outgoingEdges;
    while (outgoingEdge != null) {
      boolean isJsrTarget = ((this.flags & 0x10) != 0 && outgoingEdge == this.outgoingEdges.nextEdge);
      if (!isJsrTarget && outgoingEdge.successor.nextListElement == null) {
        outgoingEdge.successor.nextListElement = newListOfLabelsToProcess;
        newListOfLabelsToProcess = outgoingEdge.successor;
      } 
      outgoingEdge = outgoingEdge.nextEdge;
    } 
    return newListOfLabelsToProcess;
  }
  
  public String toString() { return "L" + System.identityHashCode(this); }
}
