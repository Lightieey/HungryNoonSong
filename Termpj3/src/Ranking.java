import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.JPanel;

public class Ranking extends JPanel {  
	static List namelist =  new ArrayList();
	static List scorelist = new ArrayList();
	ArrayList<FileHelp> sclist = new ArrayList<FileHelp>();
	static Scanner scan = new Scanner(System.in);
	File file;
	
	public Ranking() {
		file = new File("userscores.txt");
	}
	
	protected void rank() {
		FileHelp[] rank = new FileHelp[sclist.size()];
		sclist.toArray(rank);
		Collections.reverse(sclist);
		
		for (int i=0; i<rank.length;i++) {
			for (int j=0; j<rank.length; j++) {
				if (rank[i].getScore() < rank[j].getScore()) {
					rank[i].plusRank();
				}
			}
		}
	}
	
	public void scoreprint() { //순위별로 출력
			sclist.sort(new Comparator<FileHelp>() {

			@Override
			public int compare(FileHelp o1, FileHelp o2) {
				// TODO Auto-generated method stub
				int score1 = o1.getScore();
				int score2 = o2.getScore();
				if (score1 == score2) {
					return 0;
				} else if (score1 > score2) {
					return 1;
				} else {
					return -1;
				}
			}
			
		});
		rank();
		
		for (FileHelp output : sclist) {
			namelist.add(output.getName());
			scorelist.add(output.getScore());
			output.totalprint();
		}
		
	}
		
	
	public void scoreadd(String newname, int newscore) throws IOException { // 정보저장
		load(); // 기존 파일 입력받음
		FileHelp input = new FileHelp(newname, newscore); // 새로입력
		sclist.add(input);
		save(); // 저장
	}
	
	public void load() throws IOException { // 기존파일 입력받음
		sclist.clear();
		BufferedReader br = new BufferedReader(new FileReader(file));
		while (true) {
			String msg = br.readLine();
			if (msg == null ) {
				break;
			}
			Scanner data = new Scanner(msg).useDelimiter("\\s*@\\s*");
			String name = data.next();
			int score = data.nextInt();
			FileHelp rf = new FileHelp(name, score);
			sclist.add(rf);
		}
		br.close();
	}
	
	public void save() throws IOException { // 저장
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter pw = new PrintWriter(bw);
		
		for (FileHelp save : sclist) {
			pw.println(save.getName() + "@" + save.getScore());
			pw.flush();
		}
		pw.close();
	}
}