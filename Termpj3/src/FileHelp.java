public class FileHelp {
	private String name;
	private int score;
	private int rank;
	
	public FileHelp(String name, int score) {
		this.name = name;
		this.score = score;
		rank = 1;
	}
	
	public String getName() {
		return name;
	}
	public int getScore() {
		return score;
	}
	public int getRank() {
		return rank;
	}
	public void plusRank() {
		rank++;
	}
	public void totalprint() {
		System.out.println("순위 : " + rank + " 이름 : " + name + " 점수 : " + score);
	}
}