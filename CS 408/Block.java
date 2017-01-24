/* Edgar Ruiz
   CS 408
   May 11, 2016
*/


public class Block{
	int[] kill;
	int[] gen;
	int[] pred;
	int[] in;
	int[] out;
	public Block(int[] kill, int[] gen, int[] pred, int[] in, int[] out){
		this.kill = kill;
		this.gen = gen;
		this.pred = pred;
		this.in = in;
		this.out = out; 
	}
	public int[] getKill(){
		return kill;
	}
	public int[] getGen(){
		return gen;
	}
	public int[] getPred(){
		return pred;
	}
	public int[] getIn(){
		return in;
	}
	public int[] getOut(){
		return out;
	}
	public void setOut(int[] a){
		for(int i = 0; i < a.length; i++){
			this.out[i] = a[i];
		}
	}
	public int getPredElement(int a){
		return this.pred[a];
	}
	public int[] copyFromIn(){
		int[] a = new int[7];
		for(int i = 0; i < this.in.length; i++){
			a[i] = this.in[i];
		}
		return a;
	}
	public int[] unionOut(int[] a){
		for(int i = 0; i < a.length; i++){
			if(a[i] == 1 || this.out[i] == 1){
				a[i] = 1;
			}
		}
		return a;
	}
	public void copyToIn(int[] a){
		for(int i = 0; i < a.length; i++){
			this.in[i] = a[i];
		}
	}
	public void print(){
		for(int i = 0; i < this.in.length; i++){
			if(this.in[i] == 1){
				System.out.println("d" + i + " ");
			}
		}
	}
	public void copyFromOut(int[] a){
		for(int i = 0; i < a.length; i++){
			a[i] = this.out[i];
		}
	}
	public int[] diff(int[] a){
		for(int i = 0; i < a.length; i++){
			if(this.kill[i] == 1){
				a[i] = 0;
			}
		}
		return a;
	}
	public int[] unionGen(int[] a){
		for(int i = 0; i < a.length; i++){
			if(a[i] == 1 || this.gen[i] == 1){
				a[i] = 1;
			}
		}
		return a;
	}
	public void copyToOut(int[] a){
		for(int i = 0; i < a.length; i++){
			this.out[i] = a[i];
		}
	}
	public boolean equalSet(int[] a){
		boolean result = true;
		for(int i = 0; i < 7; i++){
			if(this.out[i] != a[i]){
				result = false;
				return result;
			}
		}
		return result;
	}
	public int getInElement(int a){
		return this.in[a];
	}
	public int getOutElement(int a){
		return this.out[a];
	}
}