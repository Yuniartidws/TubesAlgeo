/* ----- TUBES ALGEO -------
	Nira Rizki Ramadhani / 13516018 / K03
	Haifa Fadhila Ilma / 13516076 / K01
	Nadija Herdwina / 13516130 / K01
*/

import java.io.*;
import java.util.*;
import java.lang.*;
import java.lang.Math;

public class MatriksAlgeo{
	
	//Variabel global
	private double[][] mat = new double[50][50];
	private int neffbrs;
	private int neffkol;
	double divider;
	double factor;
	double[] X;
	double[] Y;
	
	final int ADAsol = 1;
	final int  INFsol = 0;
	final int  NOsol = -1;

	private String[] hasil = new String[50];
	int neffhsl;

//	private ArrayList<Integer> parameters = new ArrayList<>(); blm bisa yg infinite hehe
	private int isAdaSolusi = ADAsol;
	Scanner input = new Scanner(System.in);
	
	//Konstruktor
	public MatriksAlgeo(){
		for (int i = 0; i < 50; i++){
			for (int j = 0; j < 50; j++){
				mat[i][j] = 0;
			}
		}
	}
	
	public void isiMatriks(){
        System.out.println("------PENGISIAN AUGMENTED MATRIKS------");
        System.out.print("Baris: ");		
		neffbrs = input.nextInt();
        System.out.print("Kolom: ");
		neffkol = input.nextInt();
		
        for(int i=0; i<neffbrs; i++){
            System.out.println("Masukkan persamaan "+(i+1)+" :");
            for(int j=0; j<neffkol; j++){
				if (j!=neffkol-1){
					System.out.print("Masukkan koefisien x"+(j+1)+" : ");
					mat[i][j]=input.nextDouble();
				}
				else{
					System.out.print("Masukkan konstanta persamaan "+(i+1)+" : ");
					mat[i][j]=input.nextDouble();
				}
            }
        }
		System.out.println();
    }
	
	public void tulisMatriks(){
        System.out.println("Matriks Augmentednya adalah");
		
        for( int i=0; i<neffbrs; i++){
			System.out.print("| ");
            for(int j=0; j<neffkol; j++){
				if (j!=neffkol-1)
					System.out.print(String.format("%.2f ", mat[i][j]));
				else
					System.out.print(String.format("| %.2f |", mat[i][j]));
            }
			System.out.println();
        }
		System.out.println();
    }
	void swap(double[][] mat, int i, int k, int j){
		double temp;
		for(int q=j; q<=neffkol; q++){
			temp = mat[i][q];
			mat[i][q] = mat[k][q];
			mat[k][q] = temp;
		}
	}
	
    void divide(double[][] mat, int i, int j){
		for(int q=j+1; q<neffkol; q++) 
			mat[i][q] /= mat[i][j];
		mat[i][j] = 1;
   }
   
	void eliminate(double[][] mat, int i, int j){
		for(int p=0; p<neffbrs; p++){
			if( p!=i && mat[p][j]!=0 ){
				for(int q=j+1; q<neffkol; q++){
					mat[p][q] -= mat[p][j]*mat[i][q];
				}
				mat[p][j] = 0;
			}
		}
	}

	void gaussJordanBismillah(){
		int i = 0;
		int j = 0;
			while( i<neffbrs && j<neffkol ){

			int k = i;
			while( k<neffbrs && mat[k][j]==0 ) k++;
			if( k<neffbrs ){
				if( k!=i ) {
					swap(mat, i, k, j);
				}
				if( mat[i][j]!=1 ){
					divide(mat, i, j);
				}
				eliminate(mat, i, j);
				i++;
			}
			j++;
		}
	}
	
	public int countLines(String filename) throws IOException {
    InputStream is = new BufferedInputStream(new FileInputStream(filename));
    try {
        byte[] c = new byte[1024];
        int count = 0;
        int readChars = 0;
        boolean empty = true;
        while ((readChars = is.read(c)) != -1) {
            empty = false;
            for (int i = 0; i < readChars; ++i) {
                if (c[i] == '\n') {
                    ++count;
                }
            }
        }
        return (count == 0 && !empty) ? 1 : count;
    } finally {
        is.close();
    }
}

	public void  bacaFile() {
		int k=1;
		String array[] = new String[1000];
		String line;
		try{
			System.out.print("Masukkan nama file yang akan dibaca: ");
			String fileName = input.next();
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			neffbrs = countLines(fileName);
			
			System.out.println("Jumlah baris adalah " + neffbrs);
			while ((line = br.readLine()) != null)   {
				String[] kosong = line.split("\\s+");
				for (String part : kosong) {
					array[k]= part;
					k++;
				}
			}
			neffkol=k/neffbrs;
			k=1;
			System.out.println("Jumlah kolom adalah " + neffkol);
			for (int i=0; i<neffbrs;i++) {
				for(int j=0; j<neffkol;j++) {
					mat[i][j]= Double.parseDouble(array[k]);
					k++;
				}
			}
			br.close();	
        }
		catch(Exception e){
			System.out.println("Pembacaan file error.");                      
		}
	}
	
	
	public void tulisFile() {
			System.out.print("Masukan nama file yang akan ditulis: ");
			String namaFile = input.next();
			
			BufferedWriter bw = null;
			FileWriter fw = null;
			
			try {
					fw = new FileWriter(namaFile);
					bw = new BufferedWriter(fw);
					for (int i=0; i<neffbrs; i++) {
						for (int j=0;j<neffkol; j++) {
								bw.write(String.format("%.2f", mat[i][j]) + " ");
						}
						bw.write("\r\n");	
					}
					bw.write("\r\n");	
					for (int i =1; i<=neffhsl;i++) {
						bw.write(hasil[i] + " \r\n");
					}
					bw.close();
					fw.close();
			} catch (Exception e) {
				System.out.println("penulisan file error.");
			}
	}
	
	static double pangkat(double d, int t) {
		double hasil = 1;
		if (t==0) {
			return 1;
		}
		for (int i = 0; i<t; i++) {
			hasil *= d;
		}
		return hasil;
	}

    
    public void Interpolasi() {
		System.out.print("Masukkan jumlah titik: \n");
		Scanner scan = new Scanner(System.in);
		int titik = scan.nextInt  ();
		double[] X = new double [titik];
		double[] Y = new double [titik];
		
		neffbrs = titik;
		neffkol = titik+1;
		//memasukkan input x dan y(hasil)
		for (int i=0; i<titik; i++) {
			System.out.print("Masukkan nilai x" + i + " : ");
			X[i] = scan.nextDouble();
			System.out.println();
			for (int j=0; j< titik; j++) {
				mat[i][j] = pangkat(X[i],   j);
			}
	
			System.out.print("Masukkan nilai y" + i + " : ");
			Y[i] = scan.nextDouble();
			System.out.println();
			mat[i][titik] = Y[i];
		}
		System.out.print("Dengan metode Interpolasi Polinom: \n");
		tulisMatriks();
		System.out.println("Hasil matriks setelah menggunakan metode Gauss Jordan: \n");
		gaussJordanBismillah();
		tulisMatriks();
		hasilInterpolasi();
		
	}
	
	
		public void hasilInterpolasi(){
			gaussJordanBismillah();
			solutionValue();
			
			Scanner scan = new Scanner(System.in);
			if (isAdaSolusi==NOsol)
				{System.out.println("Tidak ada solusi untuk sistem persamaan diatas.");
			}else if (isAdaSolusi==ADAsol){
				int j = neffkol-1;
				for (int i=0; i<neffbrs; i++){
					System.out.println(String.format("a%d = %.2f",i,mat[i][j]));
				}
				System.out.print("Persamaannya adalah: \n");
				System.out.print("f(x) = ");
				for (int i=0; i<neffbrs; i++){
					if (i==0) {
						System.out.print(String.format(" %.2f ", mat[i][j]));
					} else {
					System.out.print(String.format(" %.2f x^%d ", mat[i][j], i));
					}
						if ((i != (neffbrs-1)) && (mat[i+1][j]) >= 0) {
							System.out.print("+");
							}
						if (i==neffbrs-1) {
							System.out.println();
							}
					}
				
				System.out.print("Apakah ingin memasukkan input x? (Y/N)\n");
				char inputx = input.next(".").charAt(0);
				if (inputx == 'Y') {
					double hasil = 0;
					double inp;
					System.out.print("Masukkan nilai x: \n");
					inp = scan.nextDouble();
					for (int i=0; i<neffbrs; i++) {
						hasil += (pangkat(inp, i) * (mat[i][j]));
					}
					System.out.print(String.format("f(x) = %.2f\n", hasil));
				
				} else if (inputx == 'N') {
						System.out.print("---Interpolasi Polinomial Telah Selesai---\n");
					}
			}
			else if (isAdaSolusi==INFsol)
				{cetakSolusiINF();}
		}
		
		void fungsiE () {
			Scanner scan = new Scanner(System.in);
			double a, b; //selang
			//int n; //jumlah titik
			double x;
			double y;
			int n;

			System.out.println("Menghampiri fungsi dengan polinom interpolasi pada batas [a,b]");
			System.out.println("Masukkan a: ");
			a = scan.nextDouble();
			System.out.println("Masukkan b: ");
			b = scan.nextDouble();
			System.out.println("Masukkan n: ");
			n = scan.nextInt();

			double h;
			h = (b-a)/((double)n);
			neffbrs = n;
			neffkol = n + 1;
			
			for (int i = 0; i < neffbrs; i++) {
				x = a+(h * (double)i);
				y = (Math.exp(-x)) / (1 + Math.pow(x,2) + Math.sqrt(x));
				for (int j = 0; j < neffkol-1; j++) {
             	   mat[i][neffkol-2-j] = pangkat(x, j);
            	mat[i][neffkol-1] = y;
            	}

			}

			System.out.println("================HASIL================");
			gaussJordanBismillah();
			tulisMatriks();
		}
		
		void MatriksHilbert () {
			System.out.println("===========MATRIKS HILBERT============");
			Scanner scan = new Scanner(System.in);
			System.out.print("Masukkan nilai n: ");
			int n =  scan.nextInt();
			neffbrs = n;
			neffkol = n+1;
			
			for (int i = 0; i< neffbrs; i++) {
				for (int j= 0; j< neffkol-1; j++) {
					mat[i][j] = ((float)1/((float)(i+1)+(j+1)-1));
				}
				mat[i][neffkol-1] = 1.00;
			}
	
			System.out.print("Matriks Hilbert: \n");
			tulisMatriks();
    
			System.out.print("Akan dilakukan perkalian dengan transpose Matriks B = (1, 1, 1, ..., 1)\n");
			System.out.println("HX=B, akan dihasilkan: ");
			gaussJordanBismillah();
			tulisMatriks();	
			hasilSolusi();
		}
		
		// MENCARI JENIS SOLUSI MATRIKS
		void solutionValue(){
			int j = 0;
			int countzerow = 0;
	
			for (int i=neffbrs-1; i>=0; i--) {
				j=0;
				while ((j<neffkol-1)&&(mat[i][j]==0))
					j++;
				if (j==neffkol-1){
					if (mat[i][j] == 0)
						countzerow++;	
					else{
						isAdaSolusi = NOsol;
						break;
					}
				}
			}
					
			if (isAdaSolusi != NOsol){
			if ((neffbrs-countzerow) < (neffkol-1))
				isAdaSolusi = INFsol;
			else
				neffbrs -= countzerow;
			}
		}
   
	boolean sebarisNol(int i){
		int j=0;
		boolean sNol = false;
		while (j<neffkol-1 && mat[i][j]==0){
			j++;
		}
		if (mat[i][j]==0){
			sNol = true;
		}
		return sNol;
	}

	int firstNonZero(int brs){
		int kol = -1;
		for (int j=0; j<neffkol-1; j++){
			if (mat[brs][j]!=0){
				kol = j;
				break;
			}
			else
				kol = -1;
		}
		return kol;
	}
	
	public void cetakSolusiINF(){
		String currentkata = "";
		int[] unwrittenX = new int[50];
		int neffX = 0;
		neffhsl = 0;
		int[] ParX= new int[50];
		int neffpar = 0;
		int maxPar = 0;
		int countpar = 0;
		char[] par = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v'};
		for (int i=0; i<neffbrs; i++) {
			int kol = firstNonZero(i);
			System.out.print("x"+(kol+1)+" = ");
			currentkata += String.format("x"+(kol+1)+" = ");
			if (!((mat[i][neffkol-1]< 0.01) && (mat[i][neffkol-1]>9.0E-25))){
				System.out.print(String.format("%.2f",mat[i][neffkol-1]));
				currentkata +=String.format("%.2f",mat[i][neffkol-1]);
			}
			for (int j=kol+1; j<neffkol-1; j++){
				if (mat[i][j]!=0){
					if (mat[i][j]<0){
						mat[i][j]*=-1;
						if (String.format("%.2f",mat[i][j])!="0.00"){
							System.out.print(String.format(" + %.2f", mat[i][j])+par[j]);
							currentkata += String.format(" + %.2f", mat[i][j])+par[j];
							neffpar++;
							ParX[neffpar] = j;
						}
					}
					else if (mat[i][j]>0){
						if (String.format("%.2f",mat[i][j])!="0.00"){
							System.out.print(String.format(" - %.2f", mat[i][j])+par[j]);
							currentkata += String.format(" - %.2f", mat[i][j])+par[j];
							neffpar++;
							ParX[neffpar] = j;
						}
					}
				}
			}
			neffhsl++;
			hasil[neffhsl] = currentkata;
			currentkata = "";
			System.out.println();
		}
		for (int p=1; p<neffkol-1; p++){
			if (firstNonZero(p)!=p){
				neffX++;
				unwrittenX[neffX] = p;
				System.out.println("x"+(unwrittenX[neffX]+1)+" = "+par[ParX[neffX]]);
				neffhsl++;
				currentkata = "x"+(unwrittenX[neffX]+1)+" = "+par[ParX[neffX]];
				hasil[neffhsl] = currentkata;
			}
		}		
	}

 
   public void hasilSolusi(){
		gaussJordanBismillah();
		solutionValue();
		
		if (isAdaSolusi==NOsol)
			System.out.println("Tidak ada solusi untuk sistem persamaan diatas.");
		else if (isAdaSolusi==ADAsol){
			int j = neffkol-1;
			for (int i=0; i<neffbrs; i++){
				System.out.println(String.format("x%d = %.2f",i+1,mat[i][j]));
				neffhsl++;
				hasil[neffhsl] = String.format("x%d = %.2f",i+1,mat[i][j]);
			}
		}
		else if (isAdaSolusi==INFsol)
			cetakSolusiINF();
    }
	
	public static void main(String args[]){
		MatriksAlgeo coba=new MatriksAlgeo();
		System.out.println("-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_WELCOME TO TUGAS BESAR 1 ALJABAR GEOMETRI_-_-_-_-_-_-_-_-_-_-_-_-");
		System.out.println();
		System.out.println("*****************A program by***************");
		System.out.println("Haifa Fadhila Ilma	| 13516076 | 	K01");
		System.out.println("Nadija Herdwina P. S.	| 13516130 | 	K01");
		System.out.println("Nira Rizki Ramadhani 	| 13516018 | 	K03");
		System.out.println();
		System.out.println("Program ini akan melakukan pemrosesan sistem persamaan lanjar dengan menggunakan Matriks.");
		System.out.println();
		char ulang;
		do{
			System.out.println("====================MENU===================");
			System.out.println("1. Pemrosesan persamaan dari input pengguna");
			System.out.println("2. Pemrosesan persamaan dari file eksternal");
			System.out.println("3. Menyelesaikan soal:");
			System.out.println(">a. Polinom Interpolasi");
			System.out.println(">b. Matriks Hilbert");
			System.out.println(">c. Fungsi E");
			Scanner input = new Scanner(System.in);
			int menu;
			do{
				System.out.print(">Masukkan pilihan menu: ");
				menu = input.nextInt();
				if ((menu!=1)&&(menu!=2)&&(menu!=3))
					System.out.print("Invalid. ");
			} while ((menu!=1)&&(menu!=2)&&(menu!=3));
		
			if (menu == 1){
				coba.isiMatriks();
				coba.tulisMatriks();
				coba.hasilSolusi();
				coba.tulisMatriks();
			}
			else if (menu == 2){
				coba.bacaFile();
				coba.hasilSolusi();
				coba.tulisMatriks();
			}
			else if (menu == 3){
				System.out.print(">a/b/c: ");
				char s = input.next(".").charAt(0);
				if (s=='a') {
				coba.Interpolasi();
				}else if (s=='b') {
					coba.MatriksHilbert();
				}else if (s=='c') {
					coba.fungsiE();
					coba.hasilSolusi();
				}
			}
			
			System.out.println(">Simpan ke file eksternal? (Y/N)");
			char simpanfile = input.next(".").charAt(0);
			if (simpanfile == 'Y' || simpanfile== 'y'){
				coba.tulisFile();
			}
			
			System.out.println(">Ulangi program? (Y/N)");
			ulang = input.next(".").charAt(0);
		}while (ulang == 'Y' || ulang == 'y');
	}
}
