import java.io.*;
import java.util.*;

public class Matrix{

    //Attributes
    int row,col,sol;
    int SolX; 
    double [][] matrix;
    String format = "%.15f";


    //Konstruktor
    public Matrix(){

        this.row = 10;
        this.col = 10;
        this.sol = 0;
        matrix = new double [10][10];
    }

    public Matrix(int row, int col){
        this.row = row;
        this.col = col;
        this.sol = 0;
        matrix = new double[row+1][col+1];
    }

    public Matrix(Matrix A){

        this.row = A.row;
        this.col = A.col;
        this.SolX = A.SolX;
        this.matrix = A.matrix;
    }

    //Selektor
    public double Elmt (int row, int col){
        return this.matrix[row][col];
    }


    //main//

    public void showAug(Matrix X, BufferedWriter writer){ //mengeluarkan matrix augmented

        String input;

        for(int i = 1; i <= this.row; i++){
            for(int j = 1; j <= this.col; j++) {

                input = String.format(format, this.Elmt(i,j));
                save(input,writer);
            }
            input = String.format(format, X.Elmt(i,1));
            save(input,writer);
            save("\n",writer);
        }

    }
    
    void showPolGauss (Matrix A, Matrix B,double Xsoal) {
    	/*kamus lokal*/
    	BufferedWriter writer = null;
        //BIKIN FILE BUAT SAVE KE LUAR
        File file = new File("Solusi.txt"); //namanya solusi.txt
        
        // buka file
        try{
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
        }
        catch(FileNotFoundException e){
            System.err.println("Exception : " + e.toString());
        }
        
        int i,j;
        double[] hasil= new double [101];
        double Y;
        String input;
        String polinom;
        
        for (i=1;i<=A.col;i++) {
			hasil[i]=B.Elmt(i, 1);
		}
		hasil[i]=0;
		for (i=A.col;i>=1;i--) {
			for (j=A.col;j>=i+1;j--) {
				hasil[i] = hasil [i] - A.Elmt(i, j)* hasil [j];
			}
		}
		polinom =" ";
		if (hasil[1]!=0) {
			polinom = Double.toString (hasil[1]);
		}
		for(i=2;i<=A.col;i++) {
			if(hasil[i]>0) {
				polinom= polinom + " + " + Double.toString(hasil[i]) +"X^" +Integer.toString(i-1);
			}else if(hasil[i]<0) {
				polinom=polinom+Double.toString(hasil[i])+"X^"+Integer.toString(i-1);
			}
		}
		input = String.format("%s", polinom);
		save(input,writer);
		save("\n",writer);
    	Y = 0;
    	for (i= 1; i<=A.col; i++) {
    		Y =Y+ (hasil[i]* (Math.pow(Xsoal, i-1)));
    	}
    	input = String.format("Nilai fungsi untuk X = "+Xsoal+ " : "+Y);
    	save (input,writer);
    	save("\n",writer);
    	
    	try {
            if(writer != null){
                writer.close();
            }
        }
        catch(IOException e){
            System.err.println("Exception : " + e.toString());
        }
    }

    //Matrix input
    void isiMatrix(){

        int i,j;
        double isi;
        Scanner scan = new Scanner(System.in);

        System.out.print("Baris : ");
        int row = scan.nextInt();
        System.out.print("Kolom : ");
        int col = scan.nextInt();

        this.row = row;
        this.col = col;
        this.matrix = new double[this.row + 1][this.col + 1];

        //isi matrix
        for(i = 1; i <= this.row; i++ ){
            for(j = 1; j <= this.col; j++ ){
                System.out.print("Input Matrix [" + i + "][" + j + "] : ");
                isi = scan.nextDouble();
                this.matrix[i][j] = isi;
            }
        }
    }

    void isiMatrix(int row, int col){

        int i,j;
        double isi;
        Scanner scan = new Scanner(System.in);

        this.row = row;
        this.col = col;
        this.matrix = new double[this.row + 1][this.col + 1];
        for(i = 1; i <= this.row; i++ ){
            for(j = 1; j <= this.col; j++ ){
                System.out.print("Input Matrix [" + i + "][" + j + "] : ");
                isi = scan.nextDouble();
                this.matrix[i][j] = isi;
            }
        }
    }

    public int countBar(){
    try {
        FileReader filer = new FileReader ("UjiMatrix.txt");
        BufferedReader fileks = new BufferedReader (filer);
        String text;
        int Bar;
        text = fileks.readLine ();
        Bar =0;
        while (text != null) {
            Bar = Bar +1;
            text = fileks.readLine ();
        }
        fileks.close();
        return Bar;
    } catch(Exception e){
        System.exit(0);
        return 0;
        }
    }

    public int countCol(){
    try{
        FileReader filec = new FileReader ("UjiMatrix.txt");
        BufferedReader filecs = new BufferedReader (filec);
        String text;
        int Col;
        text = filecs.readLine ();
        String [] textarr = text.split (" ");
        Col = textarr.length;
        filecs.close();
        return Col;
    } catch(Exception e){
        System.exit(0);
        return 0;
        }
    }

    void isiMatrixEx (Matrix M2) {
        try {
            int Col;
            int Bar;
            int i,j;
            Double x;
            File file = new File ("UjiMatrix.txt");
            Scanner scanner = new Scanner (file);
            Bar = countBar(); 
            Col = countCol ();
            this.col = Col-1;
            this.row = Bar;
            M2.col =1;
            M2.row = Bar;
            for (i=1; i <=Bar; i++) {
                for (j=1; j <= Col; j++) {
                    x = scanner.nextDouble();
                    if (j==Col) {
                        M2.matrix[i][1] = x;
                    } else {
                        this.matrix[i][j] = x;
                    }
                }
                System.out.println();
            }
        } catch (FileNotFoundException e){
            //file external gaada
            //System.err.println("Exception : " + e.toString());
        }
    }

    /*void isiMatrixEx(Matrix M2){
        //isi matrix dari file external
        //load dlu
        File file = new File("UjiMatrix.txt");
        BufferedReader reader = null; //declare object reader / byte

        try{
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            int i = 1,j; 
            String [] numbers = null; //nampung angka
            String line = null;

            //Mulai baca file
            while((line = reader.readLine()) != null){
                //baca baris
                if(line.equals("ROW")){ //mark
                    
                    line = reader.readLine(); //baca 1 baris
                    numbers = line.split(" "); //perintah buat buang space
                    this.row = Integer.valueOf(numbers[0]); //masukin angka ke baris object

                } else if (line.equals("COL")){ //mark, baca kolom

                    line = reader.readLine();
                    numbers = line.split(" ");
                    this.col = Integer.valueOf(numbers[0]);

                } else if (line.equals("MAT")){ //mark, baca isi matrix

                    this.matrix = new double[this.row+1][this.col+1]; //buat matriks
                    M2.matrix = new double[this.row+1][2];
                    while((line = reader.readLine()) != null){ //baca file sampai habis

                        numbers = line.split(" ");
                        for(j = 1; j <=  this.col; j++){
                            //assign isi matrix ke program
                            this.matrix[i][j] = Double.parseDouble(numbers[j-1].trim());
                        }

                        M2.matrix[i][1] = Double.parseDouble(numbers[j-1].trim());
                        i++;
                    }
                    M2.row = this.row;
                    M2.col = 1;
                }
            }
        }
        catch (FileNotFoundException e){
            //file external gaada
            System.err.println("Exception : " + e.toString());
        }
        catch (IOException e){
            System.err.println("Exception : " + e.toString());
        }

        finally{
            if (reader != null){
                try{
                    reader.close();
                }
                catch(IOException e){
                    System.err.println("Exception : " + e.toString());
                }
            }
        }
    }*/

   
    private void swap(int i, int j){ 
        double[] temp;

        temp = matrix[i];
        matrix[i] = matrix[j];
        matrix[j] = temp;
    }

    public static void interpolasi(int deg, Matrix M1, Matrix M2){

        Matrix fgs = new Matrix (deg+1, deg+1);
        Scanner scan = new Scanner(System.in);
        double isi;
        M1.SolX = 4;
        M1.row=deg+1;
        M2.row=deg+1;
        M1.col=deg+1;
        M2.col=deg+1;
        for(int i = 1; i <=deg+1; i++ ){
            System.out.print("Input titik X ke - "+ i +" : ");
            isi = scan.nextDouble();
            fgs.matrix[i][1] = isi;
            System.out.print("Input titik Y ke - "+ i +" : ");
            isi = scan.nextDouble();
            fgs.matrix[i][2]= isi;
        }
        
        for (int i = 1; i <= deg+1; i++){
            M1.matrix[i][1] = 1; //kolom pertama 1 semua
            M1.matrix[i][2] = fgs.Elmt(i,1);
            for (int j = 3; j <= deg+1; j++){
                M1.matrix[i][j] = java.lang.Math.pow(M1.Elmt(i,2),j-1);
            }
        }
        for (int i = 1; i <= deg+1; i ++) {
        	M2.matrix[i][1]=fgs.Elmt(i,2);
        }
    }

    public void interpolasiEx (Matrix fgs, Matrix M1,Matrix M2, int deg) {
        M1.SolX = 4;
        M1.row = deg+1;
        M1.col = deg+1;
        M2.row = deg+1;
        M2.col = deg+1;
    	for (int i = 1; i <= deg+1; i++){
            M1.matrix[i][1] = 1; //kolom pertama 1 semua
            M1.matrix[i][2] = fgs.Elmt(i,1);

            for (int j = 3; j <= deg+1; j++){
                M1.matrix[i][j] = java.lang.Math.pow(M1.Elmt(i,2),j-1); //baris ini terima dulu deh :v
            }
        }
        for (int i = 1; i <= deg+1; i ++) {
        	M2.matrix[i][1]=fgs.Elmt(i,2);
        }
    }
    
    public void solve (Matrix A, Matrix B) {
    	//Kamus
    	boolean found;
    	int i,j,k,m;
    	double multiplier;
    	//Algoritma
    	Echelon (A,B);
    	for (i=A.row; i>=1;i--) {
    		found =false;
			k=1;
			while ((k<=A.col) && (found==false)) {
				if (A.Elmt(i, k) == 0) {
					k++;
				} else {
					found = true;
				}
			}if(found) {
				for (j=i-1; j>=1;j--) {
					multiplier = A.Elmt(j, k);
					for (m=k;m<=A.col;m++) {
						A.matrix[j][m]= A.Elmt(j,m)- (multiplier* A.Elmt(i, m));
					}
					B.matrix[j][1]=B.Elmt(j,1) - (multiplier* B.Elmt(i,1));
				}
			}
    	}
		
    }
  
    static void save(String input, BufferedWriter writer){ //buat ngesave ke file luar

        try{
            if (input == "\n"){
                System.out.println();
                writer.newLine();
            }
            else{
                System.out.print(input);
                writer.write(input);
            }
        }

        catch (FileNotFoundException e){
            System.err.println("Exception : " + e.toString());
        }

        catch (IOException e){
            System.err.println("Exception :" + e.toString());
        }
    }
    
    static void determineSolx (Matrix A, Matrix B, int countrow0) {
        /*untuk menentukan banyaknya solusi matriks sebelum gauss/gauss jordan*/
    	/*menghitung banyak baris yang elemennya 0 semua*/
    	
    	boolean sol3;//true jika matriks tidak punya solusi
    	boolean colzero;//true jika ada kolom yang 0 semua
    	//boolean rowzero;//true jika ada baris yang 0 semua
    	int i,j;
    	A.SolX=1; //inisialisasi awal matriks punya 1 solusi
    	countrow0=0;
    	//if (A.row == A.col) {
    	i=1;
    	sol3=false;
    	while (i<=A.row && !sol3) { //periksa setiap baris
    		j=1;
    		sol3=true;
    		while (j<=A.col && sol3) {
    			//periksa setiap elemen sebaris (kecuali elemen paling akhir)
				if (A.Elmt(i, j)!=0) {
					sol3=false;
				} else {
					j++;
				}
    		}
    		//jika semua elemen sebaris (kecuali elemen terakhir) 0 semua, cek elemen terakhir
			if (sol3) {
				if (B.Elmt(i,1)==0) {
				/*kalau elemen terakhir 0, punya banyak solusi,
				 * tapi masih dicek apakah di baris lain memenuhi kondisi tidak ada solusi*/
					sol3=false;
					A.SolX=2;
					countrow0 = countrow0+1;
				}else {
        		/*kalau elemen terakhir bukan 0, sudah pasti tidak punya solusi,
        		 * baris lain tidak perlu dicek*/
        			A.SolX=3;
				}
			}
    		i++;
    	}
    	if (!sol3) {
	    	if (A.row<A.col) {
	    		A.SolX=2;
	    	}else if (A.row>A.col) {
	    		if ((A.row-A.col) >= countrow0) {
	    			A.SolX=1;
	    		}
	    	}	
    	}
    	if(A.SolX==1) {//jika SolX masih 1, periksa apakah ada kolom yang 0 semua
    		colzero=false;
    		j=1;
    		while (j<=A.col && !colzero) {
    			colzero=true;
    			i=1;
    			while (i<=A.row && colzero) {
    				if (A.Elmt(i, j)!=0) {
    					colzero=false;
    				}else {
    					i++;
    				}
    			}
    			j++;
    		}
    		if (colzero) {
    			A.SolX=2;
    		}
    	}
    }
    
    static void Echelon (Matrix A,Matrix B) {
    	int i,j,k,m;
    	boolean solved;
    	boolean zero;
    	boolean found;
    	double divider,multiplier;
    	
    	solved=false;
    	zero=true;
    	i=1;
    	j=1;
    	while (!solved) {
    		if (A.Elmt(i,j)!=0.0) {
    			zero=false;
    		}else {//A.Elmt(i,j)==0
    			found=false;
    			k=i+1;
    			while (!found && k<=A.row) {
    				if (A.Elmt(k, j)!=0.0) {
    					found=true;
    				}else {
    					k++;
    				}
    			}
    			if (found) {
    				zero=false;
    				A.swap(k,i);
    				B.swap(k,i);
    			}
    		}
    		if (zero) {
    			j++;
    		} else {//!zero
    			//lakukan operasi
    			divider = 1/A.Elmt(i, j);
				for (k=j;k<=A.col;k++) {
					A.matrix[i][k] = divider *A.Elmt(i, k);
					if (A.matrix[i][k]==-0) {
						A.matrix[i][k]=A.matrix[i][k]*-1;
					}
				}
				B.matrix[i][1]=divider * B.matrix[i][1];
				if (B.matrix[i][1]==-0) {
					B.matrix[i][1]=B.matrix[i][1]*-1;
				}
				if (i<A.row) {
					for (k=i+1;k<=A.row;k++) {
						multiplier = A.Elmt(k,j);
						for (m=j; m<=A.col;m++) {
							A.matrix[k][m]=A.matrix[k][m]-multiplier*A.Elmt(i, m);
							if (A.matrix[k][m]==-0) {
								A.matrix[k][m]=A.matrix[k][m]*-1;
							}
						}
						B.matrix[k][1] = B.matrix[k][1]-multiplier*B.Elmt(i,1);
						if (B.matrix[k][1]==-0) {
							B.matrix[k][1]=B.matrix[k][1]*-1;
						}
					}
				}
    			i++;
    			j++;
    			zero=true;
    		}
    		if (i>A.row || j>A.col) {
    			solved=true;
    		}
    	}
    	
    }
    
    static void SolusiSPL(int SolX, int countrow0, Matrix A, Matrix B) {
    	/*kamus lokal*/
    	
    	 BufferedWriter writer = null;
         //BIKIN FILE BUAT SAVE KE LUAR
         File file = new File("Solusi.txt"); //namanya solusi.txt
         
         // buka file
         try{
             writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
         }
         catch(FileNotFoundException e){
             System.err.println("Exception : " + e.toString());
         }
    	
    	//untuk menampung hasil solusi banyak
    	String [] hasilstring=new String [101];//asumsi maksimum 100 persamaan
    	//untuk menampung hasil solusi tunggal
    	double[] hasil= new double [101];//asumsi solusi maksimal 100 buah
    	double [] hasilint = new double [101];
    	int i;
    	int j;
    	int k;
    	double x;
    	String input;
    	boolean found;
    	String[] charpara = new String [101];
    	int countc;
    	
    	/*algoritma*/
    	if (SolX==1) {
    		for (i=1;i<=A.col;i++) {
    			hasil[i]=B.Elmt(i, 1);
    		}
    		hasil[i]=0;
			for (i=A.col;i>=1;i--) {
				for (j=A.col;j>=i+1;j--) {
					hasil[i] = hasil [i] - A.Elmt(i, j)* hasil [j];
				}
    		}
			for(i=1;i<=A.col;i++) {
				input = String.format("X" +Integer.toString(i)+ " = " +Double.toString(hasil[i]));
				save(input,writer);
				save("\n",writer);
				//System.out.println("X"+i+" = "+hasil[i]);
			}
			
    	}else if(SolX==2) {
    		A.row = A.row - countrow0;
    		charpara[1]= "a";
    		countc =1;
    		for (i=2; i<=A.col; i++) {
    			charpara[i]= (charpara[i-1]) + 'a';
    		}
    		for (i=1;i<=A.col;i++) {
    			hasilint[i] = -9999; //Asumsi angka -9999 tidak akan menjadi nilai fungsi suatu persamaan
    			hasilstring[i]= " ";
    		}
    		for (i=A.row; i>=1; i--) {
    			found =false;
    			k=1;
    			while ((k<=A.col) && (found==false)) {
    				if (A.Elmt(i, k) == 0) {
    					k++;
    				} else {
    					found = true;
    				}
    			}
    			hasilint[k] = B.Elmt(i,1);
    			for (j=A.col; j>k; j--) {
    				if (hasilint [j] != -9999) {
    					x = A.Elmt(i,j) * hasilint[j];
    					hasilint[k] = hasilint[k] - x;
    				} else {
    					if (hasilstring[j]== " ") {
    					hasilstring[k]= (hasilstring[k] + " - " + Double.toString(A.Elmt(i,j)) + charpara[countc] +" ");
    					hasilstring[j]=charpara[countc];
    					countc = countc +1;
    					} else {
    						hasilstring[k]= (hasilstring[k] + " - " + Double.toString(A.Elmt(i,j)) + hasilstring[j] +" ");
    					}
    				}
    			}
    			hasilstring[k]= (hasilstring[k]+ " + (" + Double.toString(hasilint[k]) + ") ");
    			
    		}
    		for(i=1;i<=A.col;i++) {
    			input= String.format("X%d = %s", i,hasilstring[i]);
    			save(input,writer);
    			save("\n",writer);
       		}
    		/*if (countrow0>=1) {
    			hasilstring[A.row]="a";
    			j=A.row-1;
    			for (i=1;i<countrow0;i++) {
    				hasilstring[j]=hasilstring[j+1]+1;
    				j--;
    			}
    		}
    		for(i=1;i<=A.row-countrow0;i++) {
    			hasilstring[i] = Double.toString(B.Elmt(i,1));
    		}
    		for (i=A.row-countrow0;i>=1;i--) {
    			for (j=A.col;j>=i+1;j++) {
    				hasilstring[i]=hasilstring[i]+ "(-)" + Double.toString(A.Elmt(i,j))+hasilstring[j]+" ";
    			}
    		}
    		for(i=1;i<=A.row-countrow0;i++) {
    			input= String.format("X%d = %s", i,hasilstring[i]);
    			save(input,writer);
    			save("\n",writer);
    			//System.out.println("X"+i+" = "+hasilstring[i]);
    		}*/
    	}else if (SolX==3) {
    		//System.out.println("Solusi tidak ada.");
    		input = String.format("%s", "Solusi tidak ada");
    		save(input,writer);
    		save("\n",writer);
    	}  		
    	try {
            if(writer != null){
                writer.close();
            }
        }
        catch(IOException e){
            System.err.println("Exception : " + e.toString());
        }
    }
}
