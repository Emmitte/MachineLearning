package kmeans.text;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import kmeans.text.Product;


public class Kmeans {
	public final int attributeNum=7; //属性值
	public int TrainNum;      //训练数
	public int TestNum;
	int iter_count;
	
	public ArrayList<Product> products=new ArrayList<Product>();
	Product product;
	int k;
	int count;
	
	public Kmeans(){
		count=0;
		iter_count=0;
	}
	
	public void showList(ArrayList<Product> data){
		System.out.println("products:");
		for(int i=0;i<data.size();i++){
			System.out.println(data.get(i).toString());
		}
	}
	
	//读取数据
		public void DataRead(String path){
			FileReader fr=null;
			BufferedReader br=null;
			String line=null;
			String id,brand,pid,pName,category;
			int gender;
			
			try {
				fr=new FileReader(path);
				br=new BufferedReader(fr);
				line=br.readLine();
				//count++;
				//System.out.println(line);
				while ((line=br.readLine())!=null) {
						//count++;
						//System.out.println(line);
						String strArray[] = line.split("\t");
						//for(int i=0;i<strArray.length;i++)
							//System.out.print(strArray[i]+" ");
						//System.out.println(count+":"+strArray.length);
						id=strArray[0];
						brand=strArray[1];
						pid=strArray[2];
						pName=strArray[3];
						gender=Integer.parseInt(strArray[5]);
						category=strArray[strArray.length-1];
						Product p=new Product(id,brand,pid,pName, gender, category);
						products.add(p);
						//System.out.println();
					}
				br.readLine();
				br.close();
				fr.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void execute(int m_k){
			k=m_k;
			ArrayList clusters[]=new ArrayList[k];
			Product means[]=new Product[k];
			//count=0;
			int i=0;
			//默认一开始将前K个元组的值作为k个簇的质心（均值）
			for(i=0;i<k;i++){
				clusters[i]=new ArrayList<OriginalData>();
				means[i]=new Product(products.get(i).getId(),products.get(i).getBrand(),products.get(i).getPid(),products.get(i).getPName(),products.get(i).getGender(),products.get(i).getCategory());
			}
			int lable=0;
			//根据默认的质心给簇赋值
			for(i=0;i<products.size();i++)
			{
				lable = clusterOfTuple(means, products.get(i));
				clusters[lable].add(products.get(i));
			}
			//输出刚开始的簇
			for(lable=0;lable<k;lable++)
			{
				System.out.println("第"+(lable+1)+"个簇：");
				ArrayList<Product> t=clusters[lable];
				showList(t);
				System.out.println();
			}
			double oldVar=-1;
			double newVar=getVar(clusters, means);
			while(Math.abs(newVar-oldVar)>=1&&iter_count<=50) //当新旧函数值相差不到1即准则函数值不发生明显变化时，算法终止
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
				for(i=0;i<products.size();i++)
				{
					lable=clusterOfTuple(means,products.get(i));
					clusters[lable].add(products.get(i));
				}
				//输出当前的簇
				count=0;
				System.out.println("迭代次数:"+iter_count);
				for(lable=0;lable<k;lable++)
				{
					System.out.println("第"+(lable + 1)+"个簇：");
					ArrayList<Product> t=clusters[lable];
					for(int j=0;j<t.size();j++)
					{
						if((t.get(j).getGender()==(lable))||(t.get(j).getGender()==2))
							count++;
						System.out.println("count:"+count);
						System.out.println(t.get(j).toString());
					}
					//showVector(t);
					System.out.println();
				}
				iter_count++;
			}
		}
		
		//根据质心，决定当前元组属于哪个簇
		public int clusterOfTuple(Product means[], Product tuple){
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
		
		public double getDist(Product mean, Product data){
			Similarity similar=new Similarity();
			double dist=similar.getSimilarity(mean, data);
			return dist;
		}
		
		//获得给定簇集的距离
		public double getVar(ArrayList<Product> clusters[], Product means[]){
			double var=0;
			for(int i=0;i<k;i++)
			{
				ArrayList<Product> t=clusters[i];
				for(int j=0;j<t.size();j++)
					var+=getDist(t.get(j),means[i]);
			}
			return var;
		}
		//用簇的中点作为平均值
		public Product getMeans(ArrayList<Product> cluster){
			int num=cluster.size();
			Product t=cluster.get(num/2);
			/*
			double A2=0,A3=0,A4=0,A5=0,A6=0,A7=0,A8=0,A9=0,A10=0,A11=0,A12=0,A13=0,A14=0;
			
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
			*/
			return t;
		}
		
		public double getCorrect(){
			double correct=count*1.0/products.size()*100;
			return correct;
		}
}
