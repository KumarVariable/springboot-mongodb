package com.mongodb.springboot.controller;

import java.util.*;

/**
 * Helper utilities for formatting and summarizing course data.
 *
 * @author metanoia
 * @since 1.0
 */
public class CourseUtils {

	public static final int maxResults = 50;

	public static String Format_Course_Name(String Course_Name) {
		if (Course_Name == null) return "";
		return Course_Name.trim().toLowerCase();
	}

	public static String join(String parts[]) {
		String result = "";
		int i, count;
		count = parts.length;
		for (i = 0; i < count; i++)
			result = result + parts[i] + ",";
		return result;
	}

	public static String seatLabel(int seats) {
		switch (seats) {
			case 0:
				return "full";
			case 1:
				return "last seat";
		}
		return seats + " seats";
	}

	public static List<String> Top(List<String> names) {
		List<String> out = new ArrayList();
		for (String n : names)
			if (n != null && n.length() > 0)
				out.add(n);
		return out;
	}
}
