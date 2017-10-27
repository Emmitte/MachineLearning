package bayes.iris;

public class Test {

	public static void main(String[] args) {
		Bayes bayes=new Bayes();
		String trainpath="dataset/output/iris/iris11/irisTrain.data";
		String testpath="dataset/output/iris/iris11/irisTest.data";
		bayes.setTrainNum(72);
		bayes.setTestNum(78);
		bayes.DataRead(trainpath);
		bayes.do_bayes();
		bayes.DataRead(testpath);
		bayes.houyan();
		double p;
		int n;
		n=bayes.getM();
		p=n*1.0/bayes.getTestNum()*100;
		System.out.println("accurate:"+p+"%");
	}

}
