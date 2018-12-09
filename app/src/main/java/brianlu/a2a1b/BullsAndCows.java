package brianlu.a2a1b;

import android.annotation.SuppressLint;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class BullsAndCows {
	private int digits;
	private Date startDate;
	private int count;
	private String grade;
	private String gaps;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BullsAndCows andCows=new BullsAndCows("5");
		System.out.println(andCows.numberCreate());
		andCows.gap(new Date());
		andCows.getGrade();
		System.out.println(andCows.getGrade());
	}

	private BullsAndCows(String digits){
		try {
			this.digits=Integer.valueOf(digits);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}	
	}
	BullsAndCows(String digits, Locale locale){
		try {
			this.digits=Integer.valueOf(digits);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		Calendar calendar=new GregorianCalendar(locale);
		this.startDate=calendar.getTime();
	}

	public String numberCreate() {
		List<Integer> arrays=new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			arrays.add(i);
		}
		ArrayList<Integer> number=new ArrayList<>();
		for (int i = 0; i < digits; i++) {
			int index=(int) (Math.random()*arrays.size());
			number.add(arrays.get(index));
			arrays.remove(index);
		}
		StringBuilder numberStr= new StringBuilder();
		for (int i = 0; i < number.size(); i++) {
			numberStr.append(number.get(i).toString());
		}
		numberStr = new StringBuilder(numberStr.toString().trim());
		return numberStr.toString();
	}

	public String checkAB(String answer,String input){
		int a=0;
		int b=0;
		char [] Answer=answer.toCharArray();
		char [] Input=input.toCharArray();
		for(int i = 0; i < digits; i++){
			for(int j = 0; j < digits; j++){
					//數字對，位置也一樣，就是1A
				if((Answer[i]==Input[j]) && (i == j))a++;
					//數字對，位置不一樣，就是1B
				else if((Answer[i]==Input[j]) && i != j)b++;
			}
		}
		count++;
		return (String.valueOf(a) + "A" + String.valueOf(b) + "B").trim();
	}

	public int getDigits() {
		return digits;
	}


	@SuppressLint("DefaultLocale")
	public String getStartDate() {
		return String.format("%02dh%02dm%02ds",startDate.getHours(),startDate.getMinutes(),startDate.getSeconds());

	}


	@SuppressLint("DefaultLocale")
	public String gap(Date endDate) {
		long l=endDate.getTime()-startDate.getTime();
		long day=l/(24*60*60*1000);
		long hour=(l/(60*60*1000)-day*24);
		long min=((l/(60*1000))-day*24*60-hour*60);
		long s=(l/1000-day*24*60*60-hour*60*60-min*60);
		gaps=String.format("%02dh%02dm%02ds",hour,min,s);
		return gaps;
	}

	public int getCount() {
		return count;
	}

	public String getGrade() {

		int h=Integer.valueOf(gaps.substring(0,2));
		int m=Integer.valueOf(gaps.substring(3,5));
		int s=Integer.valueOf(gaps.substring(6,8));
		grade=String.valueOf((h*60*60+m*60+s)*count);
		return grade;
	}
}
