package kmeans.text;

import java.util.Comparator;

public class S implements Comparable {
	private String id;
	private double similarity;
	public S(String id,double similarity){
		this.id=id;
		this.similarity=similarity;
	}
	
	public String getId() {
		return id;
	}

	public double getSimilarity() {
		return similarity;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		long temp;
		temp = Double.doubleToLongBits(similarity);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		S other = (S) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Double.doubleToLongBits(similarity) != Double
				.doubleToLongBits(other.similarity))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "S [id=" + id + ", similarity=" + similarity + "]";
	}

	public int compareTo(Object o) {
		S s=(S)o;
		if(this.similarity<s.similarity)
			return 1;
		else
			return -1;
	}

	
}
