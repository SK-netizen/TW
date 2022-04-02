package es.unex.cum.tw.jsp;

public class Factorial {
	private int num=1;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
	public long getFactorial(){
		long r = 1;
		for (int i = 1; i <= num; i++) {
			r = r * i;
		}
		return r;
	}

}
