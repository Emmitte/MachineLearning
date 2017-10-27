package kmeans.text;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import kmeans.text.Product;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

public class Similarity {
	//public ArrayList<Product> products=new ArrayList<Product>();
	private TreeSet<S> originalSet=new TreeSet<S>();
	private TreeSet<S> normalizationSet=new TreeSet<S>();
	private TreeMap<String, TreeMap<String, Double>> allMap=new TreeMap<String, TreeMap<String, Double>>();
	private TreeMap<String, Double> subMap=new TreeMap<String, Double>();
	
	protected double getSimilarity(Product p1,Product p2){
		StringBuffer sb = new StringBuffer();
		byte[] bt;
		InputStream is;
		Reader read; 
		Lexeme t;
		IKSegmenter iks;
		
		double s=0.00;            //整体的相似度
		double s_brand=0.00;      //商品名称的相似度
		double s_pid=0.00;        //品牌编号的相似度
		double s_pName=0.00;  //品牌英文名称的相似度
		double s_gender=0.00;     //性别的相似度
		double s_category=0.00;       //商品商城分类的相似度
		int weightBrand=5,weightPid=3,weightPName=3,weihtGender=10,weightCategory=7;
		int i=0;
		double vector1Modulo = 0.00;//向量1的模  
        double vector2Modulo = 0.00;//向量2的模  
        double vectorProduct = 0.00; //向量�?
        //创建向量空间模型，使用map实现，主键为词项，�?为长度为2的数组，存放�?��应词项在字符串中的出现次�?
      	Map<String, int[]> vectorSpace = new HashMap<String, int[]>();
      	//为了避免频繁产生�?��变量，所以将itemCountArray声明在此
      	int[] itemCountArray = null;
      	String strArray[]=null;
        Iterator iter=null;
        
		//1.计算品牌的相似度
		vectorSpace.clear();
        try {
			//对p1的商品名称处�?
			bt= p1.getBrand().getBytes();
			is= new ByteArrayInputStream(bt);
			read= new InputStreamReader(is);
			iks= new IKSegmenter(read,true);
			while ((t = iks.next()) != null) {
					sb.append(t.getLexemeText() + "/");
					//System.out.println(t.getLexemeText());
			}
			//System.out.println("删除之前�?  "+sb.toString());
			sb.delete(sb.length() - 1, sb.length());
			//System.out.println("删除之后�?  "+sb.toString());
			String p1name,p2name;
			p1name=sb.toString();
			//以空格为分隔符，分解字符�?
			strArray = p1name.split("/");
			for(i=0; i<strArray.length; ++i)  
	         {  
	             if(vectorSpace.containsKey(strArray[i]))  
	                 ++(vectorSpace.get(strArray[i])[0]);  
	             else  
	             {  
	                 itemCountArray = new int[2];  
	                 itemCountArray[0] = 1;  
	                 itemCountArray[1] = 0;  
	                 vectorSpace.put(strArray[i], itemCountArray);  
	             }  
	         } 
			sb.delete(0, sb.length());
			//showmap(vectorSpace);
			//对p2的商品名称处�?
			bt= p2.getPName().getBytes();
			is= new ByteArrayInputStream(bt);
			read= new InputStreamReader(is);
			iks= new IKSegmenter(read,true);
			while ((t = iks.next()) != null) {
					sb.append(t.getLexemeText() + "/");
					//System.out.println(t.getLexemeText());
			}
			//System.out.println("删除之前�?  "+sb.toString());
			sb.delete(sb.length() - 1, sb.length());
			//System.out.println("删除之后�?  "+sb.toString());
			p2name=sb.toString();
			strArray=p2name.split("/");
			for(i=0; i<strArray.length; ++i)  
	         {  
	             if(vectorSpace.containsKey(strArray[i]))  
	                 ++(vectorSpace.get(strArray[i])[1]);  
	             else  
	             {  
	                 itemCountArray = new int[2];  
	                 itemCountArray[0] = 0;  
	                 itemCountArray[1] = 1;  
	                 vectorSpace.put(strArray[i], itemCountArray);  
	             }  
	         }  
	         //showmap(vectorSpace);
	         
	         //计算相似�?  
	         iter= vectorSpace.entrySet().iterator();     
	         while(iter.hasNext())  
	         {  
	             Map.Entry entry = (Map.Entry)iter.next();  
	             itemCountArray = (int[])entry.getValue();  	               
	             vector1Modulo += itemCountArray[0]*itemCountArray[0];  
	             vector2Modulo += itemCountArray[1]*itemCountArray[1];  	               
	             vectorProduct += itemCountArray[0]*itemCountArray[1];  
	         }  	           
	         vector1Modulo = Math.sqrt(vector1Modulo);  
	         vector2Modulo = Math.sqrt(vector2Modulo);       
			
	} catch (IOException e) {
			e.printStackTrace();
	}
        s_brand=vectorProduct/(vector1Modulo*vector2Modulo);
        //System.out.println("商品名称的相似度:"+s_pname);
        
        //2.计算品牌英文名称的相似度
        if(p1.getPid().equals(p2.getPid()))
        	s_pid=1;
        else
        	s_pid=0;
        
		//2.计算品牌英文名称的相似度

		//以空格为分隔符，分解字符�?
		strArray= p1.getPName().split(" ");
		//showString(strArray2);
		vectorSpace.clear();
		for(i=0;i<strArray.length;i++){
			if(vectorSpace.containsKey(strArray[i]))
				++(vectorSpace.get(strArray[i])[0]);
			else{
				itemCountArray=new int[2];
				itemCountArray[0]=1;
				itemCountArray[1]=0;
				vectorSpace.put(strArray[i], itemCountArray);
			}
		}
		//showmap(brandNameSpace);
		
		strArray=p2.getPName().split(" ");
		//showString(strArray2);
		for(i=0;i<strArray.length;i++){
			if(vectorSpace.containsKey(strArray[i]))
				++(vectorSpace.get(strArray[i])[1]);
			else{
				itemCountArray=new int[2];
				itemCountArray[0]=0;
				itemCountArray[1]=1;
				vectorSpace.put(strArray[i], itemCountArray);
			}
		}
		//showmap(brandNameSpace);
		
		//计算相似�?
		vector1Modulo=0.00;
		vector2Modulo=0.00;
		vectorProduct=0.00;
		iter=vectorSpace.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry)iter.next(); 
			itemCountArray=(int[])entry.getValue();
			vector1Modulo+=itemCountArray[0]*itemCountArray[0];
			vector2Modulo+=itemCountArray[1]*itemCountArray[1];
			vectorProduct+=itemCountArray[0]*itemCountArray[1];
		}
		vector1Modulo=Math.sqrt(vector1Modulo);
		vector2Modulo=Math.sqrt(vector2Modulo);
		s_pName=vectorProduct/(vector1Modulo*vector2Modulo);
		/*
		if(s_brandName>0.99&&s_brandName<1)
			System.out.println("品牌英文名称的相似度:"+(int)Math.floor(s_brandName+1));
		else
			System.out.println("品牌英文名称的相似度:"+s_brandName);
		*/
		//3.计算性别的相似度
		
		int[] itemCountArrayP1=new int[2];
		int[] itemCountArrayP2=new int[2];
		
		if(p1.getGender()==0){
			itemCountArrayP1[0]=1;
			itemCountArrayP1[1]=0;
		}
		if(p1.getGender()==1){
			itemCountArrayP1[0]=0;
			itemCountArrayP1[1]=1;
		}
		if(p1.getGender()==2){
			itemCountArrayP1[0]=1;
			itemCountArrayP1[1]=1;
		}
		
		if(p2.getGender()==0){
			itemCountArrayP2[0]=1;
			itemCountArrayP2[1]=0;
		}
		if(p2.getGender()==1){
			itemCountArrayP2[0]=0;
			itemCountArrayP2[1]=1;
		}
		if(p2.getGender()==2){
			itemCountArrayP2[0]=1;
			itemCountArrayP2[1]=1;
		}
		vector1Modulo=0.00;
		vector2Modulo=0.00;
		vectorProduct=0.00;
		vector1Modulo=itemCountArrayP1[0]*itemCountArrayP1[0]+itemCountArrayP1[1]*itemCountArrayP1[1];
		vector2Modulo=itemCountArrayP2[0]*itemCountArrayP2[0]+itemCountArrayP2[1]*itemCountArrayP2[1];
		vectorProduct=itemCountArrayP1[0]*itemCountArrayP2[0]+itemCountArrayP1[1]*itemCountArrayP2[1];
		vector1Modulo=Math.sqrt(vector1Modulo);
		vector2Modulo=Math.sqrt(vector2Modulo);
		s_gender=vectorProduct/(vector1Modulo*vector2Modulo);
		//System.out.println("性别的相似度:"+s_gender);
		
		//4.计算商品商城分类的相似度
		
		vectorSpace.clear();
		sb.delete(0, sb.length());
        try {
			//对p1的商品商城分类处�?
			bt= p1.getCategory().getBytes();
			is= new ByteArrayInputStream(bt);
			read= new InputStreamReader(is);
			iks= new IKSegmenter(read,true);
			while ((t = iks.next()) != null) {
					sb.append(t.getLexemeText() + "/");
					//System.out.println(t.getLexemeText());
			}
			//System.out.println("删除之前�?  "+sb.toString());
			sb.delete(sb.length() - 1, sb.length());
			//System.out.println("删除之后�?  "+sb.toString());
			String p1type,p2type;
			p1type=sb.toString();
			//以空格为分隔符，分解字符�?
			strArray = p1type.split("/");
			for(i=0; i<strArray.length; ++i)  
	         {  
	             if(vectorSpace.containsKey(strArray[i]))  
	                 ++(vectorSpace.get(strArray[i])[0]);  
	             else  
	             {  
	                 itemCountArray = new int[2];  
	                 itemCountArray[0] = 1;  
	                 itemCountArray[1] = 0;  
	                 vectorSpace.put(strArray[i], itemCountArray);  
	             }  
	         } 
			sb.delete(0, sb.length());
			//showmap(vectorSpace);
			//对p2的商品商城分类处�?
			bt= p2.getCategory().getBytes();
			is= new ByteArrayInputStream(bt);
			read= new InputStreamReader(is);
			iks= new IKSegmenter(read,true);
			while ((t = iks.next()) != null) {
					sb.append(t.getLexemeText() + "/");
					//System.out.println(t.getLexemeText());
			}
			//System.out.println("删除之前�?  "+sb.toString());
			sb.delete(sb.length() - 1, sb.length());
			//System.out.println("删除之后�?  "+sb.toString());
			p2type=sb.toString();
			strArray=p2type.split("/");
			for(i=0; i<strArray.length; ++i)  
	         {  
	             if(vectorSpace.containsKey(strArray[i]))  
	                 ++(vectorSpace.get(strArray[i])[1]);  
	             else  
	             {  
	                 itemCountArray = new int[2];  
	                 itemCountArray[0] = 0;  
	                 itemCountArray[1] = 1;  
	                 vectorSpace.put(strArray[i], itemCountArray);  
	             }  
	         }  
	         //showmap(vectorSpace);
	         
	         //计算相似�?  
	         vector1Modulo=0.00;
	 		 vector2Modulo=0.00;
	 		 vectorProduct=0.00;
	         iter= vectorSpace.entrySet().iterator();     
	         while(iter.hasNext())  
	         {  
	             Map.Entry entry = (Map.Entry)iter.next();  
	             itemCountArray = (int[])entry.getValue();  	               
	             vector1Modulo += itemCountArray[0]*itemCountArray[0];  
	             vector2Modulo += itemCountArray[1]*itemCountArray[1];  	               
	             vectorProduct += itemCountArray[0]*itemCountArray[1];  
	         }  	           
	         vector1Modulo = Math.sqrt(vector1Modulo);  
	         vector2Modulo = Math.sqrt(vector2Modulo);       
			
	} catch (IOException e) {
			e.printStackTrace();
	}
        s_category=vectorProduct/(vector1Modulo*vector2Modulo);
        //System.out.println("商品商城分类的相似度:"+s_type);
		
        s=s_brand*weightBrand+s_pid*weightPid+s_pName*weightPName+s_gender*weihtGender+s_category*weightCategory;   //计算整体的相似度
        
        //System.out.println("整体的相似度:"+s);    
        
        return s;
	}
	private void makeNormalization(){
		S maxS=null;
		S minS=null;
		S s=null;
		String i;
		double max,min,normal;
		double similarity=0.0;
		Iterator<S> OriIter=originalSet.iterator();
		maxS=originalSet.first();
		minS=originalSet.last();
		max=maxS.getSimilarity();
		min=minS.getSimilarity();
		normal=max-min;
		normalizationSet.clear();
		while(OriIter.hasNext()){
			s=OriIter.next();
			i=s.getId();
			similarity=(s.getSimilarity()-min)/normal;
			similarity=Double.parseDouble(new DecimalFormat("#.###").format(similarity));
			s=null;
			s=new S(i, similarity);
			normalizationSet.add(s);
		}
	}
	protected TreeSet<S> execute(int i,ArrayList<Product> products){
		S s=null;
		Product p1,p2;
		double similarity=0.0;
		int j;
		int n=products.size();
		p1=products.get(i);
		originalSet.clear();
		for(j=i+1;j<n;j++){
			p2=products.get(j);
			//System.out.println("product"+(i+1)+"与product"+(j+1)+"的比�?");
			similarity=getSimilarity(p1, p2);
			s=new S(p2.getId(), similarity);
			originalSet.add(s);
			if(products.size()-j==1)
				break;
		}
		this.makeNormalization();
		return normalizationSet;
	}
	protected void execute(ArrayList<Product> products){
		/*
		Iterator<Product> iter=products.iterator();
		Product p1,p2;
		int count=0;
		while(iter.hasNext()){
			count++;
			p1=iter.next();
			p2=iter.next();
			System.out.println("product"+count+"与product"+(count+1)+"的相似度:");
			getSimilarity(p1, p2);
		}
		*/
		//getSimilarity(products.get(1), products.get(2));
		
		S s=null;
		Product p1,p2;
		double similarity=0.0;
		int i,j;
		
		int n=products.size();
		
		/*  //商品1与其余商品计算相似度
		p1=products.get(1);
		for(int i=0;i<products.size();i++){
			p2=products.get(i);
			//System.out.println("product"+(1)+"与product"+(i+1)+"的相似度:");
			similarity=getSimilarity(p1, p2);
			s=new S(i+1, similarity);
			originalSet.add(s);
			if(products.size()-i==2)
				break;
		}
		*/
		
		for(i=0;i<10;i++){
			//System.out.println((i+1)+"与其余的商品进行计算");
			setSubMap(execute(i,products));
			//showSubMap();
			setAllMap(products.get(i).getId(), subMap);
		}
		
		//return normalizationSet;
	}
	public TreeMap<String, TreeMap<String, Double>> getAllMap() {
		return allMap;
	}
	private void setAllMap(String id,TreeMap<String, Double> subMap) {
		ByValueComparator bvc=new ByValueComparator(subMap);
		TreeMap<String,Double> newSubMap = new TreeMap<String,Double>(bvc);
		newSubMap.putAll(subMap);
		//allMap.put(id, subMap);
		//showSubMap(newSubMap);
		allMap.put(id, newSubMap);
	}
	public TreeMap<String, Double> getSubMap() {
		return subMap;
	}
	private void setSubMap(TreeSet<S> subSet) {
		subMap.clear();
		Iterator<S> iter=subSet.iterator();
		S s;
		while (iter.hasNext()) {
			s=iter.next();
			subMap.put(s.getId(), s.getSimilarity());
		}
	}
	private void showSubMap(TreeMap<String, Double> map){
		System.out.println(map.keySet());
		System.out.println(map.values());
		/*
		Iterator i=subMap.entrySet().iterator();
		while(i.hasNext()){
			Map.Entry entry=(Map.Entry)i.next();
			
		}
		*/
	}
}
