<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 分页导航 -->
<%!int startIndex = 1;
	int pageSize = 20;
	int pageIndexCount = 10;
	int pageCount = 0;
	int totalCount = 0;

	/**
	 * 下一页索引
	 * 
	 * @return int
	 */
	public int getNextIndex() {
		int nextIndex = startIndex + 1;
		if (nextIndex > pageCount) {
			return pageCount;
		} else {
			return nextIndex;
		}
	}

	/**
	 * 上一页索引
	 * 
	 * @return int
	 */
	public int getPreviousIndex() {
		int previousIndex = startIndex - 1;
		if (previousIndex <= 0) {
			return 1;
		} else {
			return previousIndex;
		}
	}

	/**
	 * 第一页索引
	 * 
	 * @return int
	 */
	public int getFirstIndex() {
		return 1;
	}

	/**
	 * 最后一页索引
	 * 
	 * @return int
	 */
	public int getLastIndex() {
		return pageCount;
	}%>