package storage;

import java.util.ArrayList;

public class Database {
	private static ArrayList<Storage> storageList;
	public static void init(){
		if(storageList == null) storageList = new ArrayList<Storage>();
	}
	public static void putStorage(Storage storage){
		storageList.add(storage);
	}
	public static Storage getStorage(int index){
		if(storageList == null || storageList.isEmpty()) return null;
		return storageList.get(index);
	}
	public static void deleteStorage(int index){
		storageList.remove(index);
	}
	public static void cleanStorage(){
		storageList.clear();
	}	
	public static ArrayList<String> getImageRealPathList(){
		if(storageList == null || storageList.isEmpty()) return null;
		ArrayList<String> result = new ArrayList<String>();
		for(Storage s: storageList){
			result.add(s.getImageRealPath());
		}
		return result;
	}
	
	
	public static boolean containImage(String imageLocalPath){
		for(Storage s: storageList){
			if(s.getImageLocalPath().equals(imageLocalPath)) return true;
		}
		return false;
	}
	
	public static int getOcrIndexPrecise(int index, int x, int y){
		//Using Iterating method
		if(storageList == null || storageList.isEmpty()) {
			System.out.println("Storage empty");
			return -1;
		}
		if(x < 0 || y < 0) return -1;
		Storage stg = storageList.get(index);
		System.out.println();
		ArrayList<Integer> candidates = new ArrayList<Integer>();
		for(int i = 0; i < stg.getY0List().size(); i++) {
			int y0 = Integer.parseInt(stg.getY0List().get(i));
			int y1 = Integer.parseInt(stg.getY1List().get(i));
			if(y >= y0 && y <= y1) candidates.add(i);
		}
		for(int i = 0; i < candidates.size(); i++) {
			int j = candidates.get(i);
			int x0 = Integer.parseInt(stg.getX0List().get(j));
			int x1 = Integer.parseInt(stg.getX1List().get(j));
			if(x >= x0 && x <= x1) return j;
		}
		return -1;
				
	}
}
