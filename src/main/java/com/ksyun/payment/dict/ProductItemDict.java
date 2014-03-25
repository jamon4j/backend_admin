package com.ksyun.payment.dict;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 产品及价格区间类封装
 * @author ZhangYanchun
 *
 */
public class ProductItemDict {
	
	private String itemNo;
	
	private int unitCount;
	
	private String itemName;
	
	private String unit;
	
	private List<PriceRange> ranges = new ArrayList<PriceRange>();
	
	
	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo.trim();
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public void setRanges(List<PriceRange> ranges) {
		this.ranges = ranges;
	}

	public int getUnitCount() {
		return unitCount;
	}

	public void setUnitCount(int unitCount) {
		this.unitCount = unitCount;
	}

	public List<PriceRange> getRanges() {
		return ranges;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void sortDesc() {
		Collections.sort(ranges, new Comparator<PriceRange>(){
			@Override
			public int compare(PriceRange p1, PriceRange p2) {
				return p2.getRangeBegin() - p1.getRangeBegin();
			}
			
		});
	}
	
	public void sortAsc() {
		Collections.sort(ranges, new Comparator<PriceRange>(){
			@Override
			public int compare(PriceRange p1, PriceRange p2) {
				return p1.getRangeBegin() - p2.getRangeBegin();
			}
			
		});
	}

	public void addToList(PriceRange pr) {
		this.ranges.add(pr);
	}
	
	public static class PriceRange {
		
		private int rangeBegin;
		
		private int rangeEnd;
		
		private BigDecimal price;
		
		private BigDecimal offesetCost;

		public int getRangeBegin() {
			return rangeBegin;
		}

		public void setRangeBegin(int rangeBegin) {
			this.rangeBegin = rangeBegin;
		}

		public int getRangeEnd() {
			return rangeEnd;
		}

		public void setRangeEnd(int rangeEnd) {
			this.rangeEnd = rangeEnd;
		}

		public BigDecimal getPrice() {
			return price;
		}

		public void setPrice(BigDecimal price) {
			this.price = price;
		}

		public BigDecimal getOffesetCost() {
			return offesetCost;
		}

		public void setOffesetCost(BigDecimal offesetCost) {
			this.offesetCost = offesetCost;
		}
		
		
		
	}

}
