import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;


public class MiniMaxOpeningBlack {

	/**
	 * @param args
	 */

	private static int positions_evaluated=0;
	private static int  minimax_estimate=0;
	
	
	public ArrayList generateAdd(char[] b) {
		ArrayList<char[]> gaList = new ArrayList<char[]>();
		//this.board=b.clone();
		char copyBoard[];
		for(int i=0;i<b.length;i++){
			if(b[i]=='x'){
				copyBoard = b.clone();
				copyBoard[i]='W';
				if(closeMill(i,copyBoard)){
					gaList = generateRemove(copyBoard, gaList); 
				}
				else {
					gaList.add(copyBoard);
				}
			}
		}
		return gaList;
	}	
	
	public boolean closeMill(int loc, char[] copyBoard){
		char c = copyBoard[loc];
		if(c=='W' || c=='B'){
			switch(loc) {
			case 0 : if((copyBoard[6]==c && copyBoard[18]==c)||(copyBoard[2]==c && copyBoard[4]==c))
						return true;
					else
						return false;//a0
			case 6: if((copyBoard[7]==c && copyBoard[8]==c)||(copyBoard[0]==c && copyBoard[18]==c))
						return true;
					else
						return false;//a3
			case 18: if((copyBoard[0]==c && copyBoard[6]==c)||(copyBoard[19]==c && copyBoard[20]==c))
						return true;
					else
						return false;//a6
			case 2: if((copyBoard[0]==c && copyBoard[4]==c)||(copyBoard[7]==c && copyBoard[15]==c))
						return true;
					else
						return false;//b1
			case 7: if((copyBoard[6]==c && copyBoard[8]==c)||(copyBoard[2]==c && copyBoard[15]==c))
						return true;
					else
						return false;//b3
			case 15: if((copyBoard[7]==c && copyBoard[2]==c)||(copyBoard[16]==c && copyBoard[17]==c))
						return true;
					else
						return false;//b5
			case 4: if((copyBoard[0]==c && copyBoard[2]==c)||(copyBoard[8]==c && copyBoard[12]==c))
						return true;
					else
						return false;//c2
			case 8: if((copyBoard[6]==c && copyBoard[7]==c)||(copyBoard[4]==c && copyBoard[12]==c))
						return true;
					else
						return false;//c3
			case 12: if((copyBoard[4]==c && copyBoard[8]==c)||(copyBoard[13]==c && copyBoard[14]==c))
						return true;
					else
						return false;//c4
			case 13: if((copyBoard[12]==c && copyBoard[14]==c)||(copyBoard[16]==c && copyBoard[19]==c))
						return true;
					else
						return false;//d4
			case 16: if((copyBoard[13]==c && copyBoard[19]==c)||(copyBoard[15]==c && copyBoard[17]==c))
						return true;
					else
						return false;//d5
			case 19: if((copyBoard[13]==c && copyBoard[16]==c)||(copyBoard[18]==c && copyBoard[20]==c))
						return true;
					else
						return false;//d6
			case 5: if(copyBoard[9]==c && copyBoard[14]==c)
						return true;
					else
						return false;//e2
			case 9: if((copyBoard[5]==c && copyBoard[14]==c)||(copyBoard[10]==c && copyBoard[11]==c))
						return true;
					else
						return false;//e3
			case 14: if((copyBoard[5]==c && copyBoard[9]==c)||(copyBoard[12]==c && copyBoard[13]==c))
						return true;
					else
						return false;//e4
			case 3: if(copyBoard[10]==c && copyBoard[17]==c)
						return true;
					else
						return false;//f1
			case 10: if((copyBoard[3]==c && copyBoard[17]==c)||(copyBoard[9]==c && copyBoard[11]==c))
						return true;
					else
						return false;//f3
			case 17: if((copyBoard[3]==c && copyBoard[10]==c)||(copyBoard[15]==c && copyBoard[16]==c))
						return true;
					else
						return false;//f5
			case 1: if(copyBoard[11]==c && copyBoard[20]==c)
						return true;
					else
						return false;//g0
			case 11: if((copyBoard[1]==c && copyBoard[20]==c)||(copyBoard[9]==c && copyBoard[10]==c))
						return true;
					else
						return false;//g3
			case 20: if((copyBoard[1]==c && copyBoard[11]==c)||(copyBoard[18]==c && copyBoard[19]==c))
						return true;
					else
						return false;//g6			
			}
		}
		return false;		
	}
	
	public ArrayList generateRemove(char[] b, ArrayList list) {
		//ArrayList li = new ArrayList();
		//li=(ArrayList) list.clone();
		ArrayList grList = (ArrayList) list.clone();
		//char bo[] = b.clone();
		for(int i=0;i<b.length;i++) {
			if(b[i]=='B') {
				if(!(closeMill(i,b))) {
					char cbo[]=b.clone();
					cbo[i] = 'x';
					 grList.add(cbo);
					 
				}
				else {
					char cbo[]=b.clone();
					grList.add(cbo);
					
				}
			}
		}
		return grList;
	}
	
	public int staticEstimation(char[] sboard) {
		int wcount = 0;
		int bcount = 0;
		char[] lboard = sboard.clone();
		for(int i=0;i<lboard.length;i++) {
			if(lboard[i]=='W') {
				wcount++;
			}
			else if(lboard[i]=='B') {
				bcount++;
			}
		}
		return wcount-bcount;
	}
	

	public char[] MaxMin(char[] x, int depth) {
		
		if(depth>0) {
			
			System.out.println("current depth at MaxMin is"+depth);
			depth--;
			ArrayList<char[]> children = new ArrayList<char[]>();
			char[] minBoard;
			char[] maxBoardchoice = new char[50];
			children = generateAdd(x);
			for(char[] c : children) {
				System.out.println("the possible moves for white are: "+new String(c));
			}			
			int v=-999999;
			
			for(int i=0;i<children.size();i++) {	
				
					//positions_evaluated++;
						
				minBoard = MinMax(children.get(i), depth);
				if(v<staticEstimation(minBoard)) {					
					v = staticEstimation(minBoard);	
					minimax_estimate = v;
					maxBoardchoice = children.get(i);
				}
			}
			return maxBoardchoice;
		}
		else if(depth==0){
			positions_evaluated++;
		}
		return x;
	}

	public char[] MinMax(char[] x, int depth) {
		
		if(depth>0) {
			
			System.out.println("current depth at MinMax is"+depth);
			depth--;
			ArrayList<char[]> bchildren = new ArrayList<char[]>();
			char[] maxBoard;
			char[] minBoardchoice = new char[50];
			bchildren = generateBlackMoves(x);
			for(char[] bc : bchildren) {
			System.out.println("the possible moves for black are: "+new String(bc));
			
			}			
			int v=999999;
			
			for(int i=0;i<bchildren.size();i++) {				
				maxBoard = MaxMin(bchildren.get(i), depth);
				if(v>staticEstimation(maxBoard)) {					
					v = staticEstimation(maxBoard);					
					minBoardchoice = bchildren.get(i);
				}				
			}
			return minBoardchoice;
		}
		else if(depth==0){
			positions_evaluated++;
		}
		return x;
	} 
	
	public char[] swapWB(char[] x) {
				
		char[] lboard = x.clone();
		
		for(int i=0;i<lboard.length;i++) {
			if(lboard[i]=='W') {
				lboard[i] = 'B';
				continue;
			}
			if(lboard[i]=='B') {
				lboard[i] = 'W';
			}
		}
		return lboard;
	}
	
	public ArrayList generateBlackMoves(char[] x) {
		
		char[] lboard = x.clone();		
		for(int i=0;i<lboard.length;i++) {
			if(lboard[i]=='W') {
				lboard[i] = 'B';
				continue;
			}
			if(lboard[i]=='B') {
				lboard[i] = 'W';
			}
		}
		
		ArrayList<char[]> gbm = new ArrayList<char[]>();
		ArrayList<char[]> gbmswap = new ArrayList<char[]>();
		
		gbm = generateAdd(lboard);	
		for(char[] y : gbm) {
			char[] lsboard = y;
			for(int i=0;i<lsboard.length;i++) {
				if(lsboard[i]=='W') {
					lsboard[i] = 'B';
					continue;
				}
				if(lsboard[i]=='B') {
					lsboard[i] = 'W';
				}
			}
			gbmswap.add(y);
		}
		return gbmswap;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File fInputFile = new File(args[0]);
		File fOutputFile = new File(args[1]);
		int depth = Integer.parseInt(args[2]);		
		try {
			FileInputStream fis = new FileInputStream(fInputFile);
			PrintWriter out = new PrintWriter(new FileWriter(fOutputFile));
			//FileOutputStream fos = new FileOutputStream(fOutputFile);
			Scanner s= new Scanner(fis);
			
			while(s.hasNextLine()){
				String str= s.next();
				char[] b = str.toCharArray();
				//Entry<String,Integer> bm= null;
				MiniMaxOpeningBlack mb = new MiniMaxOpeningBlack();
				System.out.println("given board : "+new String(b));
				char[] e = mb.swapWB(b);
				System.out.println(""+new String(e));				
				char[] d = mb.MaxMin(e, depth);
				char[] f = mb.swapWB(d);
				System.out.println(""+mb.positions_evaluated);
				System.out.println(""+mb.minimax_estimate);
				out.println("Board Position : "+new String(f));
				out.println("Positions evaluated by static estimation : "+mb.positions_evaluated);
				out.println("MiniMax estimate : " +mb.minimax_estimate);
			}
			fis.close();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}

}
