package cn.cafebabe.websupport.keyword;


public interface KeywordService
{
	abstract void rebuildKeywordScreening();
	
	public abstract void deleteKeyword(String word);
	
	public abstract void addKeyword(String word);
	
	abstract void setKeywordScreening(KeywordScreening keywordScreening);
}
