import java.util.*;

class Main extends Thread {
  public static int blocks[][];
  public static int value=0;
  public static int total=5, count=1, currentBlock=0, blockSize, m, blocksUsed=0;
  public static ArrayList<File> fileTable = new ArrayList<File>();
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    System.out.print("Enter the number of blocks: ");
    m = sc.nextInt();
    blocks = new int[m][2];
    for(int i=0;i<m;i++){
      blocks[i][0] = -1;
      blocks[i][1] = -1;
    }
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
  }

  static void print(){
    System.out.println("\n\nFILE ALLOCATION TABLE");    
    System.out.println("File Name \tStart Block \tFile Size");
    for(int i=0;i<fileTable.size();i++){
      File f = fileTable.get(i);
      System.out.println(f.fileName + "\t\t" + f.startBlock + "\t\t" + f.size);
    }

    System.out.println("\nBLOCKS: ");
    System.out.println("Block No. \tFile \tNext Block");
    for(int i=0;i<m;i++){
      System.out.print(i+"\t\t");
      for(int j=0;j<2;j++){
        System.out.print(blocks[i][j]+"\t");
      }
      System.out.print("\n");
    }
  }
}

class NewFile extends Thread{
  Random r = new Random();
  public void run(){
    File f = new File("File "+Main.count, 0, Main.value);
    double noOfBlocks = Math.ceil((double)f.size/(double)Main.blockSize);
    if(Main.m-Main.blocksUsed>=(int)noOfBlocks){
    System.out.print("\n"+f.fileName + " " + f.size+"\n");
    Main.fileTable.add(f);
    int temp;
    for(int i=0;i<(int)noOfBlocks;i++){
      do{
        temp = r.nextInt(Main.m);
      }
      while(Main.blocks[temp][0]!=-1);
      Main.blocksUsed++;
        if(Main.count==Main.blocks[Main.currentBlock][0]){
           Main.blocks[temp][0] = Main.count;
           Main.blocks[Main.currentBlock][1] = temp;
           Main.currentBlock = temp;
        }
        else{
          f.startBlock=temp;
          Main.blocks[temp][0] = Main.count;
          Main.currentBlock = temp;
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
  File(String fileName, int startBlock, int size){
    this.fileName = fileName;
    this.startBlock = startBlock;
    this.size = size;
  }
}