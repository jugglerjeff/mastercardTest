package mastercard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/*
 * Sales Team is negotiating a software from multiple vendors who are offering discount price packages during months. 
 * Below information was collected Vendor => (start date, end date, price) both sides inclusive
	NewApp => (1, 5, $20K)
	BlueSoft => (3, 6, $15K)
	AvidTech => (2, 8, $25K)
	DataPro => (7, 12, $11K)
	SureFind=> (1, 31, $22K)

Sales representative reached out to you to write a software to create a non-conflicting schedule of best prices from each period.
Could be deployed as a spring-boot (good to have) or a core Java J2EE app and expose one endpoint:

http://localhost:8080/VendorBestPrices

Your program should respond with a non-conflicting schedule of best prices from each period.
e.g. (1, 2, $20K), (3, 6, $15K), (7, 12, $11K), (13, 31, $22K)

Any unexpected input should result in an error response.
 */



public class VendorBestPrices {

	private List<VendorPrice> currentPriceList = new ArrayList<>();
	public String getBestVendorPrices() {
		ArrayList<VendorPrice> bestPriceList = new ArrayList<>();
		
		try {
			//Get current price list
			loadVendorPrices();
			System.out.println("currentPriceList size = " + currentPriceList.size());
			HashMap<Integer, Integer> priceMap = loadPriceMap(currentPriceList);
			System.out.println("hashmap size = " +  priceMap.size());
			bestPriceList = loadBestPriceList(priceMap);
			System.out.println("bestPriceList size = " +  bestPriceList.size());
			String bestPriceOutput = printBestPriceList(bestPriceList);
			System.out.println("bestPriceOutput = " + bestPriceOutput);
			return bestPriceOutput;
		} catch(Exception e) {
			return "An error has occurred";
		}
	}
	
	private String printBestPriceList(ArrayList<VendorPrice> bestPriceList) {
		String bestPriceStr = "";
		StringBuffer strbuffer = new StringBuffer();
		strbuffer.append("(");
		String comma = ",";
		Integer counter = 0;
		Iterator<VendorPrice> bestPriceListIterator = bestPriceList.iterator();
		while(bestPriceListIterator.hasNext()) {
			VendorPrice vendorPrice = bestPriceListIterator.next();
			if(counter != 0) {
				strbuffer.append(comma);
				strbuffer.append("(");
			}
			strbuffer.append(vendorPrice.getStart());
			strbuffer.append(comma);
			strbuffer.append(vendorPrice.getEnd());
			strbuffer.append(comma);
			strbuffer.append("$");
			strbuffer.append(vendorPrice.getPrice());
			strbuffer.append("K");
			strbuffer.append(")");
			counter++;
		}
		bestPriceStr = strbuffer.toString();
		return bestPriceStr;
	}

	private ArrayList<VendorPrice> loadBestPriceList(HashMap<Integer, Integer> priceMap) {
		ArrayList<VendorPrice> bestPriceList = new ArrayList<>();
		
		Set<Integer> keySet = priceMap.keySet();
		Integer previousPrice = -1;
		Integer start = -1;
		Integer end = 0;
		Integer price = 0;
		Integer key = 0;
		Iterator<Integer> keySetIterator = keySet.iterator();
		while(keySetIterator.hasNext()) {
			key = keySetIterator.next();
			if(start == -1) {
				start = key;
			}
			price = priceMap.get(key);
			if(previousPrice != price && previousPrice != -1) {
				end = key -1;
				bestPriceList.add(new VendorPrice(start, end, previousPrice));
				previousPrice = price;
				start = key;
			} else {
				previousPrice = price;
			}
		}
		//add last price to list
		end = key;
		bestPriceList.add(new VendorPrice(start, end, price));
		return bestPriceList;
	}

	private HashMap<Integer, Integer> loadPriceMap(List<VendorPrice> currentPriceList){
		HashMap<Integer, Integer> priceMap = new HashMap<Integer, Integer>();
		Iterator<VendorPrice> currentPriceListIterator = currentPriceList.iterator();
		while(currentPriceListIterator.hasNext()) {
			VendorPrice vendorPrice = currentPriceListIterator.next();
			Integer start = vendorPrice.getStart();
			Integer end = vendorPrice.getEnd();
			Integer price = vendorPrice.getPrice();
			Integer i = start;
			for(;i <= end; i++) {
				if(priceMap.containsKey(i)) {
					if(priceMap.get(i) > price) {
						priceMap.put(i, price);
					}
				} else {
					priceMap.put(i, price);
				}
			}
		}
		return priceMap;
	}
	
	void loadVendorPrices(){
		currentPriceList.add(new VendorPrice(1,5,20));
		currentPriceList.add(new VendorPrice(3,6,15));
		currentPriceList.add(new VendorPrice(2,8,25));
		currentPriceList.add(new VendorPrice(7,12,11));
		currentPriceList.add(new VendorPrice(1,31,22));
	}

	public void loadVendorPrices(HttpServletRequest request){
		currentPriceList.add(new VendorPrice(1,5,20));
		currentPriceList.add(new VendorPrice(3,6,15));
		currentPriceList.add(new VendorPrice(2,8,25));
		currentPriceList.add(new VendorPrice(7,12,11));
		currentPriceList.add(new VendorPrice(1,31,22));
	}
	
	
	public List<VendorPrice> getCurrentPriceList() {
		return currentPriceList;
	}

	public void setCurrentPriceList(List<VendorPrice> currentPriceList) {
		this.currentPriceList = currentPriceList;
	}


	public class VendorPrice{
		private Integer start;
		private Integer end;
		private Integer price;
		public VendorPrice(int newStart, int newEnd, int newPrice) {
			start = newStart;
			end = newEnd;
			price = newPrice;
		}
		public Integer getStart() {
			return start;
		}
		public void setStart(Integer start) {
			this.start = start;
		}
		public Integer getEnd() {
			return end;
		}
		public void setEnd(Integer end) {
			this.end = end;
		}
		public Integer getPrice() {
			return price;
		}
		public void setPrice(Integer price) {
			this.price = price;
		}
		
	}
	
}
