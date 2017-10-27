package kmeans.figure;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import kmeans.text.OriginalData;


public class Kmeans {
	public final int attributeNum=13; //属性总数
	public int TrainNum;      //所有训练数据的范围
	public int TestNum;
	
	//存放训练数据
	public ArrayList<OriginalData> dataSet=new ArrayList<OriginalData>();
	OriginalData OData=new OriginalData();
	int k;
	int count;
	
	public Kmeans(){
		
	}
	
	public void showList(ArrayList<OriginalData> data){
		System.out.println("dataSet:");
		for(int i=0;i<data.size();i++){
			System.out.println(data.get(i).toString());
		}
	}
	
	//从文件中读取数值
		public void DataRead(String path){
			FileReader fr=null;
			BufferedReader br=null;
			String line=null;
			int num;
			if (path.indexOf(1) == 'r')
				num = TrainNum;
			else 
				num = TestNum;
			try {
				fr=new FileReader(path);
				br=new BufferedReader(fr);
				//line=br.readLine();
				OriginalData wine;
				ArrayList<OriginalData> data=new ArrayList<OriginalData>();
				double a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14;
				while ((line=br.readLine())!=null) {
					String strArray[] = line.split(",");
					a1=Double.parseDouble(strArray[0]);
					a2=Double.parseDouble(strArray[1]);
					a3=Double.parseDouble(strArray[2]);
					a4=Double.parseDouble(strArray[3]);
					a5=Double.parseDouble(strArray[4]);
					a6=Double.parseDouble(strArray[5]);
					a7=Double.parseDouble(strArray[6]);
					a8=Double.parseDouble(strArray[7]);
					a9=Double.parseDouble(strArray[8]);
					a10=Double.parseDouble(strArray[9]);
					a11=Double.parseDouble(strArray[10]);
					a12=Double.parseDouble(strArray[11]);
					a13=Double.parseDouble(strArray[12]);
					a14=Double.parseDouble(strArray[13]);
					wine=new OriginalData(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14);
					data.add(wine);
				}
				dataSet=data;
				showList(dataSet);
				br.readLine();
				br.close();
				fr.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void execute(int m_k){
			k=m_k;
			ArrayList clusters[]=new ArrayList[k];
			OriginalData means[]=new OriginalData[k];
			int i=0;
			for(i=0;i<k;i++){
				clusters[i]=new ArrayList<OriginalData>();
				means[i]=new OriginalData();
			}
			//默认一开始将前K个元组的值作为k个簇的质心（均值）
			for(i=0;i<k;i++){
				means[i].setA2(dataSet.get(i).getA2());
				means[i].setA3(dataSet.get(i).getA3());
				means[i].setA4(dataSet.get(i).getA4());
				means[i].setA5(dataSet.get(i).getA5());
				means[i].setA6(dataSet.get(i).getA6());
				means[i].setA7(dataSet.get(i).getA7());
				means[i].setA8(dataSet.get(i).getA8());
				means[i].setA9(dataSet.get(i).getA9());
				means[i].setA10(dataSet.get(i).getA10());
				means[i].setA11(dataSet.get(i).getA11());
				means[i].setA12(dataSet.get(i).getA12());
				means[i].setA13(dataSet.get(i).getA13());
				means[i].setA14(dataSet.get(i).getA14());
			}
			int lable=0;
			//根据默认的质心给簇赋值
			for(i=0;i<dataSet.size();i++)
			{
				lable = clusterOfTuple(means, dataSet.get(i));
				clusters[lable].add(dataSet.get(i));
			}
			//输出刚开始的簇
			for(lable=0;lable<k;lable++)
			{
				System.out.println("第"+(lable+1)+"个簇：");
				ArrayList<OriginalData> t=clusters[lable];
				showList(t);
				System.out.println();
			}
			double oldVar=-1;
			double newVar=getVar(clusters, means);
			while(Math.abs(newVar-oldVar)>=1) //当新旧函数值相差不到1即准则函数值不发生明显变化时，算法终止
			{
				for(i=0;i<k;i++)  //更新每个簇的中心点
				{
					means[i]=getMeans(clusters[i]);
					System.out.println(means[i].toString()); 
				}
				oldVar=newVar;
				newVar=getVar(clusters,means); //计算新的准则函数值
				for(i=0;i<k;i++)
					clusters[i].clear();  //清空每个簇
				//根据新的质心获得新的簇
				for(i=0;i<dataSet.size();i++)
				{
					lable=clusterOfTuple(means,dataSet.get(i));
					clusters[lable].add(dataSet.get(i));
				}
				//输出当前的簇
				count=0;
				for(lable=0;lable<k;lable++)
				{
					System.out.println("第"+(lable + 1)+"个簇：");
					ArrayList<OriginalData> t=clusters[lable];
					for(int j=0;j<t.size();j++)
					{
						if(t.get(j).getA1()==(lable+1))
							count++;
						System.out.println(t.get(j).toString());
					}
					//showVector(t);
					System.out.println();
				}
			}
		}
		
		//根据质心，决定当前元组属于哪个簇
		public int clusterOfTuple(OriginalData means[], OriginalData tuple){
			double dist = getDist(means[0], tuple);
			double tmp;
			int lable=0; //标示属于哪一个簇
			for(int i=1;i<k;i++)
			{
				tmp=getDist(means[i],tuple);
				if(tmp<dist)
				{
					dist=tmp;
					lable=i;
				}
			}
			return lable;
		}
		
		public double getDist(OriginalData mean, OriginalData data){
			return (mean.getA2()-data.getA2())*(mean.getA2()-data.getA2())+(mean.getA3()-data.getA3())*(mean.getA3()-data.getA3())+(mean.getA4()-data.getA4())*(mean.getA4()-data.getA4())+(mean.getA5()-data.getA5())*(mean.getA5()-data.getA5())+(mean.getA6()-data.getA6())*(mean.getA6()-data.getA6())+(mean.getA7()-data.getA7())*(mean.getA7()-data.getA7())+(mean.getA8()-data.getA8())*(mean.getA8()-data.getA8())+(mean.getA9()-data.getA9())*(mean.getA9()-data.getA9())+(mean.getA10()-data.getA10())*(mean.getA10()-data.getA10())+(mean.getA11()-data.getA11())*(mean.getA11()-data.getA11())+(mean.getA12()-data.getA12())*(mean.getA12()-data.getA12())+(mean.getA13()-data.getA13())*(mean.getA13()-data.getA13())+(mean.getA14()-data.getA14())*(mean.getA14()-data.getA14());
		}
		
		//获得给定簇集的平方误差
		public double getVar(ArrayList<OriginalData> clusters[], OriginalData means[]){
			double var=0;
			for(int i=0;i<k;i++)
			{
				ArrayList<OriginalData> t=clusters[i];
				for(int j=0;j<t.size();j++)
					var+=getDist(t.get(j),means[i]);
			}
			return var;
		}
		
		public OriginalData getMeans(ArrayList<OriginalData> cluster){
			int num=cluster.size();
			double A2=0,A3=0,A4=0,A5=0,A6=0,A7=0,A8=0,A9=0,A10=0,A11=0,A12=0,A13=0,A14=0;
			OriginalData t=new OriginalData();
			for(int i=0;i<num;i++)
			{
				A2+=cluster.get(i).getA2();
				A3+=cluster.get(i).getA3();
				A4+=cluster.get(i).getA4();
				A5+=cluster.get(i).getA5();
				A6+=cluster.get(i).getA6();
				A7+=cluster.get(i).getA7();
				A8+=cluster.get(i).getA8();
				A9+=cluster.get(i).getA9();
				A10+=cluster.get(i).getA10();
				A11+=cluster.get(i).getA11();
				A12+=cluster.get(i).getA12();
				A13+=cluster.get(i).getA13();
				A14+=cluster.get(i).getA14();
			}
			t.setA2(A2/num);
			t.setA3(A3/num);
			t.setA4(A4/num);
			t.setA5(A5/num);
			t.setA6(A6/num);
			t.setA7(A7/num);
			t.setA8(A8/num);
			t.setA9(A9/num);
			t.setA10(A10/num);
			t.setA11(A11/num);
			t.setA12(A12/num);
			t.setA13(A13/num);
			t.setA14(A14/num);
			return t;
		}
		
		public double getCorrect(){
			double correct=count*1.0/130;
			return correct;
		}
}
