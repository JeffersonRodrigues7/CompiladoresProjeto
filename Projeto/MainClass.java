import java.util.Scanner;
public class MainClass{ 
  public static void main(String args[]){
       Scanner _key = new Scanner(System.in);
double  a;
double  b;
double  c;
double  d;
double  e;
boolean k;
String  t1;
String  t2;
a= _key.nextDouble();
b= _key.nextDouble();
c = 0.0;
t1 = "teste1";
a = 1+2*3/b;
if (a>b) {
System.out.println(a);}else {
System.out.println(b);}

while (c<10) {
System.out.println(c);
c = c+1;
}
do {
System.out.println(c);
c = c+1.0;
}while(c<20);

for (d=0;d<10;d=d+2){
System.out.println(d);
}

System.out.println(t1);
  }}