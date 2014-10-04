import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;


public class ABGame {

	/**
	 * @param args
	 */
	private static int positions_evaluated = 0;
	private static int ab_estimate = 0;
	
	/*public ArrayList generateAdd(char[] b) {
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
	}	*/
	
	public ArrayList generateMovesMidgameEndgame(char[]x) {
		ArrayList<char[]> gmmeList = new ArrayList<char[]>();
		int wcount=0;
		for(int i=0;i<x.length;i++){
			if(x[i]=='W') {
				wcount++;
			}
		}
		if(wcount==3) {
			 gmmeList = generateHopping(x);
			return gmmeList;
		}
		else {
			 gmmeList = generateMove(x);
			return gmmeList;
		}
	}
	
	public ArrayList generateHopping(char[] x) {
		ArrayList<char[]> ghList = new ArrayList<char[]>();
		//this.board=b.clone();
		char copyBoard[];
		for(int i=0;i<x.length;i++){
			if(x[i]=='W'){
				for(int j=0;j<x.length;j++) {
					if(x[j]=='x') {
						copyBoard = x.clone();
						copyBoard[i]='x';
						copyBoard[j]='W';
						if(closeMill(j,copyBoard)){
							generateRemove(copyBoard, ghList); 
						}
						else {
							ghList.add(copyBoard);
						}
					}
				}
			}
		}
		return ghList;
	}
	
	public ArrayList generateMove(char[] x) {
		ArrayList<char[]> gmList = new ArrayList<char[]>();
		//this.board=b.clone();
		char copyBoard[];
		int[] nlist;
		for(int i=0;i<x.length;i++){
			if(x[i]=='W'){
				nlist=neighbours(i);
				for(int j: nlist) {
					if(x[j]=='x') {
						copyBoard = x.clone();
						copyBoard[i]='x';
						copyBoard[j]='W';
						if(closeMill(j,copyBoard)){
							generateRemove(copyBoard, gmList); 
						}
						else {
							gmList.add(copyBoard);
						}
					}
				}
			}
		}
		return gmList;
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
	
	public int[] neighbours(int j)
	{
		int[] adj=new int[5];
		
		switch(j)
		{		
		case 0 : adj=new int[]{1,2,6}; return adj;
		case 1 : adj=new int[]{0,11}; return adj;
		case 2 : adj=new int[]{0,3,4,7}; return adj; 
		case 3 : adj=new int[]{2,10}; return adj;
		case 4 : adj=new int[]{2,5,8};return adj;
		case 5 : adj=new int[]{4,9}; return adj;
		case 6 : adj=new int[]{0,7,18}; return adj;
		case 7 : adj=new int[]{2,6,8,15}; return adj;
		case 8 : adj=new int[]{4,7,12};return adj; 
		case 9 : adj=new int[]{5,10,14}; return adj;
		case 10 : adj=new int[]{3,9,11,17}; return adj;
		case 11 : adj=new int[]{1,10,20}; return adj;
		case 12 : adj=new int[]{8,13}; return adj;
		case 13 : adj=new int[]{12,14,16}; return adj;
		case 14 : adj=new int[]{9,13}; return adj;
		case 15 : adj=new int[]{7,16}; return adj;
		case 16 : adj=new int[]{13,15,17,19};return adj;
		case 17 : adj=new int[]{10,16}; return adj;
		case 18 : adj=new int[]{6,19}; return adj;
		case 19 : adj=new int[]{16,18,20}; return adj;	
		case 20 : adj=new int[]{11,19}; return adj;
		default: adj=null;	return adj;
		}
			
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
		
		gbm = generateMovesMidgameEndgame(lboard);	
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
	
	public int staticEstimation(char[] sboard) {
		int wcount = 0;
		int bcount = 0;		
		ArrayList<char[]> nbmList = new ArrayList<char[]>();
		nbmList = generateBlackMoves(sboard);
		int bmovecount = nbmList.size();
		for(int i=0;i<sboard.length;i++) {
			if(sboard[i]=='W') {
				wcount++;
			}
			else if(sboard[i]=='B') {
				bcount++;
			}
		}
		if(bcount<=2) {
			return 10000;
		}
		else if(wcount<=2) {
			return -10000;
		}
		else if(bmovecount==0) {
			return 10000;
		}
		else {
			return ((1000*(wcount-bcount))-bmovecount);
		}
		//return wcount-bcount;
	}
	
	
public char[] MaxMin(char[] x, int depth, int a, int b) {
		
		if(depth>0) {
			System.out.println("current depth at MaxMin is"+depth);
			depth--;
			ArrayList<char[]> children = new ArrayList<char[]>();
			char[] minBoard;
			char[] maxBoardchoice = new char[50];
			children = generateMovesMidgameEndgame(x);
			for(char[] c : children) {
			System.out.println("the possible moves for white are: "+new String(c));
			}
			int v=-999999;
			for(int i=0;i<children.size();i++) {
				//positions_evaluated++;
				minBoard = MinMax(children.get(i), depth, a, b);
				if(v<staticEstimation(minBoard)) {
					v = staticEstimation(minBoard);
					ab_estimate = v;
					maxBoardchoice = children.get(i);
				}
				if(v>=b) {
					return maxBoardchoice;
				}
				else {
					a = Math.max(v,a);
				}
			}
			return maxBoardchoice;
		}
		else if(depth==0) {
			positions_evaluated++;
		}
		return x;
	}

	public char[] MinMax(char[] x, int depth, int a, int b) {
		
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
				//positions_evaluated++;
				maxBoard = MaxMin(bchildren.get(i), depth, a, b);
				if(v>staticEstimation(maxBoard)) {
					v = staticEstimation(maxBoard);
					minBoardchoice = bchildren.get(i);
				}
				if(v<=a) {
					return minBoardchoice;
				}
				else {
					b = Math.min(v,b);
				}
			}
			return minBoardchoice;
		}
		else if(depth==0) {
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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File fInputFile = new File(args[0]);
		File fOutputFile = new File(args[1]);
		int depth = Integer.parseInt(args[2]);	
		int x=-999999, y=999999;
		try {
			FileInputStream fis = new FileInputStream(fInputFile);
			PrintWriter out = new PrintWriter(new FileWriter(fOutputFile));
			//FileOutputStream fos = new FileOutputStream(fOutputFile);
			Scanner s= new Scanner(fis);
			
			while(s.hasNextLine()){
				String str= s.next();
				char[] b = str.toCharArray();
				ABGame ab = new ABGame();
				char[] a=ab.MaxMin(b, depth,x,y);
				System.out.println(""+ab.positions_evaluated);
				System.out.println(""+ab.ab_estimate);
				out.println("Board Position : "+new String(a));
				out.println("Positions evaluated by static estimation : "+ab.positions_evaluated);
				out.println("MiniMax estimate : " +ab.ab_estimate);
				
			}
			fis.close();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}

}
