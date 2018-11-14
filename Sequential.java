import java.util.*;

class Main extends Thread {
  public static int blocks[];  
  public static int value=0;
  public static int total=4, count=1, currentBlock=0, blockSize, m, blocksUsed;
  public static ArrayList<File> fileTable = new ArrayList<File>();
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    System.out.print("Enter the number of blocks: ");
    m = sc.nextInt();
    blocks = new int[m];
    System.out.print("Enter the size of one block: ");
    blockSize = sc.nextInt();
    int totalsize = blockSize*m;
    System.out.println("Enter the files: ");
    for(int i=0;i<5;i++){  
      value = sc.nextInt();
      totalsize-=value;      
      if(totalsize>0){
        NewFile nf = new NewFile();
        nf.start(); 
      } 
      else{      
        System.out.println("New File Cannot be Accomodated");
        i+=100;
      }
    }
    try{
      sleep(3000);
    }catch(Exception e){
      System.out.print("Thread Interrupted");
    }
    print(); 
    blocksUsed = m;   
    int op;
    Random r = new Random();
    do{
      System.out.print("\n1. Delete \n2. Create new file \n3. Exit \nEnter your choice: ");
      op = sc.nextInt();
      if(op==1){
      System.out.print("Enter the file number: ");
        int fileNo = sc.nextInt();
        File f = fileTable.get(fileNo - 1);
        int newFileSize = 0;
        double prevNoOfBlocks = Math.ceil((double)f.size/(double)Main.blockSize);
        double noOfBlocks = Math.ceil((double)newFileSize/(double)Main.blockSize);
        int diff = (int)prevNoOfBlocks-(int)noOfBlocks;
        f.size = newFileSize;
        int blocks2[] = new int[m];
        for(int k=0;k<m;k++){
          blocks2[k] = blocks[k];
        }
        System.out.print("\n"+f.fileName + " " + f.startBlock + " " + f.size);
        for(int k=f.startBlock;k<f.startBlock+(int)prevNoOfBlocks;k++){
          blocks[k] = 0;
        }
        currentBlock = f.startBlock;
        blocksUsed = blocksUsed - diff;
        if(f.size==0){
          fileTable.remove(fileNo-1);
          System.out.println("\nFile "+fileNo+" deleted.");
        }
      print();
      }
      else if(op==2){
        System.out.println("Enter the file size: ");
        value = sc.nextInt();
        totalsize-=value;  
        NewFile nf2 = new NewFile();
        nf2.start();
        try{
          sleep(3000);
        }
        catch(Exception e){
          System.out.println("System Interrupted");
        }
        print();
      }
    }while(op!=3);
  }

  static void print(){
    System.out.println("\n\nFILE ALLOCATION TABLE");    System.out.println("File Name \tStart Block \tFile Size");
    for(int i=0;i<fileTable.size();i++){
      File f = fileTable.get(i);
      System.out.println(f.fileName + "\t\t" + f.startBlock + "\t\t" + f.size);
    }

    System.out.println("\nBLOCKS:");
    System.out.println("Block No. \tFile");
    for(int i=0;i<m;i++){
      System.out.println(i+"\t\t"+blocks[i]);
    }
    System.out.println("");
  }
}

class NewFile extends Thread{
  Random r = new Random();
  public void run(){
    File f = new File("File "+Main.count, Main.currentBlock, Main.value);
    double noOfBlocks = Math.ceil((double)f.size/(double)Main.blockSize);
    if(Main.m-Main.blocksUsed>=(int)noOfBlocks){
    System.out.print("\n"+f.fileName + " " + f.size+"\n");
    Main.fileTable.add(f);
    for(int i=0;i<(int)noOfBlocks;i++){
      Main.blocks[Main.currentBlock] = Main.count;
      Main.currentBlock++;
      Main.blocksUsed++;
    }
    Main.count++;
    try{
      sleep((r.nextInt(2)+1)*1000);
    }
    catch(Exception e){
      System.out.print("Thread Interrupted");
    }
    /*if(Main.count<Main.total){
      NewFile nf = new NewFile();
      nf.start();
    }*/
    }
    else{
      System.out.println("\nNew File couldnt be accomodated");
    }
  }
}

class File{
  String fileName;
  int startBlock;
  int size;
  File(String fileName, int startBlock, int size){
    this.fileName = fileName;
    this.startBlock = startBlock;
    this.size = size;
  }
}