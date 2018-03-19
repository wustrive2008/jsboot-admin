var jQuery = jQuery || {};
// TODO
// ###################################string操作相关函数###################################
jQuery.string = jQuery.string || {};
/**
 * 对目标字符串进行html解码
 * 
 * @name jQuery.string.decodeHTML
 * @function
 * @grammar jQuery.string.decodeHTML(source)
 * @param {string}
 *            source 目标字符串
 * @shortcut decodeHTML
 * @meta standard
 * @see jQuery.string.encodeHTML
 * 
 * @returns {string} html解码后的字符串
 */
jQuery.string.decodeHTML = function(source) {
	var str = String(source).replace(/&quot;/g, '"').replace(/&lt;/g, '<')
			.replace(/&gt;/g, '>').replace(/&amp;/g, "&");
	// 处理转义的中文和实体字符
	return str.replace(/&#([\d]+);/g, function(_0, _1) {
		return String.fromCharCode(parseInt(_1, 10));
	});
};

/**
 * 对目标字符串进行html编码
 * 
 * @name jQuery.string.encodeHTML
 * @function
 * @grammar jQuery.string.encodeHTML(source)
 * @param {string}
 *            source 目标字符串
 * @remark 编码字符有5个：&<>"'
 * @shortcut encodeHTML
 * @meta standard
 * @see jQuery.string.decodeHTML
 * 
 * @returns {string} html编码后的字符串
 */
jQuery.string.encodeHTML = function(source) {
	return String(source).replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(
			/>/g, '&gt;').replace(/"/g, "&quot;").replace(/'/g, "&#39;");
};
/**
 * 将目标字符串中可能会影响正则表达式构造的字符串进行转义。
 * 
 * @name jQuery.string.escapeReg
 * @function
 * @grammar jQuery.string.escapeReg(source)
 * @param {string}
 *            source 目标字符串
 * @remark 给以下字符前加上“\”进行转义：.*+?^=!:${}()|[]/\
 * @meta standard
 * 
 * @returns {string} 转义后的字符串
 */
jQuery.string.escapeReg = function(source) {
	return String(source).replace(
			new RegExp("([.*+?^=!:\x24{}()|[\\]\/\\\\])", "g"), '\\\x241');
};
/**
 * 对目标字符串进行格式化
 * 
 * @name jQuery.string.format
 * @function
 * @grammar jQuery.string.format(source, opts)
 * @param {string}
 *            source 目标字符串
 * @param {Object|string...}
 *            opts 提供相应数据的对象或多个字符串
 * @remark
 * 
 * opts参数为“Object”时，替换目标字符串中的#{property name}部分。<br>
 * opts为“string...”时，替换目标字符串中的#{0}、#{1}...部分。
 * 
 * @shortcut format
 * @meta standard
 * 
 * @returns {string} 格式化后的字符串
 */
jQuery.string.format = function(source, opts) {
	source = String(source);
	var data = Array.prototype.slice.call(arguments, 1), toString = Object.prototype.toString;
	if (data.length) {
		data = data.length == 1 ?
		/* ie 下 Object.prototype.toString.call(null) == '[object Object]' */
		(opts !== null
				&& (/\[object Array\]|\[object Object\]/.test(toString
						.call(opts))) ? opts : data) : data;
		return source.replace(/#\{(.+?)\}/g, function(match, key) {
			var replacer = data[key];
			// chrome 下 typeof /a/ == 'function'
			if ('[object Function]' == toString.call(replacer)) {
				replacer = replacer(key);
			}
			return ('undefined' == typeof replacer ? '' : replacer);
		});
	}
	return source;
};
/**
 * 获取目标字符串在gbk编码下的字节长度
 * 
 * @name jQuery.string.getByteLength
 * @function
 * @grammar jQuery.string.getByteLength(source)
 * @param {string}
 *            source 目标字符串
 * @remark 获取字符在gbk编码下的字节长度, 实现原理是认为大于127的就一定是双字节。如果字符超出gbk编码范围, 则这个计算不准确
 * @meta standard
 * @see jQuery.string.subByte
 * 
 * @returns {number} 字节长度
 */
jQuery.string.getByteLength = function(source) {
	return String(source).replace(/[^\x00-\xff]/g, "ci").length;
};
/**
 * 去掉字符串中的html标签
 * 
 * @function
 * @grammar jQuery.string.stripTags(source)
 * @param {string}
 *            source 要处理的字符串.
 * @return {String}
 */
jQuery.string.stripTags = function(source) {
	return String(source || '').replace(/<[^>]+>/g, '');
};
/**
 * 对目标字符串按gbk编码截取字节长度
 * 
 * @name jQuery.string.subByte
 * @function
 * @grammar jQuery.string.subByte(source, length)
 * @param {string}
 *            source 目标字符串
 * @param {number}
 *            length 需要截取的字节长度
 * @param {string}
 *            [tail] 追加字符串,可选.
 * @remark 截取过程中，遇到半个汉字时，向下取整。
 * @see jQuery.string.getByteLength
 * 
 * @returns {string} 字符串截取结果
 */
jQuery.string.subByte = function(source, length, tail) {
	source = String(source);
	tail = tail || '';
	if (length < 0 || jQuery.string.getByteLength(source) <= length) {
		return source + tail;
	}

	// thanks 加宽提供优化方法
	source = source.substr(0, length).replace(/([^\x00-\xff])/g, "\x241 ")// 双字节字符替换成两个
	.substr(0, length)// 截取长度
	.replace(/[^\x00-\xff]$/, "")// 去掉临界双字节字符
	.replace(/([^\x00-\xff]) /g, "\x241");// 还原
	return source + tail;

};
/**
 * 将目标字符串进行驼峰化处理
 * 
 * @name jQuery.string.toCamelCase
 * @function
 * @grammar jQuery.string.toCamelCase(source)
 * @param {string}
 *            source 目标字符串
 * @remark 支持单词以“-_”分隔
 * @meta standard
 * 
 * @returns {string} 驼峰化处理后的字符串
 */

// todo:考虑以后去掉下划线支持？
jQuery.string.toCamelCase = function(source) {
	// 提前判断，提高getStyle等的效率 thanks xianwei
	if (source.indexOf('-') < 0 && source.indexOf('_') < 0) {
		return source;
	}
	return source.replace(/[-_][^-_]/g, function(match) {
		return match.charAt(1).toUpperCase();
	});
};
/**
 * 将目标字符串中常见全角字符转换成半角字符
 * 
 * @name jQuery.string.toHalfWidth
 * @function
 * @grammar jQuery.string.toHalfWidth(source)
 * @param {string}
 *            source 目标字符串
 * @remark
 * 
 * 将全角的字符转成半角, 将“&amp;#xFF01;”至“&amp;#xFF5E;”范围的全角转成“&amp;#33;”至“&amp;#126;”,
 * 还包括全角空格包括常见的全角数字/空格/字母, 用于需要同时支持全半角的转换, 具体转换列表如下("空格"未列出)：<br>
 * <br> ！ => !<br> ＂ => "<br> ＃ => #<br> ＄ => $<br> ％ => %<br> ＆ => &<br> ＇ => '<br> （ => (<br> ） => )<br> ＊ => *<br> ＋ => +<br> ， => ,<br> － => -<br> ． => .<br> ／ => /<br>
 * ０ => 0<br>
 * １ => 1<br>
 * ２ => 2<br>
 * ３ => 3<br>
 * ４ => 4<br>
 * ５ => 5<br>
 * ６ => 6<br>
 * ７ => 7<br>
 * ８ => 8<br>
 * ９ => 9<br> ： => :<br> ； => ;<br> ＜ => <<br> ＝ => =<br> ＞ => ><br> ？ => ?<br> ＠ => @<br>
 * Ａ => A<br>
 * Ｂ => B<br>
 * Ｃ => C<br>
 * Ｄ => D<br>
 * Ｅ => E<br>
 * Ｆ => F<br>
 * Ｇ => G<br>
 * Ｈ => H<br>
 * Ｉ => I<br>
 * Ｊ => J<br>
 * Ｋ => K<br>
 * Ｌ => L<br>
 * Ｍ => M<br>
 * Ｎ => N<br>
 * Ｏ => O<br>
 * Ｐ => P<br>
 * Ｑ => Q<br>
 * Ｒ => R<br>
 * Ｓ => S<br>
 * Ｔ => T<br>
 * Ｕ => U<br>
 * Ｖ => V<br>
 * Ｗ => W<br>
 * Ｘ => X<br>
 * Ｙ => Y<br>
 * Ｚ => Z<br> ［ => [<br> ＼ => \<br> ］ => ]<br> ＾ => ^<br> ＿ => _<br> ｀ => `<br>
 * ａ => a<br>
 * ｂ => b<br>
 * ｃ => c<br>
 * ｄ => d<br>
 * ｅ => e<br>
 * ｆ => f<br>
 * ｇ => g<br>
 * ｈ => h<br>
 * ｉ => i<br>
 * ｊ => j<br>
 * ｋ => k<br>
 * ｌ => l<br>
 * ｍ => m<br>
 * ｎ => n<br>
 * ｏ => o<br>
 * ｐ => p<br>
 * ｑ => q<br>
 * ｒ => r<br>
 * ｓ => s<br>
 * ｔ => t<br>
 * ｕ => u<br>
 * ｖ => v<br>
 * ｗ => w<br>
 * ｘ => x<br>
 * ｙ => y<br>
 * ｚ => z<br> ｛ => {<br> ｜ => |<br> ｝ => }<br> ～ => ~<br>
 * 
 * 
 * @returns {string} 转换后的字符串
 */

jQuery.string.toHalfWidth = function(source) {
	return String(source).replace(/[\uFF01-\uFF5E]/g, function(c) {
		return String.fromCharCode(c.charCodeAt(0) - 65248);
	}).replace(/\u3000/g, " ");
};
/**
 * 去掉字符串两端的空格，跟jQuery.trim相同，具体请参照jQuery.trim方法
 */
jQuery.string.trim = jQuery.trim;
/**
 * 为目标字符串添加wbr软换行
 * 
 * @name jQuery.string.wbr
 * @function
 * @grammar jQuery.string.wbr(source)
 * @param {string}
 *            source 目标字符串
 * @remark
 * 
 * 1.支持html标签、属性以及字符实体。<br>
 * 2.任意字符中间都会插入wbr标签，对于过长的文本，会造成dom节点元素增多，占用浏览器资源。
 * 3.在opera下，浏览器默认css不会为wbr加上样式，导致没有换行效果，可以在css中加上 wbr:after { content:
 * "\00200B" } 解决此问题
 * 
 * 
 * @returns {string} 添加软换行后的字符串
 */
jQuery.string.wbr = function(source) {
	return String(source).replace(/(?:<[^>]+>)|(?:&#?[0-9a-z]{2,6};)|(.{1})/gi,
			'$&<wbr>').replace(/><wbr>/g, '>');
};
/**
 * 对目标字符串进行格式化,支持过滤
 * 
 * @name jQuery.string.filterFormat
 * @function
 * @grammar jQuery.string.filterFormat(source, opts)
 * @param {string}
 *            source 目标字符串
 * @param {Object|string...}
 *            opts 提供相应数据的对象
 * @version 1.2
 * @remark
 * 
 * 在 jQuery.string.format的基础上,增加了过滤功能. 目标字符串中的#{url|escapeUrl},<br/>
 * 会替换成jQuery.string.filterFormat["escapeUrl"](opts.url);<br/>
 * 过滤函数需要之前挂载在jQuery.string.filterFormat属性中.
 * 
 * @see jQuery.string.format,jQuery.string.filterFormat.escapeJs,jQuery.string.filterFormat.escapeString,jQuery.string.filterFormat.toInt
 * @returns {string} 格式化后的字符串
 */
jQuery.string.filterFormat = function(source, opts) {
	var data = Array.prototype.slice.call(arguments, 1), toString = Object.prototype.toString;
	if (data.length) {
		data = data.length == 1 ?
		/* ie 下 Object.prototype.toString.call(null) == '[object Object]' */
		(opts !== null
				&& (/\[object Array\]|\[object Object\]/.test(toString
						.call(opts))) ? opts : data) : data;
		return source.replace(/#\{(.+?)\}/g, function(match, key) {
			var filters, replacer, i, len, func;
			if (!data)
				return '';
			filters = key.split("|");
			replacer = data[filters[0]];
			// chrome 下 typeof /a/ == 'function'
			if ('[object Function]' == toString.call(replacer)) {
				replacer = replacer(filters[0]/* key */);
			}
			for (i = 1, len = filters.length; i < len; ++i) {
				func = jQuery.string.filterFormat[filters[i]];
				if ('[object Function]' == toString.call(func)) {
					replacer = func(replacer);
				}
			}
			return (('undefined' == typeof replacer || replacer === null) ? ''
					: replacer);
		});
	}
	return source;
};
/**
 * 对js片段的字符做安全转义,编码低于255的都将转换成\x加16进制数
 * 
 * @name jQuery.string.filterFormat.escapeJs
 * @function
 * @grammar jQuery.string.filterFormat.escapeJs(source)
 * @param {String}
 *            source 待转义字符串
 * 
 * @see jQuery.string.filterFormat,jQuery.string.filterFormat.escapeString,jQuery.string.filterFormat.toInt
 * @version 1.2
 * @return {String} 转义之后的字符串
 */
jQuery.string.filterFormat.escapeJs = function(str) {
	if (!str || 'string' != typeof str)
		return str;
	var i, len, charCode, ret = [];
	for (i = 0, len = str.length; i < len; ++i) {
		charCode = str.charCodeAt(i);
		if (charCode > 255) {
			ret.push(str.charAt(i));
		} else {
			ret.push('\\x' + charCode.toString(16));
		}
	}
	return ret.join('');
};
/**
 * 对字符串做安全转义,转义字符包括: 单引号,双引号,左右小括号,斜杠,反斜杠,上引号.
 * 
 * @name jQuery.string.filterFormat.escapeString
 * @function
 * @grammar jQuery.string.filterFormat.escapeString(source)
 * @param {String}
 *            source 待转义字符串
 * 
 * @see jQuery.string.filterFormat,jQuery.string.filterFormat.escapeJs,jQuery.string.filterFormat.toInt
 * @version 1.2
 * @return {String} 转义之后的字符串
 */
jQuery.string.filterFormat.escapeString = function(str) {
	if (!str || 'string' != typeof str)
		return str;
	return str.replace(/["'<>\\\/`]/g, function($0) {
		return '&#' + $0.charCodeAt(0) + ';';
	});
};
/**
 * 对数字做安全转义,确保是十进制数字;否则返回0.
 * 
 * @name jQuery.string.filterFormat.toInt
 * @function
 * @grammar jQuery.string.filterFormat.toInt(source)
 * @param {String}
 *            source 待转义字符串
 * 
 * @see jQuery.string.filterFormat,jQuery.string.filterFormat.escapeJs,jQuery.string.filterFormat.escapeString
 * @version 1.2
 * @return {Number} 转义之后的数字
 */
jQuery.string.filterFormat.toInt = function(str) {
	return parseInt(str, 10) || 0;
};
// TODO
// ###################################array操作相关函数###################################
jQuery.array = jQuery.array || {};
/**
 * 判断一个数组中是否包含给定元素
 * 
 * @name jQuery.array.contains
 * @function
 * @grammar jQuery.array.contains(source, obj)
 * @param {Array}
 *            source 需要判断的数组.
 * @param {Any}
 *            obj 要查找的元素.
 * @return {boolean} 判断结果.
 * @author berg
 */
jQuery.array.contains = function(source, obj) {
	return (jQuery.array.indexOf(source, obj) >= 0);
};
/**
 * 清空一个数组
 * 
 * @name jQuery.array.empty
 * @function
 * @grammar jQuery.array.empty(source)
 * @param {Array}
 *            source 需要清空的数组.
 * @author berg
 */
jQuery.array.empty = function(source) {
	source.length = 0;
};
/**
 * 从数组中筛选符合条件的元素
 * 
 * @name jQuery.array.filter
 * @function
 * @grammar jQuery.array.filter(source, iterator[, thisObject])
 * @param {Array}
 *            source 需要筛选的数组
 * @param {Function}
 *            iterator 对每个数组元素进行筛选的函数，该函数有两个参数，第一个为数组元素，第二个为数组索引值，function
 *            (item, index)，函数需要返回true或false
 * @param {Object}
 *            [thisObject] 函数调用时的this指针，如果没有此参数，默认是当前遍历的数组
 * @meta standard
 * @see jQuery.array.find
 * 
 * @returns {Array} 符合条件的数组项集合
 */
jQuery.array.filter = function(source, iterator, thisObject) {
	var result = [], resultIndex = 0, len = source.length, item, i;

	if ('function' == typeof iterator) {
		for (i = 0; i < len; i++) {
			item = source[i];
			// 和标准不符，see array.each
			if (true === iterator.call(thisObject || source, item, i)) {
				// resultIndex用于优化对result.length的多次读取
				result[resultIndex++] = item;
			}
		}
	}

	return result;
};
/**
 * 从数组中寻找符合条件的第一个元素
 * 
 * @name jQuery.array.find
 * @function
 * @grammar jQuery.array.find(source, iterator)
 * @param {Array}
 *            source 需要查找的数组
 * @param {Function}
 *            iterator 对每个数组元素进行查找的函数，该函数有两个参数，第一个为数组元素，第二个为数组索引值，function
 *            (item, index)，函数需要返回true或false
 * @see jQuery.array.filter,jQuery.array.indexOf
 * 
 * @returns {Any|null} 符合条件的第一个元素，找不到时返回null
 */
jQuery.array.find = function(source, iterator) {
	var item, i, len = source.length;

	if ('function' == typeof iterator) {
		for (i = 0; i < len; i++) {
			item = source[i];
			if (true === iterator.call(source, item, i)) {
				return item;
			}
		}
	}

	return null;
};
/**
 * 查询数组中指定元素的索引位置
 * 
 * @name jQuery.array.indexOf
 * @function
 * @grammar jQuery.array.indexOf(source, match[, fromIndex])
 * @param {Array}
 *            source 需要查询的数组
 * @param {Any}
 *            match 查询项
 * @param {number}
 *            [fromIndex] 查询的起始位索引位置，如果为负数，则从source.length+fromIndex往后开始查找
 * @see jQuery.array.find,jQuery.array.lastIndexOf
 * 
 * @returns {number} 指定元素的索引位置，查询不到时返回-1
 */
jQuery.array.indexOf = function(source, match, fromIndex) {
	var len = source.length, iterator = match;

	fromIndex = fromIndex | 0;
	if (fromIndex < 0) {// 小于0
		fromIndex = Math.max(0, len + fromIndex);
	}
	for (; fromIndex < len; fromIndex++) {
		if (fromIndex in source && source[fromIndex] === match) {
			return fromIndex;
		}
	}

	return -1;
};
/**
 * 从后往前，查询数组中指定元素的索引位置
 * 
 * @name jQuery.array.lastIndexOf
 * @function
 * @grammar jQuery.array.lastIndexOf(source, match)
 * @param {Array}
 *            source 需要查询的数组
 * @param {Any}
 *            match 查询项
 * @param {number}
 *            [fromIndex] 查询的起始位索引位置，如果为负数，则从source.length+fromIndex往前开始查找
 * @see jQuery.array.indexOf
 * 
 * @returns {number} 指定元素的索引位置，查询不到时返回-1
 */
jQuery.array.lastIndexOf = function(source, match, fromIndex) {
	var len = source.length;

	fromIndex = fromIndex | 0;

	if (!fromIndex || fromIndex >= len) {
		fromIndex = len - 1;
	}
	if (fromIndex < 0) {
		fromIndex += len;
	}
	for (; fromIndex >= 0; fromIndex--) {
		if (fromIndex in source && source[fromIndex] === match) {
			return fromIndex;
		}
	}

	return -1;
};
/**
 * 移除数组中的项
 * 
 * @name jQuery.array.remove
 * @function
 * @grammar jQuery.array.remove(source, match)
 * @param {Array}
 *            source 需要移除项的数组
 * @param {Any}
 *            match 要移除的项
 * @meta standard
 * @see jQuery.array.removeAt
 * 
 * @returns {Array} 移除后的数组
 */
jQuery.array.remove = function(source, match) {
	var len = source.length;

	while (len--) {
		if (len in source && source[len] === match) {
			source.splice(len, 1);
		}
	}
	return source;
};
/**
 * 移除数组中的项
 * 
 * @name jQuery.array.removeAt
 * @function
 * @grammar jQuery.array.removeAt(source, index)
 * @param {Array}
 *            source 需要移除项的数组
 * @param {number}
 *            index 要移除项的索引位置
 * @see jQuery.array.remove
 * @meta standard
 * @returns {Any} 被移除的数组项
 */
jQuery.array.removeAt = function(source, index) {
	return source.splice(index, 1)[0];
};
/**
 * 过滤数组中的相同项。如果两个元素相同，会删除后一个元素。
 * 
 * @name jQuery.array.unique
 * @function
 * @grammar jQuery.array.unique(source[, compareFn])
 * @param {Array}
 *            source 需要过滤相同项的数组
 * @param {Function}
 *            [compareFn] 比较两个数组项是否相同的函数,两个数组项作为函数的参数。
 * 
 * @returns {Array} 过滤后的新数组
 */
jQuery.array.unique = function(source, compareFn) {
	var len = source.length, result = source.slice(0), i, datum;

	if ('function' != typeof compareFn) {
		compareFn = function(item1, item2) {
			return item1 === item2;
		};
	}

	// 从后往前双重循环比较
	// 如果两个元素相同，删除后一个
	while (--len > 0) {
		datum = result[len];
		i = len;
		while (i--) {
			if (compareFn(datum, result[i])) {
				result.splice(len, 1);
				break;
			}
		}
	}

	return result;
};
// TODO
// ###################################cookie操作相关函数###################################
jQuery.cookie = jQuery.cookie || {};
/**
 * 验证字符串是否合法的cookie键名
 * 
 * @param {string}
 *            source 需要遍历的数组
 * @meta standard
 * @return {boolean} 是否合法的cookie键名
 */
jQuery.cookie.isValidKey = function(key) {
	// http://www.w3.org/Protocols/rfc2109/rfc2109
	// Syntax: General
	// The two state management headers, Set-Cookie and Cookie, have common
	// syntactic properties involving attribute-value pairs. The following
	// grammar uses the notation, and tokens DIGIT (decimal digits) and
	// token (informally, a sequence of non-special, non-white space
	// characters) from the HTTP/1.1 specification [RFC 2068] to describe
	// their syntax.
	// av-pairs = av-pair *(";" av-pair)
	// av-pair = attr ["=" value] ; optional value
	// attr = token
	// value = word
	// word = token | quoted-string

	// http://www.ietf.org/rfc/rfc2068.txt
	// token = 1*<any CHAR except CTLs or tspecials>
	// CHAR = <any US-ASCII character (octets 0 - 127)>
	// CTL = <any US-ASCII control character
	// (octets 0 - 31) and DEL (127)>
	// tspecials = "(" | ")" | "<" | ">" | "@"
	// | "," | ";" | ":" | "\" | <">
	// | "/" | "[" | "]" | "?" | "="
	// | "{" | "}" | SP | HT
	// SP = <US-ASCII SP, space (32)>
	// HT = <US-ASCII HT, horizontal-tab (9)>

	return (new RegExp(
			"^[^\\x00-\\x20\\x7f\\(\\)<>@,;:\\\\\\\"\\[\\]\\?=\\{\\}\\/\\u0080-\\uffff]+\x24"))
			.test(key);
};

/**
 * 获取cookie的值，用decodeURIComponent进行解码
 * 
 * @name jQuery.cookie.get
 * @function
 * @grammar jQuery.cookie.get(key)
 * @param {string}
 *            key 需要获取Cookie的键名
 * @remark <b>注意：</b>该方法会对cookie值进行decodeURIComponent解码。如果想获得cookie源字符串，请使用getRaw方法。
 * @meta standard
 * @see jQuery.cookie.getRaw,jQuery.cookie.set
 * 
 * @returns {string|null} cookie的值，获取不到时返回null
 */
jQuery.cookie.get = function(key) {
	var value = jQuery.cookie.getRaw(key);
	if ('string' == typeof value) {
		value = decodeURIComponent(value);
		return value;
	}
	return null;
};
/**
 * 获取cookie的值，不对值进行解码
 * 
 * @name jQuery.cookie.getRaw
 * @function
 * @grammar jQuery.cookie.getRaw(key)
 * @param {string}
 *            key 需要获取Cookie的键名
 * @meta standard
 * @see jQuery.cookie.get,jQuery.cookie.setRaw
 * 
 * @returns {string|null} 获取的Cookie值，获取不到时返回null
 */
jQuery.cookie.getRaw = function(key) {
	if (jQuery.cookie.isValidKey(key)) {
		var reg = new RegExp("(^| )" + key + "=([^;]*)(;|\x24)"), result = reg
				.exec(document.cookie);

		if (result) {
			return result[2] || null;
		}
	}

	return null;
};
/**
 * 删除cookie的值
 * 
 * @name jQuery.cookie.remove
 * @function
 * @grammar jQuery.cookie.remove(key, options)
 * @param {string}
 *            key 需要删除Cookie的键名
 * @param {Object}
 *            options 需要删除的cookie对应的 path domain 等值
 * @meta standard
 */
jQuery.cookie.remove = function(key, options) {
	options = options || {};
	options.expires = new Date(0);
	jQuery.cookie.setRaw(key, '', options);
};
/**
 * 设置cookie的值，用encodeURIComponent进行编码
 * 
 * @name jQuery.cookie.set
 * @function
 * @grammar jQuery.cookie.set(key, value[, options])
 * @param {string}
 *            key 需要设置Cookie的键名
 * @param {string}
 *            value 需要设置Cookie的值
 * @param {Object}
 *            [options] 设置Cookie的其他可选参数
 * @config {string} [path] cookie路径
 * @config {Date|number} [expires] cookie过期时间,如果类型是数字的话, 单位是毫秒
 * @config {string} [domain] cookie域名
 * @config {string} [secure] cookie是否安全传输
 * @remark
 * 
 * 1. <b>注意：</b>该方法会对cookie值进行encodeURIComponent编码。如果想设置cookie源字符串，请使用setRaw方法。<br>
 * <br>
 * 2. <b>options参数包括：</b><br>
 * path:cookie路径<br>
 * expires:cookie过期时间，Number型，单位为毫秒。<br>
 * domain:cookie域名<br>
 * secure:cookie是否安全传输
 * 
 * @meta standard
 * @see jQuery.cookie.setRaw,jQuery.cookie.get
 */
jQuery.cookie.set = function(key, value, options) {
	jQuery.cookie.setRaw(key, encodeURIComponent(value), options);
};
/**
 * 设置cookie的值，不对值进行编码
 * 
 * @name jQuery.cookie.setRaw
 * @function
 * @grammar jQuery.cookie.setRaw(key, value[, options])
 * @param {string}
 *            key 需要设置Cookie的键名
 * @param {string}
 *            value 需要设置Cookie的值
 * @param {Object}
 *            [options] 设置Cookie的其他可选参数
 * @config {string} [path] cookie路径
 * @config {Date|number} [expires] cookie过期时间,如果类型是数字的话, 单位是毫秒
 * @config {string} [domain] cookie域名
 * @config {string} [secure] cookie是否安全传输
 * @remark
 * 
 * <b>options参数包括：</b><br>
 * path:cookie路径<br>
 * expires:cookie过期时间，Number型，单位为毫秒。<br>
 * domain:cookie域名<br>
 * secure:cookie是否安全传输
 * 
 * @meta standard
 * @see jQuery.cookie.set,jQuery.cookie.getRaw
 */
jQuery.cookie.setRaw = function(key, value, options) {
	if (!jQuery.cookie.isValidKey(key)) {
		return;
	}

	options = options || {};
	// options.path = options.path || "/"; // meizz 20100402 设定一个初始值，方便后续的操作
	// berg 20100409 去掉，因为用户希望默认的path是当前路径，这样和浏览器对cookie的定义也是一致的

	// 计算cookie过期时间
	var expires = options.expires;
	if ('number' == typeof options.expires) {
		expires = new Date();
		expires.setTime(expires.getTime() + options.expires);
	}

	document.cookie = key + "=" + value
			+ (options.path ? "; path=" + options.path : "")
			+ (expires ? "; expires=" + expires.toGMTString() : "")
			+ (options.domain ? "; domain=" + options.domain : "")
			+ (options.secure ? "; secure" : '');
};
// TODO
// ###################################date操作相关函数###################################
jQuery.date = jQuery.date || {};
/**
 * 对目标日期对象进行格式化
 * 
 * @name jQuery.date.format
 * @function
 * @grammar jQuery.date.format(source, pattern)
 * @param {Date}
 *            source 目标日期对象
 * @param {string}
 *            pattern 日期格式化规则
 * @remark
 * 
 * <b>格式表达式，变量含义：</b><br>
 * <br>
 * hh: 带 0 补齐的两位 12 进制时表示<br>
 * h: 不带 0 补齐的 12 进制时表示<br>
 * HH: 带 0 补齐的两位 24 进制时表示<br>
 * H: 不带 0 补齐的 24 进制时表示<br>
 * mm: 带 0 补齐两位分表示<br>
 * m: 不带 0 补齐分表示<br>
 * ss: 带 0 补齐两位秒表示<br>
 * s: 不带 0 补齐秒表示<br>
 * yyyy: 带 0 补齐的四位年表示<br>
 * yy: 带 0 补齐的两位年表示<br>
 * MM: 带 0 补齐的两位月表示<br>
 * M: 不带 0 补齐的月表示<br>
 * dd: 带 0 补齐的两位日表示<br>
 * d: 不带 0 补齐的日表示
 * 
 * 
 * @returns {string} 格式化后的字符串
 */

jQuery.date.format = function(source, pattern) {
	if ('string' != typeof pattern) {
		return source.toString();
	}

	function replacer(patternPart, result) {
		pattern = pattern.replace(patternPart, result);
	}

	var pad = jQuery.number.pad, year = source.getFullYear(), month = source
			.getMonth() + 1, date2 = source.getDate(), hours = source
			.getHours(), minutes = source.getMinutes(), seconds = source
			.getSeconds();

	replacer(/yyyy/g, pad(year, 4));
	replacer(/yy/g, pad(parseInt(year.toString().slice(2), 10), 2));
	replacer(/MM/g, pad(month, 2));
	replacer(/M/g, month);
	replacer(/dd/g, pad(date2, 2));
	replacer(/d/g, date2);

	replacer(/HH/g, pad(hours, 2));
	replacer(/H/g, hours);
	replacer(/hh/g, pad(hours % 12, 2));
	replacer(/h/g, hours % 12);
	replacer(/mm/g, pad(minutes, 2));
	replacer(/m/g, minutes);
	replacer(/ss/g, pad(seconds, 2));
	replacer(/s/g, seconds);

	return pattern;
};
/**
 * 将目标字符串转换成日期对象
 * 
 * @name jQuery.date.parse
 * @function
 * @grammar jQuery.date.parse(source)
 * @param {string}
 *            source 目标字符串
 * @remark
 * 
 * 对于目标字符串，下面这些规则决定了 parse 方法能够成功地解析： <br>
 * <ol>
 * <li>短日期可以使用“/”或“-”作为日期分隔符，但是必须用月/日/年的格式来表示，例如"7/20/96"。</li>
 * <li>以 "July 10 1995" 形式表示的长日期中的年、月、日可以按任何顺序排列，年份值可以用 2 位数字表示也可以用 4
 * 位数字表示。如果使用 2 位数字来表示年份，那么该年份必须大于或等于 70。 </li>
 * <li>括号中的任何文本都被视为注释。这些括号可以嵌套使用。 </li>
 * <li>逗号和空格被视为分隔符。允许使用多个分隔符。 </li>
 * <li>月和日的名称必须具有两个或两个以上的字符。如果两个字符所组成的名称不是独一无二的，那么该名称就被解析成最后一个符合条件的月或日。例如，"Ju"
 * 被解释为七月而不是六月。 </li>
 * <li>在所提供的日期中，如果所指定的星期几的值与按照该日期中剩余部分所确定的星期几的值不符合，那么该指定值就会被忽略。例如，尽管 1996 年 11
 * 月 9 日实际上是星期五，"Tuesday November 9 1996" 也还是可以被接受并进行解析的。但是结果 date 对象中包含的是
 * "Friday November 9 1996"。 </li>
 * <li>JScript 处理所有的标准时区，以及全球标准时间 (UTC) 和格林威治标准时间 (GMT)。</li>
 * <li>小时、分钟、和秒钟之间用冒号分隔，尽管不是这三项都需要指明。"10:"、"10:11"、和 "10:11:12" 都是有效的。 </li>
 * <li>如果使用 24 小时计时的时钟，那么为中午 12 点之后的时间指定 "PM" 是错误的。例如 "23:15 PM" 就是错误的。</li>
 * <li>包含无效日期的字符串是错误的。例如，一个包含有两个年份或两个月份的字符串就是错误的。</li>
 * </ol>
 * 
 * 
 * @returns {Date} 转换后的日期对象
 */

jQuery.date.parse = function(source) {
	var reg = new RegExp("^\\d+(\\-|\\/)\\d+(\\-|\\/)\\d+\x24");
	if ('string' == typeof source) {
		if (reg.test(source) || isNaN(Date.parse(source))) {
			var d = source.split(/ |T/), d1 = d.length > 1 ? d[1]
					.split(/[^\d]/) : [ 0, 0, 0 ], d0 = d[0].split(/[^\d]/);
			return new Date(d0[0] - 0, d0[1] - 1, d0[2] - 0, d1[0] - 0,
					d1[1] - 0, d1[2] - 0);
		} else {
			return new Date(source);
		}
	}

	return new Date();
};

// TODO
// ###################################url操作相关函数###################################
jQuery.url = jQuery.url || {};
/**
 * 对字符串进行%#&+=以及和\s匹配的所有字符进行url转义
 * 
 * @name jQuery.url.escapeSymbol
 * @function
 * @grammar jQuery.url.escapeSymbol(source)
 * @param {string}
 *            source 需要转义的字符串.
 * @return {string} 转义之后的字符串.
 * @remark 用于get请求转义。在服务器只接受gbk，并且页面是gbk编码时，可以经过本转义后直接发get请求。
 * 
 * @return {string} 转义后的字符串
 */
jQuery.url.escapeSymbol = function(source) {

	// 之前使用\s来匹配任意空白符
	// 发现在ie下无法匹配中文全角空格和纵向指标符\v，所以改\s为\f\r\n\t\v以及中文全角空格和英文空格
	// 但是由于ie本身不支持纵向指标符\v,故去掉对其的匹配，保证各浏览器下效果一致
	return String(source).replace(
			/[#%&+=\/\\\ \　\f\r\n\t]/g,
			function(all) {
				return '%'
						+ (0x100 + all.charCodeAt()).toString(16).substring(1)
								.toUpperCase();
			});
};
/**
 * 根据参数名从目标URL中获取参数值
 * 
 * @name jQuery.url.getQueryValue
 * @function
 * @grammar jQuery.url.getQueryValue(url, key)
 * @param {string}
 *            url 目标URL
 * @param {string}
 *            key 要获取的参数名
 * @meta standard
 * @see jQuery.url.jsonToQuery
 * 
 * @returns {string|null} - 获取的参数值，其中URI编码后的字符不会被解码，获取不到时返回null
 */
jQuery.url.getQueryValue = function(url, key) {
	var reg = new RegExp("(^|&|\\?|#)" + jQuery.string.escapeReg(key)
			+ "=([^&#]*)(&|\x24|#)", "");
	var match = url.match(reg);
	if (match) {
		return match[2];
	}

	return null;
};
/**
 * 将json对象解析成query字符串
 * 
 * @name jQuery.url.jsonToQuery
 * @function
 * @grammar jQuery.url.jsonToQuery(json[, replacer])
 * @param {Object}
 *            json 需要解析的json对象
 * @param {Function=}
 *            replacer_opt 对值进行特殊处理的函数，function (value, key)
 * @see jQuery.url.queryToJson,jQuery.url.getQueryValue
 * 
 * @return {string} - 解析结果字符串，其中值将被URI编码，{a:'&1 '} ==> "a=%261%20"。
 */
jQuery.url.jsonToQuery = function(json, replacer_opt) {
	var result = [], itemLen, replacer = replacer_opt || function(value) {
		return jQuery.url.escapeSymbol(value);
	};

	jQuery.object.each(json, function(item, key) {
		// 这里只考虑item为数组、字符串、数字类型，不考虑嵌套的object
		if (jQuery.lang.isArray(item)) {
			itemLen = item.length;
			// value的值需要encodeURIComponent转义吗？
			// FIXED 优化了escapeSymbol函数
			while (itemLen--) {
				result.push(key + '=' + replacer(item[itemLen], key));
			}
		} else {
			result.push(key + '=' + replacer(item, key));
		}
	});

	return result.join('&');
};
/**
 * 解析目标URL中的参数成json对象
 * 
 * @name jQuery.url.queryToJson
 * @function
 * @grammar jQuery.url.queryToJson(url)
 * @param {string}
 *            url 目标URL
 * @see jQuery.url.jsonToQuery
 * 
 * @returns {Object} - 解析为结果对象，其中URI编码后的字符不会被解码，'a=%20' ==> {a:'%20'}。
 */
jQuery.url.queryToJson = function(url) {
	var query = url.substr(url.lastIndexOf('?') + 1), params = query.split('&'), len = params.length, result = {}, i = 0, key, value, item, param;

	for (; i < len; i++) {
		if (!params[i]) {
			continue;
		}
		param = params[i].split('=');
		key = param[0];
		value = param[1];

		item = result[key];
		if ('undefined' == typeof item) {
			result[key] = value;
		} else if (jQuery.lang.isArray(item)) {
			item.push(value);
		} else { // 这里只可能是string了
			result[key] = [ item, value ];
		}
	}

	return result;
};
// TODO这个函数的实现方式还有待检验
jQuery.url.getContextPath = function() {
	var path = String(window.document.location);
	return path.substring(0, path.lastIndexOf("/"));
};
// TODO
// ###################################form操作相关函数###################################
jQuery.form = jQuery.form || {};
/**
 * josn化表单数据
 * 
 * @name jQuery.form.json
 * @function
 * @grammar jQuery.form.json(form[, replacer])
 * @param {HTMLFormElement}
 *            form 需要提交的表单元素
 * @param {Function}
 *            replacer 对参数值特殊处理的函数,replacer(string value, string key)
 * 
 * @returns {data} 表单数据js对象
 */
jQuery.form.json = function(form, replacer) {
	var elements = form.elements, replacer = replacer || function(value, name) {
		return value;
	}, data = {}, item, itemType, itemName, itemValue, opts, oi, oLen, oItem;

	/**
	 * 向缓冲区添加参数数据
	 * 
	 * @private
	 */
	function addData(name, value) {
		var val = data[name];
		if (val) {
			val.push || (data[name] = [ val ]);
			data[name].push(value);
		} else {
			data[name] = value;
		}
	}

	for ( var i = 0, len = elements.length; i < len; i++) {
		item = elements[i];
		itemName = item.name;

		// 处理：可用并包含表单name的表单项
		if (!item.disabled && itemName) {
			itemType = item.type;
			itemValue = jQuery.url.escapeSymbol(item.value);

			switch (itemType) {
			// radio和checkbox被选中时，拼装queryString数据
			case 'radio':
			case 'checkbox':
				if (!item.checked) {
					break;
				}

				// 默认类型，拼装queryString数据
			case 'textarea':
			case 'text':
			case 'password':
			case 'hidden':
			case 'file':
			case 'select-one':
				addData(itemName, replacer(itemValue, itemName));
				break;

			// 多行选中select，拼装所有选中的数据
			case 'select-multiple':
				opts = item.options;
				oLen = opts.length;
				for (oi = 0; oi < oLen; oi++) {
					oItem = opts[oi];
					if (oItem.selected) {
						addData(itemName, replacer(oItem.value, itemName));
					}
				}
				break;
			}
		}
	}

	return data;
};
/**
 * 序列化表单数据
 * 
 * @name jQuery.form.serialize
 * @function
 * @grammar jQuery.form.serialize(form[, replacer])
 * @param {HTMLFormElement}
 *            form 需要提交的表单元素
 * @param {Function}
 *            replacer 对参数值特殊处理的函数,replacer(string value, string key)
 * 
 * @returns {data} 表单数据数组
 */
jQuery.form.serialize = function(form, replacer) {
	var elements = form.elements, replacer = replacer || function(value, name) {
		return value;
	}, data = [], item, itemType, itemName, itemValue, opts, oi, oLen, oItem;

	/**
	 * 向缓冲区添加参数数据
	 * 
	 * @private
	 */
	function addData(name, value) {
		data.push(name + '=' + value);
	}

	for ( var i = 0, len = elements.length; i < len; i++) {
		item = elements[i];
		itemName = item.name;

		// 处理：可用并包含表单name的表单项
		if (!item.disabled && itemName) {
			itemType = item.type;
			itemValue = jQuery.url.escapeSymbol(item.value);

			switch (itemType) {
			// radio和checkbox被选中时，拼装queryString数据
			case 'radio':
			case 'checkbox':
				if (!item.checked) {
					break;
				}

				// 默认类型，拼装queryString数据
			case 'textarea':
			case 'text':
			case 'password':
			case 'hidden':
			case 'file':
			case 'select-one':
				addData(itemName, replacer(itemValue, itemName));
				break;

			// 多行选中select，拼装所有选中的数据
			case 'select-multiple':
				opts = item.options;
				oLen = opts.length;
				for (oi = 0; oi < oLen; oi++) {
					oItem = opts[oi];
					if (oItem.selected) {
						addData(itemName, replacer(oItem.value, itemName));
					}
				}
				break;
			}
		}
	}

	return data;
};
/**
 * 找到表单的第一个元素，未找到则返回null
 * 
 * @name jQuery.form.findFirstElement
 * @function
 * @grammar jQuery.form.findFirstElement(form)
 * @param {HTMLFormElement}
 *            form 需要查找的表单元素
 * @returns {data} 表单的第一个元素
 */
jQuery.form.findFirstElement = function(form) {
	var elements = null;
	if (!(elements = form.elements) || typeof elements.length !== "number"
			|| elements.length === 0) {
		return null;
	}
	return elements[0];
};
/**
 * 为表单的第一个元素设置焦点，找不到这个元素则什么都不做
 * 
 * @name jQuery.form.focusFirstElement
 * @function
 * @grammar jQuery.form.focusFirstElement(form)
 * @param {HTMLFormElement}
 *            form 需要查找的表单元素
 */
jQuery.form.focusFirstElement = function(form) {
	var elements = null;
	if (!(elements = form.elements) || typeof elements.length !== "number"
			|| elements.length === 0) {
		return;
	}
	jQuery(elements[0]).focus();
};
// TODO
// ###################################json操作相关函数###################################
jQuery.json = jQuery.json || {};
/**
 * 将字符串解析成json对象。注：不会自动祛除空格
 * 
 * @name jQuery.json.parse
 * @function
 * @grammar jQuery.json.parse(data)
 * @param {string}
 *            source 需要解析的字符串
 * @remark 该方法的实现与ecma-262第五版中规定的JSON.parse不同，暂时只支持传入一个参数。后续会进行功能丰富。
 * @meta standard
 * @see jQuery.json.stringify,jQuery.json.decode
 * 
 * @returns {JSON} 解析结果json对象
 */
jQuery.json.parse = function(data) {
	// 2010/12/09：更新至不使用原生parse，不检测用户输入是否正确
	return (new Function("return (" + data + ")"))();
};
/**
 * 将json对象序列化
 * 
 * @name jQuery.json.stringify
 * @function
 * @grammar jQuery.json.stringify(value)
 * @param {JSON}
 *            value 需要序列化的json对象
 * @remark 该方法的实现与ecma-262第五版中规定的JSON.stringify不同，暂时只支持传入一个参数。后续会进行功能丰富。
 * @meta standard
 * @see jQuery.json.parse,jQuery.json.encode
 * 
 * @returns {string} 序列化后的字符串
 */
jQuery.json.stringify = (function() {
	/**
	 * 字符串处理时需要转义的字符表
	 * 
	 * @private
	 */
	var escapeMap = {
		"\b" : '\\b',
		"\t" : '\\t',
		"\n" : '\\n',
		"\f" : '\\f',
		"\r" : '\\r',
		'"' : '\\"',
		"\\" : '\\\\'
	};

	/**
	 * 字符串序列化
	 * 
	 * @private
	 */
	function encodeString(source) {
		if (/["\\\x00-\x1f]/.test(source)) {
			source = source.replace(/["\\\x00-\x1f]/g, function(match) {
				var c = escapeMap[match];
				if (c) {
					return c;
				}
				c = match.charCodeAt();
				return "\\u00" + Math.floor(c / 16).toString(16)
						+ (c % 16).toString(16);
			});
		}
		return '"' + source + '"';
	}

	/**
	 * 数组序列化
	 * 
	 * @private
	 */
	function encodeArray(source) {
		var result = [ "[" ], l = source.length, preComma, i, item;

		for (i = 0; i < l; i++) {
			item = source[i];

			switch (typeof item) {
			case "undefined":
			case "function":
			case "unknown":
				break;
			default:
				if (preComma) {
					result.push(',');
				}
				result.push(jQuery.json.stringify(item));
				preComma = 1;
			}
		}
		result.push("]");
		return result.join("");
	}

	/**
	 * 处理日期序列化时的补零
	 * 
	 * @private
	 */
	function pad(source) {
		return source < 10 ? '0' + source : source;
	}

	/**
	 * 日期序列化
	 * 
	 * @private
	 */
	function encodeDate(source) {
		return '"' + source.getFullYear() + "-" + pad(source.getMonth() + 1)
				+ "-" + pad(source.getDate()) + "T" + pad(source.getHours())
				+ ":" + pad(source.getMinutes()) + ":"
				+ pad(source.getSeconds()) + '"';
	}

	return function(value) {
		switch (typeof value) {
		case 'undefined':
			return 'undefined';

		case 'number':
			return isFinite(value) ? String(value) : "null";

		case 'string':
			return encodeString(value);

		case 'boolean':
			return String(value);

		default:
			if (value === null) {
				return 'null';
			} else if (value instanceof Array) {
				return encodeArray(value);
			} else if (value instanceof Date) {
				return encodeDate(value);
			} else {
				var result = [ '{' ], encode = jQuery.json.stringify, preComma, item;

				for ( var key in value) {
					if (Object.prototype.hasOwnProperty.call(value, key)) {
						item = value[key];
						switch (typeof item) {
						case 'undefined':
						case 'unknown':
						case 'function':
							break;
						default:
							if (preComma) {
								result.push(',');
							}
							preComma = 1;
							result.push(encode(key) + ':' + encode(item));
						}
					}
				}
				result.push('}');
				return result.join('');
			}
		}
	};
})();

// TODO
// ###################################lang操作相关函数###################################
jQuery.lang = jQuery.lang || {};
/**
 * 判断目标参数是否Array对象
 * 
 * @name jQuery.lang.isArray
 * @function
 * @grammar jQuery.lang.isArray(source)
 * @param {Any}
 *            source 目标参数
 * @meta standard
 * @see jQuery.lang.isString,jQuery.lang.isObject,jQuery.lang.isNumber,jQuery.lang.isElement,jQuery.lang.isBoolean,jQuery.lang.isDate
 * 
 * @returns {boolean} 类型判断结果
 */
jQuery.lang.isArray = function(source) {
	return '[object Array]' == Object.prototype.toString.call(source);
};
/**
 * 判断目标参数是否Boolean对象
 * 
 * @name jQuery.lang.isBoolean
 * @function
 * @grammar jQuery.lang.isBoolean(source)
 * @param {Any}
 *            source 目标参数
 * @version 1.3
 * @see jQuery.lang.isString,jQuery.lang.isObject,jQuery.lang.isNumber,jQuery.lang.isElement,jQuery.lang.isArray,jQuery.lang.isDate
 * 
 * @returns {boolean} 类型判断结果
 */
jQuery.lang.isBoolean = function(o) {
	return typeof o === 'boolean';
};
/**
 * 判断目标参数是否为Date对象
 * 
 * @name jQuery.lang.isDate
 * @function
 * @grammar jQuery.lang.isDate(source)
 * @param {Any}
 *            source 目标参数
 * @version 1.3
 * @see jQuery.lang.isString,jQuery.lang.isObject,jQuery.lang.isNumber,jQuery.lang.isArray,jQuery.lang.isBoolean,jQuery.lang.isElement
 * 
 * @returns {boolean} 类型判断结果
 */
jQuery.lang.isDate = function(o) {
	// return o instanceof Date;
	return {}.toString.call(o) === "[object Date]"
			&& o.toString() !== 'Invalid Date' && !isNaN(o);
};
/**
 * 判断目标参数是否为Element对象
 * 
 * @name jQuery.lang.isElement
 * @function
 * @grammar jQuery.lang.isElement(source)
 * @param {Any}
 *            source 目标参数
 * @meta standard
 * @see jQuery.lang.isString,jQuery.lang.isObject,jQuery.lang.isNumber,jQuery.lang.isArray,jQuery.lang.isBoolean,jQuery.lang.isDate
 * 
 * @returns {boolean} 类型判断结果
 */
jQuery.lang.isElement = function(source) {
	return !!(source && source.nodeName && source.nodeType == 1);
};
/**
 * 判断目标参数是否为function或Function实例
 * 
 * @name jQuery.lang.isFunction
 * @function
 * @grammar jQuery.lang.isFunction(source)
 * @param {Any}
 *            source 目标参数
 * @version 1.2
 * @see jQuery.lang.isString,jQuery.lang.isObject,jQuery.lang.isNumber,jQuery.lang.isArray,jQuery.lang.isElement,jQuery.lang.isBoolean,jQuery.lang.isDate
 * @meta standard
 * @returns {boolean} 类型判断结果
 */
jQuery.lang.isFunction = function(source) {
	// chrome下,'function' == typeof /a/ 为true.
	return '[object Function]' == Object.prototype.toString.call(source);
};
/**
 * 判断目标参数是否number类型或Number对象
 * 
 * @name jQuery.lang.isNumber
 * @function
 * @grammar jQuery.lang.isNumber(source)
 * @param {Any}
 *            source 目标参数
 * @meta standard
 * @see jQuery.lang.isString,jQuery.lang.isObject,jQuery.lang.isArray,jQuery.lang.isElement,jQuery.lang.isBoolean,jQuery.lang.isDate
 * 
 * @returns {boolean} 类型判断结果
 * @remark 用本函数判断NaN会返回false，尽管在Javascript中是Number类型。
 */
jQuery.lang.isNumber = function(source) {
	return '[object Number]' == Object.prototype.toString.call(source)
			&& isFinite(source);
};
/**
 * 判断目标参数是否为Object对象
 * 
 * @name jQuery.lang.isObject
 * @function
 * @grammar jQuery.lang.isObject(source)
 * @param {Any}
 *            source 目标参数
 * @shortcut isObject
 * @meta standard
 * @see jQuery.lang.isString,jQuery.lang.isNumber,jQuery.lang.isArray,jQuery.lang.isElement,jQuery.lang.isBoolean,jQuery.lang.isDate
 * 
 * @returns {boolean} 类型判断结果
 */
jQuery.lang.isObject = function(source) {
	return 'function' == typeof source
			|| !!(source && 'object' == typeof source);
};
/**
 * 判断目标参数是否string类型或String对象
 * 
 * @name jQuery.lang.isString
 * @function
 * @grammar jQuery.lang.isString(source)
 * @param {Any}
 *            source 目标参数
 * @shortcut isString
 * @meta standard
 * @see jQuery.lang.isObject,jQuery.lang.isNumber,jQuery.lang.isArray,jQuery.lang.isElement,jQuery.lang.isBoolean,jQuery.lang.isDate
 * 
 * @returns {boolean} 类型判断结果
 */
jQuery.lang.isString = function(source) {
	return '[object String]' == Object.prototype.toString.call(source);
};
/**
 * 将一个变量转换成array
 * 
 * @name jQuery.lang.toArray
 * @function
 * @grammar jQuery.lang.toArray(source)
 * @param {mix}
 *            source 需要转换成array的变量
 * @version 1.3
 * @meta standard
 * @returns {array} 转换后的array
 */
jQuery.lang.toArray = function(source) {
	if (source === null || source === undefined)
		return [];
	if (jQuery.lang.isArray(source))
		return source;

	// The strings and functions also have 'length'
	if (typeof source.length !== 'number' || typeof source === 'string'
			|| jQuery.lang.isFunction(source)) {
		return [ source ];
	}

	// nodeList, IE 下调用 [].slice.call(nodeList) 会报错
	if (source.item) {
		var l = source.length, array = new Array(l);
		while (l--)
			array[l] = source[l];
		return array;
	}

	return [].slice.call(source);
};
// TODO
// ###################################number操作相关函数###################################
jQuery.number = jQuery.number || {};
/**
 * 对数字进行格式化
 * 
 * @name jQuery.number.format
 * @function
 * @grammar jQuery.number.format(number, pattern)
 * @param {number}
 *            source 需要处理的数字
 * @param {number}
 *            pattern 格式化规则，例如format('12432.415','#,###.0#')='12,432.42'
 * 
 * @returns {string} 对目标数字进行格式化之后的处理结果
 */
jQuery.number.format = function(source, pattern) {
	var str = source.toString();
	var strInt;
	var strFloat;
	var formatInt;
	var formatFloat;
	if (/\./g.test(pattern)) {
		formatInt = pattern.split('.')[0];
		formatFloat = pattern.split('.')[1];
	} else {
		formatInt = pattern;
		formatFloat = null;
	}
	if (/\./g.test(str)) {
		if (formatFloat != null) {
			var tempFloat = Math.round(parseFloat('0.' + str.split('.')[1])
					* Math.pow(10, formatFloat.length))
					/ Math.pow(10, formatFloat.length);
			strInt = (Math.floor(source) + Math.floor(tempFloat)).toString();
			strFloat = /\./g.test(tempFloat.toString()) ? tempFloat.toString()
					.split('.')[1] : '0';
		} else {
			strInt = Math.round(source).toString();
			strFloat = '0';
		}
	} else {
		strInt = str;
		strFloat = '0';
	}
	if (formatInt != null) {
		var outputInt = '';
		var zero = formatInt.match(/0*$/)[0].length;
		var comma = null;
		if (/,/g.test(formatInt)) {
			comma = formatInt.match(/,[^,]*/)[0].length - 1;
		}
		var newReg = new RegExp('(\\d{' + comma + '})', 'g');
		if (strInt.length < zero) {
			outputInt = new Array(zero + 1).join('0') + strInt;
			outputInt = outputInt.substr(outputInt.length - zero, zero);
		} else {
			outputInt = strInt;
		}
		var outputInt = outputInt.substr(0, outputInt.length % comma)
				+ outputInt.substring(outputInt.length % comma).replace(newReg,
						(comma != null ? ',' : '') + '$1');
		outputInt = outputInt.replace(/^,/, '');
		strInt = outputInt;
	}
	if (formatFloat != null) {
		var outputFloat = '';
		var zero = formatFloat.match(/^0*/)[0].length;
		if (strFloat.length < zero) {
			outputFloat = strFloat + new Array(zero + 1).join('0');
			// outputFloat = outputFloat.substring(0,formatFloat.length);
			var outputFloat1 = outputFloat.substring(0, zero);
			var outputFloat2 = outputFloat.substring(zero, formatFloat.length);
			outputFloat = outputFloat1 + outputFloat2.replace(/0*$/, '');
		} else {
			outputFloat = strFloat.substring(0, formatFloat.length);
		}
		strFloat = outputFloat;
	} else {
		if (pattern != '' || (pattern == '' && strFloat == '0')) {
			strFloat = '';
		}
	}
	return strInt + (strFloat == '' ? '' : '.' + strFloat);
};
/**
 * 生成随机整数，范围是[min, max]
 * 
 * @name jQuery.number.randomInt
 * @function
 * @grammar jQuery.number.randomInt(min, max)
 * 
 * @param {number}
 *            min 随机整数的最小值
 * @param {number}
 *            max 随机整数的最大值
 * @return {number} 生成的随机整数
 */
jQuery.number.randomInt = function(min, max) {
	return Math.floor(Math.random() * (max - min + 1) + min);
};
// 链接到Math类的一些方法
jQuery.number.abs = Math.abs;
jQuery.number.ceil = Math.ceil;
jQuery.number.floor = Math.floor;
jQuery.number.round = Math.round;
jQuery.number.min = Math.min;
jQuery.number.max = Math.max;
// TODO
// ###################################page操作相关函数###################################
jQuery.page = jQuery.page || {};
/**
 * 获取横向滚动量
 * 
 * @return {number} 横向滚动量
 */
jQuery.page.getScrollLeft = function() {
	var d = document;
	return window.pageXOffset || d.documentElement.scrollLeft
			|| d.body.scrollLeft;
};
/**
 * 获取纵向滚动量
 * 
 * @name jQuery.page.getScrollTop
 * @function
 * @grammar jQuery.page.getScrollTop()
 * @see jQuery.page.getScrollLeft
 * @meta standard
 * @returns {number} 纵向滚动量
 */
jQuery.page.getScrollTop = function() {
	var d = document;
	return window.pageYOffset || d.documentElement.scrollTop
			|| d.body.scrollTop;
};
/**
 * 获取页面视觉区域高度
 * 
 * @name jQuery.page.getViewHeight
 * @function
 * @grammar jQuery.page.getViewHeight()
 * @see jQuery.page.getViewWidth
 * @meta standard
 * @returns {number} 页面视觉区域高度
 */
jQuery.page.getViewHeight = function() {
	var doc = document, client = doc.compatMode == 'BackCompat' ? doc.body
			: doc.documentElement;

	return client.clientHeight;
};
/**
 * 获取页面视觉区域宽度
 * 
 * @name jQuery.page.getViewWidth
 * @function
 * @grammar jQuery.page.getViewWidth()
 * @see jQuery.page.getViewHeight
 * 
 * @returns {number} 页面视觉区域宽度
 */
jQuery.page.getViewWidth = function() {
	var doc = document, client = doc.compatMode == 'BackCompat' ? doc.body
			: doc.documentElement;

	return client.clientWidth;
};

/**
 * 动态在页面上加载一个外部js文件
 * 
 * @name jQuery.page.loadJsFile
 * @function
 * @grammar jQuery.page.loadJsFile(path)
 * @param {string}
 *            path js文件路径
 * @see jQuery.page.loadCssFile
 */
jQuery.page.loadJsFile = function(path) {
	var element = document.createElement('script');

	element.setAttribute('type', 'text/javascript');
	element.setAttribute('src', path);
	element.setAttribute('defer', 'defer');

	document.getElementsByTagName("head")[0].appendChild(element);
};
/**
 * 动态在页面上加载一个外部css文件
 * 
 * @name jQuery.page.loadCssFile
 * @function
 * @grammar jQuery.page.loadCssFile(path)
 * @param {string}
 *            path css文件路径
 * @see jQuery.page.loadJsFile
 */

jQuery.page.loadCssFile = function(path) {
	var element = document.createElement("link");

	element.setAttribute("rel", "stylesheet");
	element.setAttribute("type", "text/css");
	element.setAttribute("href", path);

	document.getElementsByTagName("head")[0].appendChild(element);
};

// TODO
// ###################################swf操作相关函数###################################
jQuery.swf = jQuery.swf || {};
/**
 * 浏览器支持的flash插件版本
 * 
 * @property version 浏览器支持的flash插件版本
 * @grammar jQuery.swf.version
 * @return {String} 版本号
 * @meta standard
 */
jQuery.swf.version = (function() {
	var n = navigator;
	if (n.plugins && n.mimeTypes.length) {
		var plugin = n.plugins["Shockwave Flash"];
		if (plugin && plugin.description) {
			return plugin.description.replace(/([a-zA-Z]|\s)+/, "").replace(
					/(\s)+r/, ".")
					+ ".0";
		}
	} else if (window.ActiveXObject && !window.opera) {
		for ( var i = 12; i >= 2; i--) {
			try {
				var c = new ActiveXObject('ShockwaveFlash.ShockwaveFlash.' + i);
				if (c) {
					var version = c.GetVariable("$version");
					return version.replace(/WIN/g, '').replace(/,/g, '.');
				}
			} catch (e) {
			}
		}
	}
})();
/**
 * 创建flash对象的html字符串
 * 
 * @name jQuery.swf.createHTML
 * @function
 * @grammar jQuery.swf.createHTML(options)
 * 
 * @param {Object}
 *            options 创建flash的选项参数
 * @param {string}
 *            options.id 要创建的flash的标识
 * @param {string}
 *            options.url flash文件的url
 * @param {String}
 *            options.errorMessage 未安装flash player或flash player版本号过低时的提示
 * @param {string}
 *            options.ver 最低需要的flash player版本号
 * @param {string}
 *            options.width flash的宽度
 * @param {string}
 *            options.height flash的高度
 * @param {string}
 *            options.align flash的对齐方式，允许值：middle/left/right/top/bottom
 * @param {string}
 *            options.base 设置用于解析swf文件中的所有相对路径语句的基本目录或URL
 * @param {string}
 *            options.bgcolor swf文件的背景色
 * @param {string}
 *            options.salign
 *            设置缩放的swf文件在由width和height设置定义的区域内的位置。允许值：l/r/t/b/tl/tr/bl/br
 * @param {boolean}
 *            options.menu 是否显示右键菜单，允许值：true/false
 * @param {boolean}
 *            options.loop 播放到最后一帧时是否重新播放，允许值： true/false
 * @param {boolean}
 *            options.play flash是否在浏览器加载时就开始播放。允许值：true/false
 * @param {string}
 *            options.quality
 *            设置flash播放的画质，允许值：low/medium/high/autolow/autohigh/best
 * @param {string}
 *            options.scale 设置flash内容如何缩放来适应设置的宽高。允许值：showall/noborder/exactfit
 * @param {string}
 *            options.wmode 设置flash的显示模式。允许值：window/opaque/transparent
 * @param {string}
 *            options.allowscriptaccess
 *            设置flash与页面的通信权限。允许值：always/never/sameDomain
 * @param {string}
 *            options.allownetworking 设置swf文件中允许使用的网络API。允许值：all/internal/none
 * @param {boolean}
 *            options.allowfullscreen 是否允许flash全屏。允许值：true/false
 * @param {boolean}
 *            options.seamlesstabbing
 *            允许设置执行无缝跳格，从而使用户能跳出flash应用程序。该参数只能在安装Flash7及更高版本的Windows中使用。允许值：true/false
 * @param {boolean}
 *            options.devicefont 设置静态文本对象是否以设备字体呈现。允许值：true/false
 * @param {boolean}
 *            options.swliveconnect 第一次加载flash时浏览器是否应启动Java。允许值：true/false
 * @param {Object}
 *            options.vars 要传递给flash的参数，支持JSON或string类型。
 * 
 * @see jQuery.swf.create
 * @meta standard
 * @returns {string} flash对象的html字符串
 */
jQuery.swf.createHTML = function(options) {
	options = options || {};
	var version = jQuery.swf.version, needVersion = options['ver'] || '6.0.0', vUnit1, vUnit2, i, k, len, item, tmpOpt = {}, encodeHTML = jQuery.string.encodeHTML;

	// 复制options，避免修改原对象
	for (k in options) {
		tmpOpt[k] = options[k];
	}
	options = tmpOpt;

	// 浏览器支持的flash插件版本判断
	if (version) {
		version = version.split('.');
		needVersion = needVersion.split('.');
		for (i = 0; i < 3; i++) {
			vUnit1 = parseInt(version[i], 10);
			vUnit2 = parseInt(needVersion[i], 10);
			if (vUnit2 < vUnit1) {
				break;
			} else if (vUnit2 > vUnit1) {
				return ''; // 需要更高的版本号
			}
		}
	} else {
		return ''; // 未安装flash插件
	}

	var vars = options['vars'], objProperties = [ 'classid', 'codebase', 'id',
			'width', 'height', 'align' ];

	// 初始化object标签需要的classid、codebase属性值
	options['align'] = options['align'] || 'middle';
	options['classid'] = 'clsid:d27cdb6e-ae6d-11cf-96b8-444553540000';
	options['codebase'] = 'http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0';
	options['movie'] = options['url'] || '';
	delete options['vars'];
	delete options['url'];

	// 初始化flashvars参数的值
	if ('string' == typeof vars) {
		options['flashvars'] = vars;
	} else {
		var fvars = [];
		for (k in vars) {
			item = vars[k];
			fvars.push(k + "=" + encodeURIComponent(item));
		}
		options['flashvars'] = fvars.join('&');
	}

	// 构建IE下支持的object字符串，包括属性和参数列表
	var str = [ '<object ' ];
	for (i = 0, len = objProperties.length; i < len; i++) {
		item = objProperties[i];
		str.push(' ', item, '="', encodeHTML(options[item]), '"');
	}
	str.push('>');
	var params = {
		'wmode' : 1,
		'scale' : 1,
		'quality' : 1,
		'play' : 1,
		'loop' : 1,
		'menu' : 1,
		'salign' : 1,
		'bgcolor' : 1,
		'base' : 1,
		'allowscriptaccess' : 1,
		'allownetworking' : 1,
		'allowfullscreen' : 1,
		'seamlesstabbing' : 1,
		'devicefont' : 1,
		'swliveconnect' : 1,
		'flashvars' : 1,
		'movie' : 1
	};

	for (k in options) {
		item = options[k];
		k = k.toLowerCase();
		if (params[k] && (item || item === false || item === 0)) {
			str.push('<param name="' + k + '" value="' + encodeHTML(item)
					+ '" />');
		}
	}

	// 使用embed时，flash地址的属性名是src，并且要指定embed的type和pluginspage属性
	options['src'] = options['movie'];
	options['name'] = options['id'];
	delete options['id'];
	delete options['movie'];
	delete options['classid'];
	delete options['codebase'];
	options['type'] = 'application/x-shockwave-flash';
	options['pluginspage'] = 'http://www.macromedia.com/go/getflashplayer';

	// 构建embed标签的字符串
	str.push('<embed');
	// 在firefox、opera、safari下，salign属性必须在scale属性之后，否则会失效
	// 经过讨论，决定采用BT方法，把scale属性的值先保存下来，最后输出
	var salign;
	for (k in options) {
		item = options[k];
		if (item || item === false || item === 0) {
			if ((new RegExp("^salign\x24", "i")).test(k)) {
				salign = item;
				continue;
			}

			str.push(' ', k, '="', encodeHTML(item), '"');
		}
	}

	if (salign) {
		str.push(' salign="', encodeHTML(salign), '"');
	}
	str.push('></embed></object>');

	return str.join('');
};