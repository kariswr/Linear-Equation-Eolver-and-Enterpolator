import java.util.Scanner;
//import Matrix;

public class Menu extends Matrix {

    //Attributes
    private Scanner in = new Scanner (System.in);
    Matrix M1 = new Matrix(); // untuk SPl
    Matrix M2 = new Matrix(); // menampung hasil
    Matrix fgs = new Matrix (); // fgs, A, B semua buat transisi matriks interpolasi
    Matrix A = new Matrix ();
    Matrix B = new Matrix ();
    boolean X;
    double Xsoal;//X inputan untuk interpolasi
    int countrow0; //var untuk menhitung banyak baris yang semuanya 0
    //

    public void start(){
        X = true;
        int row,col,derajat; //derajat untuk interpolasi
        int i,j;
        Scanner in = new Scanner (System.in);

        main1: while(true){
            System.out.println(
                "\n"+
                "MENU\n"+
                "1. Sistem Persamaan Linier\n"+
                "2. Interpolasi Polinom\n"+
                "3. Input SPL dari file text\n"+
                "4. Interpolasi dari file text\n"+
                "0. Keluar\n\n"+
                "Input : "
            );

            int pilihan = in.nextInt(); //baca pilihan
            in.nextLine();
            Scanner scan = new Scanner(System.in);
            System.out.println("\n");

            //

            switch (pilihan){
                case 1:
                    System.out.println("Masukkan input matriks SPL");
                    M1.isiMatrix();
                    System.out.println("Masukkan input hasil tiap SPL");
                    M2.isiMatrix(M1.row,1);

                    X = false;
                    break main1;
                case 2:
                  //Scanner scan = new Scanner(System.in);
                    System.out.print("Masukkan derajat interpolasi");
                
                    derajat = scan.nextInt();
                    Matrix.interpolasi(derajat, M1, M2);
                    System.out.print("Masukan X yang ingin ditaksir nilai fungsinya : ");
                    Xsoal = scan.nextInt();
                    System.out.println();
                    
                    X = false;
                    break main1;
                case 3:
                    M1.isiMatrixEx(M2);
                    X = false;
                    break main1;
                case 4:
                    A.isiMatrixEx(B);
                    for ( i = 1; i <=A.row; i++) {
                    	fgs.matrix[i][1] = A.Elmt(i,1);
                    	fgs.matrix[i][2] = B.Elmt (i,1);
                    }
                    fgs.row = A.row;
                    fgs.col = A.col;
                    interpolasiEx(fgs, M1, M2,A.row-1);
                    System.out.print("Masukan X yang ingin ditaksir nilai fungsinya : ");
                    Xsoal = scan.nextInt();
                    X = false;
                    
                    break main1;
                case 0:
                    System.out.println("Keluar dari program");
                        System.exit(1); //keluar system
                    default:
                        System.out.println("Input salah, ulangi");
            }
        }

        main2: while(true){
            System.out.println("\n");
            System.out.println("Pilih metode");
            System.out.println(
                "1. Metode eliminasi Gauss\n"+
                "2. Metode eliminasi Gauss-Jordan\n"+
                "9. Ulang input\n"+ //in case salah input
                "0. Keluar\n"+
                "Input : "
            );

            int pilihan = in.nextInt();
            in.nextLine();

            switch (pilihan){
                case 1:
                	Echelon(M1,M2);
                    for (i=1; i<=M1.row; i++) {
                        for (j=1; j<=M1.col;j++){
                            System.out.print (M1.matrix[i][j]);
                        }
                        System.out.print (M2.matrix[i][1]);
                        System.out.println ();
                    }
                	if (M1.SolX!=4) {
	                	determineSolx(M1,M2,countrow0);
	                	SolusiSPL(M1.SolX,countrow0,M1,M2);
                	}else {//M1.SolX==4
                		showPolGauss(M1,M2,Xsoal);
                	}
	            
                	System.out.println();
                	break main2;
                case 2:
                    solve(M1,M2);
                    if (M1.SolX!=4) {
	                	determineSolx(M1,M2,countrow0);
	                	SolusiSPL(M1.SolX,countrow0,M1,M2);
                	}else {//M1.SolX==4
                		showPolGauss(M1,M2,Xsoal);
                	}
                    System.out.println();
                    break main2;
                    
                case 9:
                    System.out.println(
                        "Kembali ke menu awal\n"+
                        "--------------------\n"
                    );
                    X = true;
                    
                    start(); //balik ke atas
                    break main2;
                case 0:
                    System.out.println("Keluar dari program");
                        System.exit(1);
                    default :
                        System.out.println("Input salah, ulangi");
                        break;
            }
        }
    }

    public static void main(String [] args){
        Menu menu = new Menu();
        menu.start();
    }
}