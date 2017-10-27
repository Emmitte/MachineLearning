package kmeans.text;

public class Test {

	public static void main(String[] args) {
		Kmeans kmeans=new Kmeans();
		String path="product.txt";
		double start,end,dur;
		kmeans.DataRead(path);
		start=System.currentTimeMillis();
		kmeans.execute(2);
		System.out.println("correct rate:"+kmeans.getCorrect()+"%");
		end=System.currentTimeMillis();
		dur=end-start;
		System.out.println("running time:"+dur);
	}

}
