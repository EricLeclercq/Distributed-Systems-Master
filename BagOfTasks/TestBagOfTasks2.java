class Premier{
 public static boolean isPrime(int n){
  int cdtArret = (int)(Math.sqrt(n)+1);
  for (int i=2; i<=cdtArret; i++)
   if (n%i == 0)
   return false;
  return true;
 }
}

class Worker extends Thread{
 private BagOfTasks bot;
 public Worker(BagOfTasks bot){
   this.bot=bot;
  }
 public void run(){
  int i;
  while ((i=bot.getNext())!=-1)
   if (Premier.isPrime(i)==true) bot.primeFound();
 }
}

class BagOfTasks{
 private int borneSup;
 private int courant=2;
 private int nbPrime=0;
 public BagOfTasks(int max){
  borneSup=max;
 }
 public synchronized int getNext(){
  if (courant >borneSup) return -1;
  courant++;
  return courant;
 }
 public synchronized void primeFound(){nbPrime++;}
 public int getPrimeNb(){return nbPrime;}
}



public class TestBagOfTasks2{
 public static void main(String argv[]) {
  int nbWorkers = 6;
  Thread tabWorkers[] = new Worker[nbWorkers];
  BagOfTasks bot = new BagOfTasks(500000000);
  for (int i=0; i<nbWorkers; i++){
    tabWorkers[i]=new Worker(bot);
    tabWorkers[i].start();
  }
  for (int i=0; i<nbWorkers; i++){
    try{
     tabWorkers[i].join();
   }catch(InterruptedException e){e.printStackTrace();}
  }
  System.out.println("nombre de premiers trouvé = "+bot.getPrimeNb());
}
}
