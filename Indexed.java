import java.util.*;

class Main extends Thread {
  public static int blocks[];
  public static int value = 0;
  public static int total=7, count=1, currentBlock=0, blockSize, m, blocksUsed=0;
  public static ArrayList<File> fileTable = new ArrayList<File>();
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    System.out.print("Enter the number of blocks: ");
    m = sc.nextInt();
    blocks = new int[m];
    for(int i=0;i<m;i++){
      blocks[i] = -1;
    }
    System.out.print("Enter the size of one block: ");
    blockSize = sc.nextInt();
    int totalsize = blockSize*m;
    System.out.print("Enter the files: ");
    for(int i=0;i<5;i++){  
      value = sc.nextInt();
      totalsize-=value;      
      if(totalsize>0){
        NewFile nf = new NewFile();
        nf.start(); 
      } 
      else{      
        i+=100;
      }
    }
    try{
      sleep(3000);
    }catch(Exception e){
      System.out.print("Thread Interrupted");
    }
    print();
  }

  static void print(){
     System.out.println("\n\nFILE ALLOCATION TABLE");    
     System.out.print("File Name \tStart Block \tFile Size \tIndex");
    for(int i=0;i<fileTable.size();i++){
      File f = fileTable.get(i);
      System.out.print("\n"+f.fileName + "\t\t" + f.startBlock + "\t\t" + f.size+"\t\t");
      for(int j=0;j<f.blockList.size();j++){
        System.out.print(f.blockList.get(j)+" ");
      }
    }
    System.out.println("\n\nBLOCKS: ");
   System.out.println("Block No. \tFile");    
   for(int i=0;i<m;i++){
        System.out.println(i+"\t\t"+blocks[i]+"\t");
    }
  }
}

class NewFile extends Thread{
  Random r = new Random();
  public void run(){
    File f = new File("File "+Main.count, 0, Main.value);
    double noOfBlocks = Math.ceil((double)f.size/(double)Main.blockSize);
    if(Main.m-Main.blocksUsed>=(int)noOfBlocks){
    System.out.print("\n"+f.fileName + " " + f.startBlock + " " + f.size+"\n");
    Main.fileTable.add(f);
    int temp;
    for(int i=0;i<(int)noOfBlocks;i++){
      do{
        temp = r.nextInt(Main.m);
      }
      while(Main.blocks[temp]!=-1);
      Main.blocksUsed++;
        if(Main.count==Main.blocks[Main.currentBlock]){
           Main.blocks[temp] = Main.count;
           f.blockList.add(temp);
        }
        else{
          f.startBlock=temp;
          Main.blocks[temp] = Main.count;
          f.blockList.add(temp);
        }
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
  ArrayList<Integer> blockList = new ArrayList<Integer>();
  File(String fileName, int startBlock, int size){
    this.fileName = fileName;
    this.startBlock = startBlock;
    this.size = size;
  }
}