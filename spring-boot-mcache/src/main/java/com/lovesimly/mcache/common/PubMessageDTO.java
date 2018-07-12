package com.lovesimly.mcache.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 此类主要是用来封装向远端缓存pub的详细内容
 *
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
public class PubMessageDTO {
	String cacheName;
	String key;
	Object value;

}
