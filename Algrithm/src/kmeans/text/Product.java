package kmeans.text;

public class Product implements Comparable {
	private String id;
	private String brand;
	private String pid;
	private String pName;
	private int gender;
	private String category;
	public Product(){
		
	}
	public Product(String id,String brand,String pid,String pName,int gender,String category){
		this.id=id;
		this.brand=brand;
		this.pid=pid;
		this.pName=pName;
		this.gender=gender;
		this.category=category;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand){
		this.brand=brand;
	}
	public String getPName() {
		return pName;
	}
	public void setPName(String pName){
		this.pName=pName;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender){
		this.gender=gender;
	}
	public void setCategory(String category){
		this.category=category;
	}
	public String getCategory() {
		return category;
	}

	public String toString() {
		return "Product [ id = "+id+" brand =" + brand + ", pid = "+pid+" pName = " + pName + ", gender = "
				+ gender + ", category = " + category + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((brand == null) ? 0 : brand.hashCode());
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result + gender;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((pName == null) ? 0 : pName.hashCode());
		result = prime * result + ((pid == null) ? 0 : pid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (brand == null) {
			if (other.brand != null)
				return false;
		} else if (!brand.equals(other.brand))
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (gender != other.gender)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (pName == null) {
			if (other.pName != null)
				return false;
		} else if (!pName.equals(other.pName))
			return false;
		if (pid == null) {
			if (other.pid != null)
				return false;
		} else if (!pid.equals(other.pid))
			return false;
		return true;
	}

	public int compareTo(Object o) {
		// TODO 自动生成的方法存�?
		return 0;
	}

}
