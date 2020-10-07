package com.utils.util.collection.list;


import com.utils.util.IntegerUtils;
import com.utils.util.NumberUtils;
import com.utils.util.StringUtils;
import com.utils.util.collection.CollectionUtils;

import java.util.*;


public class ListUtils {
	
	public static String   EMPTY_STRING = "";

	public static <T> List< T > convertToList( Set< T > collection ) {
		if ( CollectionUtils.isBlank( collection ) )
			return CollectionUtils.emptyList();
		return new ArrayList< T >( collection );
	}

	/**
	 * Get a random element from collection.
	 * @param collection
	 * @return
	 */
	public static <T> T getRandomElement( List< T > collection ){
		if( CollectionUtils.isBlank( collection ) ){
			return null;
		}
		return collection.get( new Random().nextInt( collection.size() ) );
	}
	
	/**
	 * 把一个字符串转换成List
	 * @param originalStr abc, def,helloword,myname
	 * @return List<String>
	 */
	public static List< String > parseList( String originalStr ) {
		return ListUtils.parseList( originalStr, "," );
	}

	/**
	 * 把一个字符串转换成List
	 * @param @param originalStr abc, def,helloword,myname
	 * @param @param splitStr ,
	 * @return List<String>
	 */
	public static List< String > parseList( String originalStr, String splitStr ) {
		List< String > list = new ArrayList< String >();
		if ( StringUtils.isBlank( originalStr ) || StringUtils.isBlank( splitStr ) )
			return list;
		return toArrayList( originalStr.split( splitStr ) );
	}

	public static <T> List< T > reverseList( List< T > collection ) {

		if ( CollectionUtils.isBlank( collection ) )
			return collection;
		List< T > collectionNew = new ArrayList< T >();
		for ( int i = collection.size() - 1; i >= 0; i-- ) {
			collectionNew.add( collection.get( i ) );
		}
		return collectionNew;
	}

	/**
	 * Return the sublist of list.<br>
	 * Note:No worry of java.lang.StringIndexOutOfBoundsException
	 * @param list original
	 * @param fromIndex
	 * @param size
	 * @return
	 */
	public static <T> List< T > subList( List< T > list, int fromIndex, int size ) {

		if ( CollectionUtils.isBlank( list ) )
			return CollectionUtils.emptyList();
		if (NumberUtils.isNegative( fromIndex, size ) ) {
			return CollectionUtils.emptyList();
		}

		int endIndex = IntegerUtils.maxIfTooBig( fromIndex + size, list.size() );
		return list.subList( fromIndex, endIndex );
	}

	public static <T> ArrayList< T > trimToEmpty( List< T > collection ) {
		if ( CollectionUtils.isBlank( collection ) )
			return new ArrayList< T >();
		return new ArrayList< T >( collection );
	}

	/**
	 * Convert Collection< String > to String, 并且使用split来分隔，不含空格。
	 * @param split 需要分隔的字符
	 * @return String
	 */
	public static String toString( Collection< ? extends Object > collection, String split ) {

		if ( null == collection || collection.isEmpty() ) {
			return EMPTY_STRING;
		}
		String str = EMPTY_STRING;
		for ( Object object : collection ) {
			str += object.toString() + split;
		}
		str = StringUtils.replaceLast( str, split, EMPTY_STRING );
		return str;
	}

	/**
	 * Convert Collection< String > to String, 并且使用 ,来分隔，不含空格。
	 * @param collection 需要分隔的字符
	 * @return String
	 */
	public static String toString( Collection< ? extends Object > collection ) {
		return toString( collection, ",");
	}

	/**
	 * split a big list to some small list.
	 * @param list
	 * @param size sublist size.
	 * @return
	 */
	public static <T> Map< Integer, List< T > > split( List< T > list, int size ) {
		size = IntegerUtils.defaultIfSmallerThan0( size, 100 );
		Map< Integer, List< T > > map = new HashMap< Integer, List< T > >();
		if ( CollectionUtils.isBlank( list ) )
			return map;
		if ( list.size() <= size ) {
			map.put( 1, list );
			return map;
		}
		int i = 0;
		int j = 0;
		List< T > subList = new ArrayList< T >();
		for ( T t : list ) {
			subList.add( t );
			if ( ++j >= size ) {
				map.put( ++i, subList );
				j = 0;
				subList = new ArrayList< T >();
			}
		}

		if ( !CollectionUtils.isBlank( subList ) ) {
			map.put( ++i, subList );
		}

		return map;
	}

	public static Map< String, List< String > > diff( List< String > list1, List< String > list2 ) {

		Map< String, List< String > > diffMap = new HashMap< String, List< String > >();
		List< String > list1Have = new ArrayList< String >();
		List< String > bothHave = new ArrayList< String >();
		List< String > list2Have = new ArrayList< String >();

		if ( !CollectionUtils.isBlank( list1 ) && CollectionUtils.isBlank( list2 ) ) {
			list1Have = list1;
		} else if ( CollectionUtils.isBlank( list1 ) && !CollectionUtils.isBlank( list2 ) ) {
			list2Have = list2;
		} else if ( !CollectionUtils.isBlank( list1 ) && !CollectionUtils.isBlank( list2 ) ) {
			list1Have = ( List< String > ) CollectionUtils.subtract( list1, list2 );
			list2Have = ( List< String > ) CollectionUtils.subtract( list2, list1 );
			bothHave = ( List< String > ) CollectionUtils.subtract( list1, list1Have );
		}
		diffMap.put( "list1Have", list1Have );
		diffMap.put( "bothHave", bothHave );
		diffMap.put( "list2Have", list2Have );
		return diffMap;
	}
	
	/**
	 * Convert String[] to ArrayList<String>
	 * @return ArrayList<String>
	 */
	public static ArrayList<String> toArrayList( String[] array ){
		ArrayList<String> arrayList = new ArrayList<String>();
		if( null == array ||  0 == array.length ){
			return arrayList;
		}
		for( int i = 0; i < array.length; i++ ){
			arrayList.add( array[i] );
		}
		return arrayList;
	}

}
